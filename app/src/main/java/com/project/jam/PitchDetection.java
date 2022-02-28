package com.project.jam;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;

public class PitchDetection {
    AudioDispatcher dispatcher;
    MyCallback myCallback = null;

    public PitchDetection(MyCallback callback) {
        this.myCallback = callback;
    }


    void stopPitchDetection() {
        System.out.println(Thread.activeCount());
        System.out.println(Thread.getAllStackTraces().toString());
        try {
            dispatcher.stop();
        }catch (IllegalStateException e){
            e.printStackTrace();
        }

    }

    void enablePitchDetection(){
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050, 1024, 0);
        if (dispatcher != null) {
            dispatcher.addAudioProcessor(new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.FFT_YIN, 22050, 1024, new PitchDetectionHandler() {

                @Override
                public void handlePitch(PitchDetectionResult pitchDetectionResult,
                                        AudioEvent audioEvent) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            float pitch = pitchDetectionResult.getPitch();
                            try {
                                if(pitch > 0)
                                    myCallback.updateText(Float.toString(pitch));

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

}
