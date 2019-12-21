package com.wiser.library.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.HashMap;

import com.wiser.library.helper.WISERHelper;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.util.Base64;
import android.view.View;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERBitmap {

	/**
	 * 获取视频的缩略图 先通过ThumbnailUtils来创建一个视频的缩略图，然后再利用ThumbnailUtils来生成指定大小的缩略图。
	 * 如果想要的缩略图的宽和高都小于MICRO_KIND，则类型要使用MICRO_KIND作为kind的值，这样会节省内存。
	 *
	 * @param videoPath
	 *            视频的路径
	 * @param width
	 *            指定输出视频缩略图的宽度
	 * @param height
	 *            指定输出视频缩略图的高度度
	 * @param kind
	 *            参照MediaStore.Images.Thumbnails类中的常量MINI_KIND和MICRO_KIND。
	 *            其中，MINI_KIND: 512 x 384，MICRO_KIND: 96 x 96
	 * @return 指定大小的视频缩略图
	 */
	public static Bitmap getVideoThumbnail(String videoPath, int width, int height, int kind) {
		Bitmap bitmap;
		// 获取视频的缩略图
		bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, kind);
		bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height, ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
		return bitmap;
	}

	/**
	 * 完美兼容 获取网络视频缩略图
	 *
	 * @param url
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH) public static Bitmap createVideoThumbnail(String url) {
		Bitmap bitmap = null;
		MediaMetadataRetriever retriever = new MediaMetadataRetriever();
		try {
			retriever.setDataSource(url, new HashMap<String, String>());
			bitmap = retriever.getFrameAtTime();
		} catch (IllegalArgumentException ignored) {
		} catch (RuntimeException ignored) {
		} finally {
			try {
				retriever.release();
			} catch (RuntimeException ignored) {
			}
		}
		return bitmap;
	}

	/**
	 * @param bitmap
	 *            原图
	 * @param edgeLength
	 *            希望得到的新位图的宽
	 * @return 缩放截取后的位图。
	 */
	public static Bitmap centerSquareScaleBitmap(Bitmap bitmap, int edgeLength) {
		if (null == bitmap || edgeLength <= 0) {
			return null;
		}

		Bitmap result = bitmap;
		int widthOrg = bitmap.getWidth();
		int heightOrg = bitmap.getHeight();
		if (widthOrg > heightOrg && widthOrg / heightOrg < 2) return result;
		// 从图中截取中间部分位图Y坐标。
		int yTopLeft = Math.abs(heightOrg - edgeLength) / 2;

		try {
			result = Bitmap.createBitmap(bitmap, 0, yTopLeft, widthOrg, edgeLength);
		} catch (Exception e) {
			return null;
		}

		return result;
	}

	/**
	 * 加载大图
	 * 
	 * @param context
	 * @param resId
	 * @return
	 */
	public static Bitmap getResBitmap(Context context, int resId) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;
		// 获取资源图片
		InputStream is = context.getResources().openRawResource(resId);
		return BitmapFactory.decodeStream(is, null, opt);

	}

	/**
	 * 图片文件转化为Bitmap
	 */
	public static Bitmap convertToBitmap(String path) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 获取圆角图片
	 *
	 * @param bitmap
	 * @param roundPx
	 * @return
	 */
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Bitmap output = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, w, h);
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);

		return output;
	}

	/**
	 * 长宽等比例放大缩小
	 *
	 * @param bitmap
	 * @param x
	 * @param y
	 * @return
	 */
	public static Bitmap zoom(Bitmap bitmap, float x, float y) {
		Matrix matrix = new Matrix();
		float scaleWidth = (x) / bitmap.getWidth();
		float scaleHeight = (y) / bitmap.getHeight();
		matrix.setScale(scaleWidth, scaleHeight);
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	/**
	 * 按宽/高缩放图片到指定大小并进行裁剪得到中间部分图片
	 *
	 * @param bitmap
	 *            源bitmap
	 * @param w
	 *            缩放后指定的宽度
	 * @param h
	 *            缩放后指定的高度
	 * @return 缩放后的中间部分图片
	 */
	public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		if (width >= height && width / height <= 2) return bitmap;
		float scaleWidth, scaleHeight, x, y;
		Bitmap newBmp;
		Matrix matrix = new Matrix();
		if (width > height) {
			scaleWidth = ((float) h / height);
			scaleHeight = ((float) h / height);
			x = (width - w * height / h) / 2;// 获取bitmap源文件中x做表需要偏移的像数大小
			y = 0;
		} else if (width < height) {
			scaleWidth = ((float) w / width);
			scaleHeight = ((float) w / width);
			x = 0;
			y = (float) ((height - h * width / w) / 1.4);// 获取bitmap源文件中y做表需要偏移的像数大小
		} else {
			scaleWidth = ((float) w / width);
			scaleHeight = ((float) w / width);
			x = 0;
			y = 0;
		}
		matrix.postScale(scaleWidth, scaleHeight);
		try {
			newBmp = Bitmap.createBitmap(bitmap, (int) x, (int) y / 2, (int) (width - x), (int) (height - y), matrix, true);// createBitmap()方法中定义的参数x+width要小于或等于bitmap.getWidth()，y+height要小于或等于bitmap.getHeight()
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return newBmp;
	}

	/**
	 * 获取压缩比例整数数值
	 *
	 * @param options
	 * @param w
	 * @param h
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int w, int h) {
		int height = options.outHeight;
		int width = options.outWidth;
		int inSampleSize = 1;
		if (w == 0 || h == 0) return 1;
		if (height > h || width > w) {
			int heightRatio = Math.round((float) height / (float) h);
			int widthRatio = Math.round((float) width / (float) w);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	/**
	 * 获取压缩图片Bitmap
	 *
	 * @param path
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap getCompressBitmap(String path, int w, int h) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = calculateInSampleSize(options, w, h);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 压缩某个输入流中的图片，可以解决网络输入流压缩问题，并得到图片对象
	 *
	 * @param is
	 * @param reqsW
	 * @param reqsH
	 * @return Bitmap {@link Bitmap}
	 */
	public final static Bitmap compressBitmap(InputStream is, int reqsW, int reqsH) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ReadableByteChannel channel = Channels.newChannel(is);
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			while (channel.read(buffer) != -1) {
				buffer.flip();
				while (buffer.hasRemaining())
					baos.write(buffer.get());
				buffer.clear();
			}
			byte[] bts = baos.toByteArray();
			Bitmap bitmap = compressBitmap(bts, reqsW, reqsH);
			is.close();
			channel.close();
			baos.close();
			return bitmap;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 压缩指定byte[]图片，并得到压缩后的图像
	 *
	 * @param bts
	 * @param reqsW
	 * @param reqsH
	 * @return
	 */
	public final static Bitmap compressBitmap(byte[] bts, int reqsW, int reqsH) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
		options.inSampleSize = calculateInSampleSize(options, reqsW, reqsH);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
	}

	/**
	 * 讀取本地或網絡圖片轉化Bitmap類型
	 *
	 * @param url
	 * @return
	 */
	public static Bitmap getLocalOrNetBitmap(String url) {
		Bitmap bitmap;
		InputStream in;
		BufferedOutputStream out;
		try {
			in = new BufferedInputStream(new URL(url).openStream(), 2 * 1024);
			final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
			out = new BufferedOutputStream(dataStream, 1024);
			copy(in, out);
			out.flush();
			byte[] data = dataStream.toByteArray();
			bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void copy(InputStream in, OutputStream out) throws IOException {
		byte[] b = new byte[1024];
		int read;
		while ((read = in.read(b)) != -1) {
			out.write(b, 0, read);
		}
	}

	/**
	 * Bitmap轉byte
	 *
	 * @param bm
	 * @return
	 */
	public static byte[] bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		return baos.toByteArray();
	}

	/**
	 * 壓縮到100kb轉byte方法
	 *
	 * @param bitmap
	 * @param size
	 * @return
	 */
	public static byte[] compressBitmap(Bitmap bitmap, float size) {
		if (bitmap == null || getSizeOfBitmap(bitmap) <= size) {
			return null;// 如果图片本身的大小已经小于这个大小了，就没必要进行压缩
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 如果签名是png的话，则不管quality是多少，都不会进行质量的压缩
		int quality = 100;
		while (baos.toByteArray().length / 1024f > size) {
			quality = quality - 4;// 每次都减少4
			baos.reset();// 重置baos即清空baos
			if (quality <= 0) {
				break;
			}
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
		}
		return baos.toByteArray();
	}

	private static Integer getSizeOfBitmap(Bitmap bitmap) {
		return bitmap.getRowBytes() * bitmap.getHeight();
	}

	/**
	 * 保存Bitmap到本地
	 *
	 * @param bitmap
	 * @param path
	 * @param picName
	 */
	public static void saveBitmapToLocal(Bitmap bitmap, String path, String picName) {
		WISERHelper.log().i("wxy", "开始保存");
		File f = new File(path, picName);
		if (f.exists()) {
			f.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(f);
			bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
			out.flush();
			out.close();
			WISERHelper.log().i("wxy", "已经保存");
		} catch (Exception e) {
			e.printStackTrace();
			WISERHelper.log().i("wxy", "保存失败");
		}
	}

	/**
	 * 根据原图和变长绘制圆形图片
	 *
	 * @param source
	 * @param min
	 * @return
	 */
	public static Bitmap createCircleImage(Bitmap source, int min) {
		final Paint paint = new Paint();
		paint.setAntiAlias(true);
		Bitmap target = Bitmap.createBitmap(min, min, Bitmap.Config.ARGB_8888);
		/**
		 * 产生一个同样大小的画布
		 */
		Canvas canvas = new Canvas(target);
		/**
		 * 首先绘制圆形
		 */
		canvas.drawCircle(min / 2, min / 2, min / 2, paint);
		/**
		 * 使用SRC_IN
		 */
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		/**
		 * 绘制图片
		 */
		canvas.drawBitmap(source, 0, 0, paint);
		return target;
	}

	/**
	 * 获取压缩图片Bitmap
	 *
	 * @param context
	 * @param uri
	 * @param w
	 * @param h
	 * @return
	 */
	public static Bitmap getCompressBitmap(Context context, Uri uri, int w, int h) {
		String path;
		if (WISERString.cutSignString(uri.toString(), ":", true).equals("file")) {
			path = WISERString.cutSignString(uri.toString(), ":", false);
		} else {
			path = WISERHelper.fileCacheManage().getPathFromUri(context, uri);
		}
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = calculateInSampleSize(options, w, h);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(path, options);
	}

	/**
	 * 读取图片属性：旋转的角度
	 *
	 * @param path
	 *            图片绝对路径图片绝对路径
	 * @return degree旋转的角度
	 */
	public static int readPhotoDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	/**
	 * 旋转图片
	 *
	 * @param angle
	 *
	 * @param bitmap
	 *
	 * @return Bitmap
	 */
	public static Bitmap rotateImageView(int angle, Bitmap bitmap) {
		// 旋转图片 动作
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		// 创建新的图片
		Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		return resizedBitmap;
	}

	/**
	 * 图片压缩路径
	 */
	public static Bitmap getSmallBitmap(String filePath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, 480, 800);
		options.inJustDecodeBounds = false;
		if (readPhotoDegree(filePath) > 0) {
			return rotateImageView(readPhotoDegree(filePath), BitmapFactory.decodeFile(filePath, options));
		}
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 图片压缩路径
	 */
	public static Bitmap getSmallBitmap(String filePath, int width, int height) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);
		options.inSampleSize = calculateInSampleSize(options, width, height);
		options.inJustDecodeBounds = false;
		if (readPhotoDegree(filePath) > 0) {
			return rotateImageView(readPhotoDegree(filePath), BitmapFactory.decodeFile(filePath, options));
		}
		return BitmapFactory.decodeFile(filePath, options);
	}

	/**
	 * 压缩之后转Base64为的字符串方便上传服务器
	 *
	 * @param filePath
	 * @return
	 */
	public static String bitmapToString(String filePath) {
		Bitmap bitmap = getSmallBitmap(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (bitmap != null) {
			bitmap.compress(Bitmap.CompressFormat.JPEG, 40, baos);
			byte[] bytes = baos.toByteArray();
			return Base64.encodeToString(bytes, Base64.DEFAULT);
		} else {
			return "";
		}
	}

	/**
	 * 压缩之后转Base64为的字符串方便上传服务器
	 *
	 * @param filePath
	 *            路径
	 * @param quality
	 *            质量
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 */
	public static String bitmapToString(String filePath, int quality, int width, int height) {
		Bitmap bitmap = getSmallBitmap(filePath, width, height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (bitmap != null) {
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			byte[] bytes = baos.toByteArray();
			return Base64.encodeToString(bytes, Base64.DEFAULT);
		} else {
			return "";
		}
	}

	/**
	 * 压缩之后转Base64为的字符串方便上传服务器
	 *
	 * @param filePath
	 *            路径
	 * @param quality
	 *            质量
	 * @param width
	 *            宽
	 * @param height
	 *            高
	 * @return
	 */
	public static String bitmapToPath(String targetFilePath, String targetFileName, String filePath, int quality, int width, int height) {
		Bitmap bitmap = getSmallBitmap(filePath, width, height);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (bitmap != null) {
			File f = new File(targetFilePath, targetFileName);
			if (!f.exists()) f.mkdirs();
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);
			byte[] bytes = baos.toByteArray();
			FileOutputStream fos;
			try {
				fos = new FileOutputStream(f);
				fos.write(bytes);
				fos.flush();
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return f.getPath();
		} else {
			return "";
		}
	}

	/**
	 * 根据网络URL转bitmap
	 *
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmapForUrl(String url) {
		Bitmap bm = null;
		try {
			URL iconUrl = new URL(url);
			URLConnection conn = iconUrl.openConnection();
			HttpURLConnection http = (HttpURLConnection) conn;

			int length = http.getContentLength();

			conn.connect();
			// 获得图像的字符流
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is, length);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();// 关闭流
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bm;
	}

	/**
	 * 将View转换成Bitmap
	 *
	 * @param addViewContent
	 * @return
	 */

	public static Bitmap getViewBitmap(View addViewContent) {

		addViewContent.setDrawingCacheEnabled(true);

		addViewContent.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
		addViewContent.layout(0, 0, addViewContent.getMeasuredWidth(), addViewContent.getMeasuredHeight());

		addViewContent.buildDrawingCache();
		Bitmap cacheBitmap = addViewContent.getDrawingCache();
		Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

		return bitmap;
	}

	/**
	 * 图片转成string
	 *
	 * @param bitmap
	 * @return
	 */
	public static String convertBitmapToString(Bitmap bitmap) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();// outputstream
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
		byte[] appicon = baos.toByteArray();// 转为byte数组
		return Base64.encodeToString(appicon, Base64.DEFAULT);

	}

	/**
	 * string转成bitmap
	 *
	 * @param st
	 */
	public static Bitmap convertStringToBitmap(String st) {
		// OutputStream out;
		Bitmap bitmap = null;
		try {
			// out = new FileOutputStream("/sdcard/aa.jpg");
			byte[] bitmapArray;
			bitmapArray = Base64.decode(st, Base64.DEFAULT);
			bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
			// bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
			return bitmap;
		} catch (Exception e) {
			return null;
		}
	}

	public static void postOnAnimation(View view, Runnable runnable) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			view.postOnAnimation(runnable);
		} else {
			view.postDelayed(runnable, 1000 / 60);
		}
	}
}
