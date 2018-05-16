package cn.bupt.runningplanner.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class bitmapUtil {

    public static final String PATH = "/";
    private static ImageView showImageView;
    private static String mUrl;

    public static String getFileName(String url) {
        int index = url.lastIndexOf("/") + 1;
        return url.substring(index);
    }

    public static void setImageBitmap(String url, ImageView iv) {
        showImageView = iv;
        mUrl = url;
        Bitmap loacalBitmap = bitmapUtil.getLoacalBitmap(Environment.getExternalStorageDirectory() + bitmapUtil.PATH + bitmapUtil.getFileName(url));
        if (loacalBitmap != null) {
            showImageView.setImageBitmap(loacalBitmap);
            Log.d("ImageUtils", "本地获取");
        } else {
            Log.d("ImageUtils", "网络获取");
            new DownImgAsyncTask().execute(url);
        }
    }
    public static void setImageBitmapKylin(String url, ImageView iv) {
        showImageView = iv;
        mUrl = url;
        Bitmap loacalBitmap = bitmapUtil.getLoacalBitmap(Environment.getExternalStorageDirectory() +url);
        if (loacalBitmap != null) {
            showImageView.setImageBitmap(loacalBitmap);
            Log.d("ImageUtils", "本地获取");
        } else {
            Log.d("ImageUtils", "网络获取");
            new DownImgAsyncTask().execute(url);
        }
    }

    /** * 通过URL地址获取Bitmap对象 * @Title: getBitMapByUrl * @param @param url * @param @return * @param @throws Exception * @return Bitmap * @throws */
    public static  Bitmap getBitmapByUrl(final String url) {
        URL fileUrl = null;
        InputStream is = null;
        Bitmap bitmap = null;
        try {
            fileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) fileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            Log.d("shaoace", "1");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != is) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            is = null;
        }
        return bitmap;
    }

    //保存图片到本地路径
    public static File saveImage(Bitmap bmp, String path, String fileName) {
        File appDir = new File(path);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /** * @Title: getLoacalBitmap * @Description: 加载本地图片 * @param @param url 本地路径 * @param @return * @return Bitmap * @throws */
    public static Bitmap getLoacalBitmap(String url) {
        if (url != null) {
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(url);
                return BitmapFactory.decodeStream(fis); // /把流转化为Bitmap图片
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return null;
            } finally {
                if(fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fis = null;
                }
            }
        } else {
            return null;
        }
    }

    static class DownImgAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap b = getBitmapByUrl(params[0]);
            return b;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            if(result!=null){
                File file = saveImage(result, Environment.getExternalStorageDirectory() + PATH, getFileName(mUrl));
                Log.d("ImageUtils", file.toString());
                showImageView.setImageBitmap(result);
            }
        }
    }


}