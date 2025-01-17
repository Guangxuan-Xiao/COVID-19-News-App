package com.java.yangjianke.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.java.yangjianke.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Date;

public class PicUtil {
    /**
     * 图片下载
     */
    public static void saveImage(ImageView shot, Context context) {
        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        //创建文件夹
        File directory = new File(externalStorageDirectory, "yilanpicture");
        if (!directory.exists())
            directory.mkdir();
        Bitmap drawingCache = shot.getDrawingCache();
        try {
            File file = new File(directory, new Date().getTime() + ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            drawingCache.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri uri = Uri.fromFile(file);
            intent.setData(uri);
            context.sendBroadcast(intent);
            Toast.makeText(context,
                    "保存成功", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context,
                    "保存失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
    图片显示
     */
    public static void showImage(ImageView showImView, Context context,String ImageUrl) {
        Glide.with(context)
                .load(ImageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(showImView);
    }
}

