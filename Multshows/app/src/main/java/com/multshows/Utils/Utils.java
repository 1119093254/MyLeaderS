package com.multshows.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lsy on 15-6-14.
 */
public class Utils {
    public static Bitmap createVideoThumbnail(String filePath) {
        // MediaMetadataRetriever is available on API Level 8
        // but is hidden until API Level 10
        Class<?> clazz = null;
        Object instance = null;
        try {
            clazz = Class.forName("android.media.MediaMetadataRetriever");
            instance = clazz.newInstance();

            Method method = clazz.getMethod("setDataSource", String.class);
            method.invoke(instance, filePath);

            // The method name changes between API Level 9 and 10.
            if (Build.VERSION.SDK_INT <= 9) {
                return (Bitmap) clazz.getMethod("captureFrame").invoke(instance);
            } else {
                byte[] data = (byte[]) clazz.getMethod("getEmbeddedPicture").invoke(instance);
                if (data != null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    if (bitmap != null) return bitmap;
                }
                return (Bitmap) clazz.getMethod("getFrameAtTime").invoke(instance);
            }
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } catch (InstantiationException e) {
            CLog.e("TAG", "createVideoThumbnail", e);
        } catch (InvocationTargetException e) {
            CLog.e("TAG", "createVideoThumbnail", e);
        } catch (ClassNotFoundException e) {
            CLog.e("TAG", "createVideoThumbnail", e);
        } catch (NoSuchMethodException e) {
            CLog.e("TAG", "createVideoThumbnail", e);
        } catch (IllegalAccessException e) {
            CLog.e("TAG", "createVideoThumbnail", e);
        } finally {
            try {
                if (instance != null) {
                    clazz.getMethod("release").invoke(instance);
                }
            } catch (Exception ignored) {
            }
        }
        return null;
    }
    public static void CopyStream(InputStream is, OutputStream os) throws IOException {
        final int buffer_size = 1024;

        byte[] bytes = new byte[buffer_size];
        for (;;) {
            int count = is.read(bytes, 0, buffer_size);
            if (count == -1)
                break;

            os.write(bytes, 0, count);
            // is.close();
            // os.close();
        }
    }

    public static float getScreenDensity(Context context) {
        return context.getResources().getDisplayMetrics().density;
    }

    public static int dip2px(Context context, float px) {
        final float scale = getScreenDensity(context);
        return (int) (px * scale + 0.5);
    }
}
