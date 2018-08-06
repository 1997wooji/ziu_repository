package com.example.ziu.a0803test;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    final int imgStartNo=R.drawable.bg01;
    final int imgEndNo=R.drawable.bg10;
    int imgCurNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ImageView imageView=(ImageView)findViewById(R.id.imageView);

        //imageView.setImageDrawable() 얘는 뭐야?
        imageView.setImageResource(imgStartNo);
        imgCurNo=imgStartNo;

        imageView.setOnTouchListener(new View.OnTouchListener(){

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                System.out.println(motionEvent.getX());
                System.out.println(imageView.getX()+(imageView.getWidth()/2));
                //imageView 혹은 view(클릭당한 그것)-getX(절대 좌표)
                if(motionEvent.getAction()==MotionEvent.ACTION_DOWN) {
                    if (motionEvent.getX() > imageView.getX() + (imageView.getWidth() / 2)) {

                        if (imgCurNo == imgEndNo) {
                            imgCurNo = imgStartNo;
                        } else {
                            imgCurNo = imgCurNo + 1;
                        }

                        imageView.setImageResource(imgCurNo);
                    } else {

                        if (imgCurNo == imgStartNo) {
                            imgCurNo = imgEndNo;
                        } else {
                            imgCurNo = imgCurNo - 1;
                        }

                        imageView.setImageResource(imgCurNo);
                    }
                }
                return true;
            }
        });
    }
}
