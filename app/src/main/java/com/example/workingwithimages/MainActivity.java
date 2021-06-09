package com.example.workingwithimages;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ImageView Image;
    //Bitmap bmap;
    final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Image = (ImageView) findViewById(R.id.imgV);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bmap = BitmapFactory.decodeStream(input);
            return bmap;
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void FindOnlineImage(View view)
    {
        Thread th = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    final Bitmap temp = getBitmapFromURL("https://picsum.photos/200/300");
                    mHandler.post(new Runnable()
                    {
                        public void run() {
                            Image.setImageBitmap(temp);
                        }
                    });
                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }
}