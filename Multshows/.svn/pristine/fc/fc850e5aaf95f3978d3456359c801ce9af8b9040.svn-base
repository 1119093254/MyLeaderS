package com.multshows.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 作用：
 * Author：张凯  on 2016/8/25 10:20
 * Blog: bukevin@github.io
 */
public class NetUtil {
    /**
     * 一般来说用一个生成一个UUID的话，会可靠很多，这里就不考虑这个了
     * 而且一般来说上传文件最好用BASE64进行编码，你只要用BASE64不用的符号就可以保证不冲突了。
     * 尤其是上传二进制文件时，其中很可能有\r、\n之类的控制字符，有时还可能出现最高位被错误处理的问题，所以必须进行编码。
     */
    public static final String BOUNDARY = "--my_boundary--";

    /**
     * 普通字符串数据
     * @param textParams
     * @param ds
     * @throws Exception
     */
    public static void writeStringParams(Map<String, String> textParams,
                                         DataOutputStream ds) throws Exception {
        Set<String> keySet = textParams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
            String name = it.next();
            String value = textParams.get(name);
            ds.writeBytes("--" + BOUNDARY + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"\r\n");
            ds.writeBytes("\r\n");
            value = value + "\r\n";
            ds.write(value.getBytes());

        }
    }

    /**
     * 文件数据
     * @param fileparams
     * @param ds
     * @throws Exception
     */
    public static void writeFileParams(Map<String, File> fileparams,
                                       DataOutputStream ds) throws Exception {
        Set<String> keySet = fileparams.keySet();
        for (Iterator<String> it = keySet.iterator(); it.hasNext();) {
            String name = it.next();
            File value = fileparams.get(name);
            ds.writeBytes("--" + BOUNDARY + "\r\n");
            ds.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\""
                    + URLEncoder.encode(value.getName(), "UTF-8") + "\"\r\n");
            ds.writeBytes("Content-Type:application/octet-stream \r\n");
            ds.writeBytes("\r\n");
            ds.write(getBytes(value));
            ds.writeBytes("\r\n");
        }
    }

    /**
     * 把文件转换成字节数组
     * @param f
     * @return
     * @throws Exception
     */
    public static byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }

    /**
     * 添加结尾数据
     * @param ds
     * @throws Exception
     */
    public static void paramsEnd(DataOutputStream ds) throws Exception {
        ds.writeBytes("--" + BOUNDARY + "--" + "\r\n");
        ds.writeBytes("\r\n");
    }

    public static String readString(InputStream is) {
        return new String(readBytes(is));
    }

    public static byte[] readBytes(InputStream is) {
        try {
            byte[] buffer = new byte[1024];
            int len = -1;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            baos.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把一个文件转化为字节
     * @param file
     * @return   byte[]
     * @throws Exception
     */
    public static byte[] getByte(File file) throws Exception
    {
        byte[] bytes = null;
        if(file!=null)
        {
            InputStream is = new FileInputStream(file);
            int length = (int) file.length();
            if(length>Integer.MAX_VALUE)   //当文件的长度超过了int的最大值
            {
                System.out.println("this file is max ");
                return null;
            }
            bytes = new byte[length];
            int offset = 0;
            int numRead = 0;
            while(offset<bytes.length&&(numRead=is.read(bytes,offset,bytes.length-offset))>=0)
            {
                offset+=numRead;
            }
            //如果得到的字节长度和file实际的长度不一致就可能出错了
            if(offset<bytes.length)
            {
                System.out.println("file length is error");
                return null;
            }
            is.close();
        }
        return bytes;
    }

    /**
     * 对图片进行压缩
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image,int imageSize) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while ( baos.toByteArray().length / 1024 > imageSize) { //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }

        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
}
