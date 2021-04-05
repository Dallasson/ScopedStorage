package com.dz.scopedstorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.icu.util.Output;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.img);
        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = bitmapDrawable.getBitmap();
                saveBitmapToGallery(bitmap);
            }
        });
    }

    private void saveBitmapToGallery(Bitmap bitmap){
        OutputStream outputStream;

        try {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver contentResolver  = getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"Image_" + System.currentTimeMillis());
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/png");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES
                        + File.separator + "Scoped Storage Test Folder");

                Uri imageUrl  = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
                outputStream = contentResolver.openOutputStream(imageUrl);
                bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);

            }
        }catch (Exception e){

        }
    }
}