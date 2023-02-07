package com.example.skiljevic_rekorder;

import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.MediaRecorder;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;
import java.io.File;
import java.io.IOException;
import android.Manifest.permission;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {







    public String folder_main = "Audio_Folder";
    public File f = null;
    public File f2 = null;

    public MediaRecorder recorder = null;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        f = new File(getExternalFilesDir(null), folder_main);
        f2 = new File(getExternalFilesDir("mp3") + "/" + folder_main + "/");
        if (!f.exists())
        {
            f.mkdir();
        }

        if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission.RECORD_AUDIO },
                    10);
        }
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] { permission.WRITE_EXTERNAL_STORAGE },
                    10);
        }
        Button bt1;
        bt1 = (Button) findViewById(R.id.button1);
        Button bt2 = (Button) findViewById(R.id.button2);
        Button bt3 = (Button) findViewById(R.id.button3);
        ConstraintLayout l1 = (ConstraintLayout)  findViewById(R.id.layout1);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (bt2.getVisibility() == View.VISIBLE)
                {
                    recorder.resume();
                }
                else
                {
                    startRecord();
                }
                l1.setBackgroundColor(Color.GREEN);
                bt2.setVisibility(View.VISIBLE);
                bt3.setVisibility(View.VISIBLE);


            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                l1.setBackgroundColor(Color.YELLOW);
                recorder.pause();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                l1.setBackgroundColor(Color.RED);
                bt2.setVisibility(View.INVISIBLE);
                bt3.setVisibility(View.INVISIBLE);
                stopRecord();

            }
        });
    }


    public void startRecord()
    {
        recorder = new MediaRecorder();
        recorder.reset();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        File output = new File(getExternalFilesDir("mp3") + "/" + folder_main + "output.mp3");
        recorder.setOutputFile(output.getAbsoluteFile());

        try
        {
            recorder.prepare();
            recorder.start();
        }
        catch(IllegalStateException e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    public void stopRecord()
    {

        if (recorder != null) {
            try
            {
                recorder.stop();
                recorder.reset();

                recorder.release();
                recorder = null;
            }
            catch (RuntimeException e)
            {
                Toast.makeText(MainActivity.this,"RUNTIME E",Toast.LENGTH_SHORT).show();
            }


        }

    }
}