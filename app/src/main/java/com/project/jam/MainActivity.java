package com.project.jam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;



public class MainActivity extends AppCompatActivity implements MyCallback {
    TextView tv;
    Button startButton;
    Button stopButton;
    //AudioDispatcher dispatcher;
    PitchDetection pitchDetection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.moin);
        pitchDetection = new PitchDetection(this);
        startButton = findViewById(R.id.startDetection);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pitchDetection.enablePitchDetection();
            }
        });
        stopButton = findViewById(R.id.stopDetection);
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pitchDetection.stopPitchDetection();
            }
        });
    }

    @Override
    public void updateText(String string) {
        tv.setText(string);
    }
    /**
    private void stopPitchDetection() {
        System.out.println(Thread.activeCount());
        System.out.println(Thread.getAllStackTraces().toString());
        dispatcher.stop();
    }

    void enablePitchDetection(){
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
        if (dispatcher != null) {
            dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, new PitchDetectionHandler() {

                @Override
                public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                        AudioEvent audioEvent) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            float pitch = pitchDetectionResult.getPitch();
                            try {
                                if(pitch > 0)
                                    tv.setText(Float.toString(pitch));
                                System.out.println(pitch);
                            }catch (Exception e){
                                e.printStackTrace();
                            };
                        }
                    });
                }
            }));
            new Thread(dispatcher, "Audio Dispatcher").start();
        }
    }
    */


}