package com.wiser.library.zxing;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.wiser.library.R;
import com.wiser.library.base.IWISERBiz;
import com.wiser.library.base.WISERActivity;
import com.wiser.library.base.WISERBuilder;
import com.wiser.library.helper.WISERHelper;
import com.wiser.library.util.WISERPermission;
import com.wiser.library.zxing.camera.CameraManager;
import com.wiser.library.zxing.decoding.CaptureActivityHandler;
import com.wiser.library.zxing.decoding.InactivityTimer;
import com.wiser.library.zxing.view.ViewfinderView;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author Wiser
 *
 *         扫一扫
 */
public abstract class WISERScanActivity<B extends IWISERBiz> extends WISERActivity<B> implements Callback, WISERPermission.PermissionResultListener {

	private CaptureActivityHandler	handler;

	private boolean					hasSurface;

	private Vector<BarcodeFormat>	decodeFormats;

	private String					characterSet;

	private InactivityTimer			inactivityTimer;

	private MediaPlayer				mediaPlayer;

	private boolean					playBeep;

	private static final float		BEEP_VOLUME			= 0.10f;

	private boolean					vibrate;

	private final int				PHOTO				= 1111;

	private static final long		VIBRATE_DURATION	= 200L;

	private ViewfinderView			viewfinderView;

	private SurfaceView				surfaceView;

	private final int				PERMISSION_CAMERA	= 2222;

	@Override protected WISERBuilder build(WISERBuilder builder) {
		return buildScan(builder);
	}

	@Override public void initData(Intent intent) {
		CameraManager.init(getApplication());
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		initDataScan(intent);
	}

	// 获取bitmap
	public static Bitmap getSmallerBitmap(Bitmap bitmap) {
		int size = bitmap.getWidth() * bitmap.getHeight() / 160000;
		if (size <= 1) return bitmap; // 如果小于
		else {
			Matrix matrix = new Matrix();
			matrix.postScale((float) (1 / Math.sqrt(size)), (float) (1 / Math.sqrt(size)));
			return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
	}

	public abstract WISERBuilder buildScan(WISERBuilder builder);

	public abstract void initDataScan(Intent intent);

	public abstract void scanSuccess(String json);

	public abstract void scanFail();

	public Handler getHandler() {
		return handler;
	}

	// 初始化控件
	public void initScan(ViewfinderView viewfinderView, SurfaceView surfaceView) {
		this.viewfinderView = viewfinderView;
		this.surfaceView = surfaceView;
		permission();
	}

	// 判断权限
	private void permission() {
		if (!WISERPermission.with(this).hasPermission(Manifest.permission.CAMERA, Manifest.permission.VIBRATE)) {
			WISERPermission.with(this).setPermissionResultListener(this).applyPermission(PERMISSION_CAMERA, Manifest.permission.CAMERA, Manifest.permission.VIBRATE);
		}
	}

	@Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		WISERPermission.with(this).onRequestPermissionsResult(requestCode, permissions, grantResults);
	}

	@Override public void applyPermissionSuccess(int requestCode) {
		WISERHelper.log().e("权限申请成功开始执行业务");
	}

	@Override public void applyPermissionFail(int requestCode) {
		WISERHelper.log().e("权限申请失败，请前往设置页面进行授权，可自行设置弹窗提示");
	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public void drawViewfinder() {
		if (viewfinderView != null) viewfinderView.drawViewfinder();
	}

	// 闪光灯
	public void flash() {
		CameraManager.get().flashHandler();
	}

	// 相册
	public void photo() {
		WISERHelper.display().intentPhoto(PHOTO);
	}

	@Override protected void onResume() {
		super.onResume();
		onResumeScan();
	}

	// onResume
	public void onResumeScan() {
		if (surfaceView == null) return;
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		assert audioService != null;
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
	}

	@Override protected void onPause() {
		super.onPause();
		onPauseScan();
	}

	// onPause
	public void onPauseScan() {
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
			case PHOTO:
				if (null != data) {
					// // 首先获取到此图片的Uri
					Uri sourceUri = data.getData();
					try {
						// 下面这句话可以通过URi获取到文件的bitmap
						if (null == sourceUri) return;
						Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), sourceUri);

						// 在这里我用到的 getSmallerBitmap 非常重要，下面就要说到
						bitmap = getSmallerBitmap(bitmap);

						// 获取bitmap的宽高，像素矩阵
						int width = bitmap.getWidth();
						int height = bitmap.getHeight();
						int[] pixels = new int[width * height];
						bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

						// 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
						RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
						BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
						Map<DecodeHintType, Object> hints = new LinkedHashMap<>();
						// 解码设置编码方式为：utf-8，
						hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
						// 优化精度
						hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
						// 复杂模式，开启PURE_BARCODE模式
						hints.put(DecodeHintType.PURE_BARCODE, Boolean.TRUE);

						// 尝试解析此bitmap，！！注意！！
						// 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析。写外部可能会有异步的问题。（开始解析时bitmap为空）
						Result result = new MultiFormatReader().decode(binaryBitmap, hints);
						WISERHelper.log().e("zxing:-->> " + result.toString());
						WISERHelper.toast().show(result.toString());
						scanSuccess(result.toString());
					} catch (Exception e) {
						e.printStackTrace();
						scanFail();
					}
				}
				break;

		}
	}

	// 解析
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		WISERHelper.log().e("zxing:-->>" + resultString);
		scanSuccess(resultString);
	}

	// 继续扫描
	private void continuePreview(SurfaceView surfaceView) {
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		initCamera(surfaceHolder);
		if (handler != null) {
			handler.restartPreviewAndDecode();
		}
	}

	// 初始化相机
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	// 初始化声音
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	// 播放声音
	@SuppressLint("MissingPermission") private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			assert vibrator != null;
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {

		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

	@Override public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	@Override protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
}