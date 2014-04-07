package com.speex.recorder;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import com.speex.encode.SpeexEncoder;


public class SpeexRecorder implements Runnable {

	private volatile boolean isRecording;
	private final Object mutex = new Object();
	private static final int frequency = 8000;
	private static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	public static int packagesize = 160;
	private String fileName = null;
	private Handler handler;
	private AudioRecord recordInstance;
	public SpeexRecorder(String fileName,Handler handler) {
		super();
		this.fileName = fileName;
		this.handler=handler;
	}

	public void run() {

		SpeexEncoder encoder = new SpeexEncoder(this.fileName,handler);
		Thread encodeThread = new Thread(encoder);
		encoder.setRecording(true);
		encodeThread.start();

		synchronized (mutex) {
			while (!this.isRecording) {
				try {
					mutex.wait();
				} catch (InterruptedException e) {
					throw new IllegalStateException("Wait() interrupted!", e);
				}
			}
		}
		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

		int bufferRead = 0;
		int bufferSize = AudioRecord.getMinBufferSize(frequency, AudioFormat.CHANNEL_IN_MONO, audioEncoding);

		short[] tempBuffer = new short[packagesize];
		if(recordInstance!=null&&recordInstance.getState()==AudioRecord.STATE_INITIALIZED ){
			recordInstance.stop();
			recordInstance.release();
		}
		recordInstance = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, AudioFormat.CHANNEL_IN_MONO, audioEncoding,
				bufferSize);

		try {
			recordInstance.startRecording();
			while (this.isRecording) {
				bufferRead = recordInstance.read(tempBuffer, 0, packagesize);
				// bufferRead = recordInstance.read(tempBuffer, 0, 320);
				if (bufferRead == AudioRecord.ERROR_INVALID_OPERATION) {
					throw new IllegalStateException("read() returned AudioRecord.ERROR_INVALID_OPERATION");
				} else if (bufferRead == AudioRecord.ERROR_BAD_VALUE) {
					throw new IllegalStateException("read() returned AudioRecord.ERROR_BAD_VALUE");
				} else if (bufferRead == AudioRecord.ERROR_INVALID_OPERATION) {
					throw new IllegalStateException("read() returned AudioRecord.ERROR_INVALID_OPERATION");
				}
				
				encoder.putData(tempBuffer, bufferRead);
				int v = 0;
				for (int i = 0; i < tempBuffer.length; i++) {
					v += tempBuffer[i] * tempBuffer[i];
				}
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
				
			}
			if(recordInstance!=null&&recordInstance.getState()==AudioRecord.STATE_INITIALIZED ){
				recordInstance.stop();
				recordInstance.release();
			}
			//tell encoder to stop.
			encoder.setRecording(false);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			setRecording(false);
			if(recordInstance!=null&&recordInstance.getState()==AudioRecord.STATE_INITIALIZED ){
				recordInstance.stop();
				recordInstance.release();
			}
			encoder.setRecording(false);
			handler.sendEmptyMessage(250);
		} catch (Exception e) {
			e.printStackTrace();
			setRecording(false);
			if(recordInstance!=null&&recordInstance.getState()==AudioRecord.STATE_INITIALIZED ){
				recordInstance.stop();
				recordInstance.release();
			}
			encoder.setRecording(false);
			handler.sendEmptyMessage(250);
		}catch (Error e) {
			e.printStackTrace();
			setRecording(false);
			if(recordInstance!=null&&recordInstance.getState()==AudioRecord.STATE_INITIALIZED ){
				recordInstance.stop();
				recordInstance.release();
			}
			encoder.setRecording(false);
			handler.sendEmptyMessage(250);
		}

	}

	public void setRecording(boolean isRecording) {
		synchronized (mutex) {
			this.isRecording = isRecording;
			if (this.isRecording) {
				mutex.notify();
			}
		}
	}

	public boolean isRecording() {
		synchronized (mutex) {
			return isRecording;
		}
	}
}
