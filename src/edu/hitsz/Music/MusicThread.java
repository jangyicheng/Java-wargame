package edu.hitsz.Music;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.sound.sampled.DataLine.Info;

public class MusicThread extends Thread {


    //音频文件名
    private String filename;
    private AudioFormat audioFormat;
    private byte[] samples;
    private int mode;//为0时静音，为1时正常播放，为2时循环播放

    private final Object lock = new Object();
    // 标志变量，用于控制线程的暂停和恢复
    private boolean paused = false;
    public MusicThread(String filename) {
        //初始化filename
        this.filename = filename;
        this.mode=1;//默认为1
        reverseMusic();
    }
    public MusicThread(String filename,int mode) {
        //初始化filename
        this.filename = filename;
        this.mode=mode;
        reverseMusic();
    }

    public void reverseMusic() {
        try {
            //定义一个AudioInputStream用于接收输入的音频数据，使用AudioSystem来获取音频的音频输入流
            AudioInputStream stream = AudioSystem.getAudioInputStream(new File(filename));
            //用AudioFormat来获取AudioInputStream的格式
            audioFormat = stream.getFormat();
            samples = getSamples(stream);
        } catch (UnsupportedAudioFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public byte[] getSamples(AudioInputStream stream) {
        int size = (int) (stream.getFrameLength() * audioFormat.getFrameSize());
        byte[] samples = new byte[size];
        DataInputStream dataInputStream = new DataInputStream(stream);
        try {
            dataInputStream.readFully(samples);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return samples;
    }


public void play(InputStream source){
    int size = (int) (audioFormat.getFrameSize() * audioFormat.getSampleRate());
    byte[] buffer = new byte[size];
    SourceDataLine dataLine = null;
    DataLine.Info info = new DataLine.Info(SourceDataLine.class, audioFormat);
    try {
        dataLine = (SourceDataLine) AudioSystem.getLine(info);
        dataLine.open(audioFormat, size);
    } catch (LineUnavailableException e) {
        e.printStackTrace();
    }
    dataLine.start();
    int numBytesRead = 0;
    try {
            while ((numBytesRead != -1 && mode != 0) || (mode == 2 && numBytesRead == -1)) {//线程不死或循环播放阶段
                synchronized (lock) {
                    while (paused) {
                        try {
                            lock.wait(); // 线程进入等待状态
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                numBytesRead = source.read(buffer, 0, buffer.length);
                if (mode == 0)
                    try {
                        // 停止运行，并等待信号
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                if (numBytesRead != -1) {
                    dataLine.write(buffer, 0, numBytesRead);
                }
                if (numBytesRead == -1 && mode == 2) {
                    // 重新播放音频
                    source.reset();
                    numBytesRead = 0;
                }
            //}
        }
    }catch(IOException  e) {
        e.printStackTrace();
    }
    dataLine.drain();
    dataLine.close();
    }



    public void setMode(int mode)
    {
        this.mode=mode;
    }
    @Override
    public void run() {
        InputStream stream = new ByteArrayInputStream(samples);
        play(stream);
    }
    // 暂停线程
    public void pauseThread() {
        synchronized (lock) {
            paused = true;
        }
    }

    // 恢复线程
    public void resumeThread() {
        synchronized (lock) {
            paused = false;
            lock.notify(); // 唤醒正在等待的线程
        }
    }
}


