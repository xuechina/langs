/**
 * 
 */
package com.speex.recorder;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.speex.encode.SpeexDecoder;

public class SpeexPlayer {
	private String fileName = null;
	private int streamType;
	private SpeexDecoder speexdec = null;
	private Handler handler;
	private int id;
	private boolean fauceStop =  false;
	public SpeexPlayer(Handler handler ) {
		this.handler=handler;
		try {
			speexdec = new SpeexDecoder();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void startPlay() {
		RecordPlayThread rpt = new RecordPlayThread();
		Thread th = new Thread(rpt);
		th.start();
		fauceStop = false;
	}
	public void stopPlay(){
		fauceStop = true;
		speexdec.stop();
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setStreamType(int streamType){
		this.streamType = streamType;
		
	}
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public boolean isPlay() {
		return isPlay;
	}

	public void setPlay(boolean isPlay) {
		this.isPlay = isPlay;
	}

	public boolean isPlay = true;

	class RecordPlayThread extends Thread {

		public void run() {
			try {
				isPlay=true;
				if (speexdec != null){
					speexdec.setSrcPath(fileName);
					speexdec.setStreamType(streamType);
					speexdec.decode();
				}
				if(!fauceStop){
					Message msg=new Message();
					msg.what=1;
					Bundle bundle = new Bundle();
					bundle.putInt("id", id);
					msg.setData(bundle);
					handler.sendMessage(msg);
				}
				isPlay=false;
			} catch (Exception t) {
				t.printStackTrace();
				isPlay=false;
				Message msg=new Message();
				msg.what=1;
				handler.sendMessage(msg);
			}
		}
	};
}
