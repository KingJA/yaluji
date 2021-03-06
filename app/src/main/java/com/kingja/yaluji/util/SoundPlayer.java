package com.kingja.yaluji.util;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

/**
 * Description:TODO
 * Create Time:2018/11/14 23:15
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class SoundPlayer {
    private volatile static SoundPlayer mSoundPlayer;
    private static SoundPool soundPool;
    private Context context;
    private int currentSoundId;
    private int currentPlay;

    private SoundPlayer() {
    }

    public static SoundPlayer getInstance() {
        if (mSoundPlayer == null) {
            synchronized (SoundPlayer.class) {
                if (mSoundPlayer == null) {
                    mSoundPlayer = new SoundPlayer();
                }
            }
        }
        return mSoundPlayer;
    }

    /**
     * 适合播放声音短，文件小
     * 可以同时播放多种音频
     * 消耗资源较小
     */
    public void init(Context context) {
        this.context = context;
        if (Build.VERSION.SDK_INT >= 21) {
            SoundPool.Builder builder = new SoundPool.Builder();
            //传入音频的数量
            builder.setMaxStreams(1);
            //AudioAttributes是一个封装音频各种属性的类
            AudioAttributes.Builder attrBuilder = new AudioAttributes.Builder();
            //设置音频流的合适属性
            attrBuilder.setLegacyStreamType(AudioManager.STREAM_MUSIC);
            builder.setAudioAttributes(attrBuilder.build());
            soundPool = builder.build();
        } else {
            //第一个参数是可以支持的声音数量，第二个是声音类型，第三个是声音品质
            soundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        }
    }

    public void playVoice(int rawId) {
        playVoice(rawId, false);
    }

    public void playVoice(int rawId, boolean loop) {
//        soundPool.release();
        //第一个参数Context,第二个参数资源Id，第三个参数优先级
        currentSoundId = soundPool.load(context, rawId, 1);
        soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                currentPlay = soundPool.play(currentSoundId, 1, 1, 0, loop ? -1 : 0, 1);
            }
        });
        //第一个参数id，即传入池中的顺序，第二个和第三个参数为左右声道，第四个参数为优先级，第五个是否循环播放，0不循环，-1循环
        //最后一个参数播放比率，范围0.5到2，通常为1表示正常播放
//        soundPool.play(1, 1, 1, 0, 0, 1);
        //回收Pool中的资源
//        soundPool.release();
    }

    public void stop() {
        if (soundPool != null) {
            soundPool.stop(currentPlay);
        }
    }
}
