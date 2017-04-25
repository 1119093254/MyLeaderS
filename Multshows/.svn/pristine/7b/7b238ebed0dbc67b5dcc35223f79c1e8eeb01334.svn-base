package com.multshows.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.multshows.Fragment.VideoFragment;
import com.multshows.R;
import com.multshows.Utils.Utils;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class VideoMenu_Activity extends AppCompatActivity {
    // 录制按钮
    private Button btnRecordAudio;
    // 播放按钮
    private ImageView btnPlay;
    // 文件路径
    private String path = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videomenu);

        btnRecordAudio = (Button) findViewById(R.id.btn_record_audio);
        btnPlay = (ImageView) findViewById(R.id.play);

        btnRecordAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动拍摄的Activity
                Intent intent = new Intent(VideoMenu_Activity.this,VideoRecording_Activity.class);
                startActivityForResult(intent,200);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 显示播放页面
                VideoFragment bigPic = VideoFragment.newInstance(path);
                android.app.FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                transaction.replace(R.id.main_menu, bigPic);
                transaction.commit();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 200:
                if(resultCode == RESULT_OK) {
                    // 成功
                    path = data.getStringExtra("path");
                    Toast.makeText(VideoMenu_Activity.this,"存储路径为:"+path,Toast.LENGTH_SHORT).show();
                    // 通过路径获取第一帧的缩略图并显示
                    Bitmap bitmap = Utils.createVideoThumbnail(path);
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    drawable.setTileModeXY(Shader.TileMode.REPEAT , Shader.TileMode.REPEAT);
                    drawable.setDither(true);
                    btnPlay.setImageDrawable(drawable);
                } else {
                    // 失败
                }
                break;

        }
    }
}
