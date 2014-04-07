package com.speex.encode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.os.Handler;

import com.speex.writer.SpeexWriter;
public class SpeexEncoder implements Runnable {

	private final Object mutex = new Object();
	private Speex speex = new Speex();
	// private long ts;
	public static int encoder_packagesize = 1024;
	private byte[] processedData = new byte[encoder_packagesize];

	List<ReadData> list = null;
	private volatile boolean isRecording;
	private String fileName;
	private Handler handler;
	public SpeexEncoder(String fileName,Handler handler) {
		super();
		speex.init();
		list = Collections.synchronizedList(new LinkedList<ReadData>());
		this.fileName = fileName;
		this.handler = handler;
	}
	

	public void run() {

		SpeexWriter fileWriter = new SpeexWriter(fileName,handler);
		Thread consumerThread = new Thread((Runnable) fileWriter);
		fileWriter.setRecording(true);
		consumerThread.start();

		android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);

		int getSize = 0;
		while (this.isRecording()) {
			if (list.size() == 0) {
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}
			if (list.size() > 0) {
				synchronized (mutex) {
					ReadData rawdata = list.remove(0);
					getSize = speex.encode(rawdata.ready, 0, processedData, rawdata.size);

				}
				if (getSize > 0) {
					fileWriter.putData(processedData, getSize);
					processedData = new byte[encoder_packagesize];
				}
			}
		}
		fileWriter.setRecording(false);
	}
	public void putData(short[] data, int size) {
		ReadData rd = new ReadData();
		synchronized (mutex) {
			rd.size = size;
			System.arraycopy(data, 0, rd.ready, 0, size);
			list.add(rd);
		}
	}

	public void setRecording(boolean isRecording) {
		synchronized (mutex) {
			this.isRecording = isRecording;
		}
	}

	public boolean isRecording() {
		synchronized (mutex) {
			return isRecording;
		}
	}

	class ReadData {
		private int size;
		private short[] ready = new short[encoder_packagesize];
	}
}
