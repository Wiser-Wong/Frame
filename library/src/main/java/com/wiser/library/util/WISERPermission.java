package com.wiser.library.util;

import org.apache.commons.lang3.StringUtils;

import com.wiser.library.base.WISERActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.v4.content.ContextCompat;

/**
 * @author Wiser
 * 
 *         权限管理
 */
public class WISERPermission {

	private static WISERPermission	wiserPermission;

	private WISERActivity			activity;

	WISERPermission(WISERActivity activity) {
		this.activity = activity;
	}

	public static WISERPermission with(WISERActivity activity) {
		synchronized (WISERPermission.class) {
			if (wiserPermission == null) {
				wiserPermission = new WISERPermission(activity);
			}
		}
		return wiserPermission;
	}

	// 判断是否有权限
	public boolean hasPermission(String permission) {
		if (StringUtils.isBlank(permission) || activity == null) return false;
		if (Build.VERSION.SDK_INT < 23) {
			switch (permission) {
				case Manifest.permission.RECORD_AUDIO:// 录音
					return hasAudioRecordPermission();
				case Manifest.permission.CAMERA:// 拍照
					return hasCameraPermission();
				default:
					return true;
			}
		} else {
			// 6.0
			if (ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED) {
				// 该权限已经有了
				switch (permission) {
					case Manifest.permission.CAMERA:// 拍照
						return hasCameraPermission();
					default:
						return true;
				}
			} else {
				return false;
			}
		}
	}

	/**
	 * 是否拍照权限允许
	 * 
	 * @return
	 */
	public boolean hasCameraPermission() {
		Camera mCamera = null;
		try {
			mCamera = Camera.open(0);
			Camera.Parameters mParameters = mCamera.getParameters();
			mCamera.setParameters(mParameters);
			mCamera.release();
			mCamera = null;
			return true;
		} catch (Exception e) {
			if (mCamera != null) {
				mCamera.release();
				mCamera = null;
			}
			// 暂时发现只有华为荣耀6型号H60-L01不管允许和禁止mCamera.getParameters()都抛异常所以做此判断
			return Build.MODEL.equals("H60-L01") && Build.VERSION.RELEASE.equals("4.4.2");
		}
	}

	/**
	 * 是否录音权限允许
	 * 
	 * @return
	 */
	public boolean hasAudioRecordPermission() {
		// 音频获取源
		int audioSource = MediaRecorder.AudioSource.MIC;
		// 设置音频采样率，44100是目前的标准，但是某些设备仍然支持22050，16000，11025
		int sampleRateInHz = 44100;
		// 设置音频的录制的声道CHANNEL_IN_STEREO为双声道，CHANNEL_CONFIGURATION_MONO为单声道
		int channelConfig = AudioFormat.CHANNEL_IN_STEREO;
		// 音频数据格式:PCM 16位每个样本。保证设备支持。PCM 8位每个样本。不一定能得到设备支持。
		int audioFormat = AudioFormat.ENCODING_PCM_16BIT;
		// 缓冲区字节大小
		int bufferSizeInBytes = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
		AudioRecord audioRecord = new AudioRecord(audioSource, sampleRateInHz, channelConfig, audioFormat, bufferSizeInBytes);
		// 开始录制音频
		try {
			// 防止某些手机崩溃，例如联想
			audioRecord.startRecording();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		/**
		 * 根据开始录音判断是否有录音权限
		 */
		if (audioRecord.getRecordingState() != AudioRecord.RECORDSTATE_RECORDING) {
			if (audioRecord != null) {
				audioRecord.stop();
				audioRecord.release();
				audioRecord = null;
			}
			return false;
		}
		byte[] bytes = new byte[1024];
		int readSize = audioRecord.read(bytes, 0, 1024);
		if (readSize == AudioRecord.ERROR_INVALID_OPERATION || readSize <= 0) {
			if (audioRecord != null) {
				audioRecord.stop();
				audioRecord.release();
				audioRecord = null;
			}
			return false;
		}
		audioRecord.stop();
		audioRecord.release();
		audioRecord = null;
		return true;
	}

}
