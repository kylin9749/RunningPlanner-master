package cn.bupt.runningplanner.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import cn.bupt.runningplanner.Dialog;
import cn.bupt.runningplanner.MainActivity;

public class Transform {
    //将图片转换成字节流
//    public byte[] setPicture(int id)
//    {
//        Bitmap bitmap= BitmapFactory.decodeResource(Dialog.res,id);
//        ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
//        return outputStream.toByteArray();
//    }

    //将字节流转换成图片
    public BitmapDrawable getPicture(byte[] b)
    {
        Bitmap bitmap= BitmapFactory.decodeByteArray(b,0,b.length,null);
        BitmapDrawable bitmapDrawable=new BitmapDrawable(bitmap);
        return bitmapDrawable;
    }

    //文件变字节流
    public static byte[] fileToBytes(String filePath) {
        byte[] buffer = null;
        File file = new File(filePath);

        FileInputStream fis = null;
        ByteArrayOutputStream bos = null;

        try {
            fis = new FileInputStream(file);
            bos = new ByteArrayOutputStream();

            byte[] b = new byte[1024];

            int n;

            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }

            buffer = bos.toByteArray();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (null != bos) {
                    bos.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally{
                try {
                    if(null!=fis){
                        fis.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return buffer;
    }
}
