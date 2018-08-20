package com.wiser.library.util;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import com.wiser.library.helper.WISERHelper;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Wiser
 * @version 版本
 */
public class WISERFile {

    /**
     * 检测SD卡状态判断SdCard存在并且是可用的
     *
     * @return
     */
    public static boolean isSDCardMounted() {
        // 判断SdCard是否存在并且是可用的
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) && Environment.getExternalStorageDirectory().canWrite();
    }

    /**
     * 检测SD卡是否是read-only模式，是否可读
     *
     * @return
     */
    public static boolean isSDCardMountedReadOnly() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED_READ_ONLY);
    }

    /**
     * 文件转byte
     *
     * @param file 文件
     * @return
     */
    public static byte[] fileToBytes(File file) {
        int byte_size = 1024;
        byte[] b = new byte[byte_size];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(
                    byte_size);
            for (int length; (length = fileInputStream.read(b)) != -1; ) {
                outputStream.write(b, 0, length);
            }
            fileInputStream.close();
            outputStream.close();
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得指定文件的byte数组
     *
     * @param filePath 文件路径
     */
    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer;
    }

    /**
     * 根据byte数组，生成文件
     */
    public static void getFileForBytes(byte[] bfile, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + "\\" + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建一个文件夹
     *
     * @param path
     * @return
     */
    public static boolean createFolder(String path) {
        File localFile = new File(path);
        return ((isSDCardMounted()) || (isSDCardMountedReadOnly())) && (!localFile.exists()) && localFile.mkdirs();
    }

    /**
     * 删除指定文件
     *
     * @param file
     * @return
     */
    public static boolean deleteFile(File file) {
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

    /**
     * 清空文件夹
     *
     * @param dirPath
     */
    public static void clearFolder(String dirPath) {
        File dir = new File(dirPath);//清空文件夹
        File[] files = dir.listFiles();
        if (null != files && files.length > 0) {
            for (File file : files) {
                file.delete();
            }
        }
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param deleteThisPath
     * @param filePath
     * @return
     */
    public static void deleteFolderAllFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (File file1 : files) {
                        deleteFolderAllFile(file1.getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //获取Assets文件夹中txt文件字符串
    public static String getAssetsTxtFile(Activity activity) {
        InputStream is;  //获得AssetManger 对象, 调用其open 方法取得  对应的inputStream对象
        StringBuilder buffer = new StringBuilder();
        try {
            is = activity.getResources().getAssets().open("station_json.txt", AssetManager.ACCESS_BUFFER);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char) ch);
            }
            in.close();
            return buffer.toString();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    /**
     * 获取文件夹里面默认第一个文件路径
     *
     * @param dirPath
     */
    public static String getFileNamePath(String dirPath) {
        File dir = new File(dirPath);//清空文件夹
        File[] files = dir.listFiles();
        if (null != files && files.length > 0) {
            return files[0].getPath();
        }
        return null;
    }

    /***
     * SD卡剩余空间
     *
     * @return
     */
    public static long getAvailableStorage() {
        String storageDirectory = null;
        storageDirectory = Environment.getExternalStorageDirectory().toString();
        try {
            StatFs stat = new StatFs(storageDirectory);
            return ((long) stat.getAvailableBlocks() * (long) stat
                    .getBlockSize());
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    /**
     * 检测文件是否存在
     *
     * @param path 文件路径
     * @return
     */
    public static boolean isFileExist(String path) {

        try {
            return new File(path).exists();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return false;
    }

    /**
     * 检测文件是否存在
     *
     * @param file 文件路径
     * @return
     */
    public static boolean isFileExist(File file) {

        if (file == null) return false;
        try {
            return file.exists();
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return false;
    }

    /**
     * 根据Uri获取路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getPathFromUri(Context context, Uri uri) {
        String res = null;
        String[] datas = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, datas, null,
                null, null);
        if (cursor == null)
            return null;
        if (cursor.moveToFirst()) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }

    /***
     * 根据url得到一个文件名
     *
     * @param url apk网络地址
     * @return
     */
    public static String getApkNameFromUrl(String url) {
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }

        if ("".equals(filename.trim())) {// 如果获取不到文件名称
            filename = UUID.randomUUID() + ".apk";// 默认取一个文件名
        }
        return filename;
    }

    /***
     * 根据url得到一个文件名
     *
     * @param url pdf网络地址
     * @return
     */
    public static String getPdfNameFromUrl(String url) {
        // 通过 ‘？’ 和 ‘/’ 判断文件名
        int index = url.lastIndexOf('?');
        String filename;
        if (index > 1) {
            filename = url.substring(url.lastIndexOf('/') + 1, index);
        } else {
            filename = url.substring(url.lastIndexOf('/') + 1);
        }

        if ("".equals(filename.trim())) {// 如果获取不到文件名称
            filename = UUID.randomUUID() + ".pdf";// 默认取一个文件名
        }
        return filename;
    }

    /**
     * 根据Uri获取文件路径
     *
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 获取指定文件夹下所有文件列表
     *
     * @param folderPath
     * @return
     */
    public static List<String> folderEntry(String folderPath) {
        List<String> mFileList = new ArrayList<>();
        if (!TextUtils.isEmpty(folderPath)) {
            try {
                File file = new File(folderPath);
                if (file.isDirectory()) {// 如果是文件夹
                    File[] files = file.listFiles();
                    for (File file1 : files) {
                        mFileList.add(file1.getName());
                    }
                    return mFileList;
                }
            } catch (Exception e) {
                e.printStackTrace();
                WISERHelper.log().e("获取指定文件夹下所有文件列表失败。。");
            }
        }
        return null;
    }

    /**
     * 指定文件路径下按指定名称删除该文件
     *
     * @param filePath
     * @param fileName
     */
    public static void deleteByFileName(String filePath, String fileName) {
        File file = new File(filePath);
        if (!TextUtils.isEmpty(filePath)) {
            File[] files = file.listFiles();
            for (File fileCall : files) {
                if (fileCall.getName().equals(fileName)) {
                    fileCall.delete();
                }
            }
        }
    }

    /**
     * 是否删除空文件夹
     *
     * @param folderName     文件夹名称
     * @param isDeleteFolder true删除，false 不删除
     */
    public static void isDeleteEmptyFolder(String folderName,
                                           boolean isDeleteFolder) {

        File file = new File(folderName);
        if (isDeleteFolder) {
            if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                file.delete();
            }
        }
    }

    /**
     * 写入SD卡文件
     *
     * @param filePath    文件路径
     * @param fileContent 字符串
     * @param fileName    写入的文件名称
     */
    public static boolean writeSdCardFile(String filePath, String fileName,
                                          String fileContent) {
        File file = new File(filePath, fileName);
        if (isSDCardMounted()) {
            try {
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(fileContent.getBytes());
                fos.close();
                Log.v("wxy", "写入文件成功");
            } catch (Exception e) {
                Log.v("wxy", "写入文件失败");
            }
        } else {
            Log.e("wxy", "此时SDcard不存在或者不能进行读写操作的");
        }
        return false;
    }

    /**
     * 读取SD卡文件
     *
     * @param fileName
     * @return
     */
    public static String readSdCardFile(String fileName, String filePath) {
        File file = new File(filePath, fileName);
        if (isSDCardMounted()) {
            try {
                FileInputStream inputStream = new FileInputStream(file);
                byte[] b = new byte[inputStream.available()];
                inputStream.read(b);
                Log.v("wxy", "读取文件成功");
                return new String(b);
            } catch (Exception e) {
                Log.v("wxy", "读取失败");
            }
        } else {
            Log.e("wxy", "此时SDcard不存在或者不能进行读写操作的");
        }
        return null;
    }

}
