package com.multshows.Utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 描述：保存图片到本地 和 存放缓存 功能
 * 作者：贾强胜
 * 时间：2016.7.16
 */
public class SavePhoto_toLocal_Utils {
    private static final String SAVE_PIC_PATH= Environment.getExternalStorageState().
            equalsIgnoreCase(Environment.MEDIA_MOUNTED) ? Environment.
            getExternalStorageDirectory().getAbsolutePath():"/mnt/sdcard";//保存到SD卡
    private static final String SAVE_REAL_PATH = SAVE_PIC_PATH+"/MultshowsPic";//保存的确切位置

     //保存至本地路径下的文件夹
    public static void saveFile(Bitmap bm, String fileName, String path, Context context) throws IOException {
        String subForder = SAVE_REAL_PATH +path;
        //判断该路径是否存在，如否，则创建
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        Toast.makeText(context, "保存本地成功", Toast.LENGTH_SHORT).show();
        //广播通知更新图库
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(myCaptureFile);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    //保存至本地路径APP临时缓存文件夹
    public static File saveCacheFile(Bitmap bm, String fileName, Context context) throws IOException {
        String subForder=context.getExternalCacheDir().getPath();
        CLog.e("PATH",subForder);
        //String subForder = SAVE_REAL_PATH +path;
        //判断该路径是否存在，如否，则创建
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
        bos.flush();
        bos.close();
        //Toast.makeText(context, "缓存成功", Toast.LENGTH_SHORT).show();
        return myCaptureFile;
    }
    //保存至本地路径APP临时缓存文件夹
    public static File saveCacheFile_2(Bitmap bm, String fileName, Context context) throws IOException {
        String subForder=context.getExternalCacheDir().getPath();
        CLog.e("PATH",subForder);
        //String subForder = SAVE_REAL_PATH +path;
        //判断该路径是否存在，如否，则创建
        File foder = new File(subForder);
        if (!foder.exists()) {
            foder.mkdirs();
        }
        File myCaptureFile = new File(subForder, fileName);
        if (!myCaptureFile.exists()) {
            myCaptureFile.createNewFile();
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 100是压缩率不压缩，如果是30就是压缩70%，压缩后的存放在baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            bm.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        baos.writeTo(new FileOutputStream(myCaptureFile));
        baos.flush();
        baos.close();
        //Toast.makeText(context, "缓存成功", Toast.LENGTH_SHORT).show();
        return myCaptureFile;
    }

    //压缩成100k以下上传

    public static String getcomImageBase64(Bitmap bitmap) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 100是压缩率不压缩，如果是30就是压缩70%，压缩后的存放在baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            options -= 10;// 每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中
        }
        byte[] bytes = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return Base64.encodeToString(bytes, Base64.DEFAULT);

    }


}
