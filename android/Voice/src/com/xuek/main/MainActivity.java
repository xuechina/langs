package com.xuek.main;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import android.app.Activity;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.speex.recorder.SpeexPlayer;
import com.speex.recorder.SpeexRecorder;
import com.speex.utils.Base64Util;
import com.xuek.voice.R;

/**
 * @author xuechinahb@gmail.com
 *
 */
public class MainActivity extends Activity implements  OnTouchListener, OnItemClickListener{
	
	private ListView mListView;
	private Button mBtn;
	
	private String SPEEX_PATH;
	
	private List<VoiceMsg> mMsgs;
	private MsgAdapter mAdapter;
	
	private RecordingDialog mDialog;
	private String mAudioStr;
	
	private SpeexRecorder recorderInstance;
	private UUID uuid;
	private SpeexPlayer splayer;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}
	
	public void init(){
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setOnItemClickListener(this);
		mBtn = (Button) findViewById(R.id.btn);
		mBtn.setOnTouchListener(this);
		mMsgs = new ArrayList<VoiceMsg>();
		mAdapter = new MsgAdapter(this, mMsgs);
		mListView.setAdapter(mAdapter);
		SPEEX_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "speex" + File.separator;
		File dir = new File(SPEEX_PATH);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		/*
		if (dir.exists()) {
			for(File file : dir.listFiles()) {
				file.delete();
			}
		}
		File f = new File(PATH + "hello.txt");
		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
	}

	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		try {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				mDialog = new RecordingDialog(
						MainActivity.this);
				mDialog.getWindow().setBackgroundDrawableResource(
						android.R.color.transparent);
				mDialog.show();
				startRecor(mHandler);
				break;
			case MotionEvent.ACTION_UP:
				mDialog.dismiss();
				recorderInstance.setRecording(false);
				VoiceMsg msg  = new VoiceMsg();
				msg.setUuid(uuid.toString());
				mMsgs.add(msg);
				mAdapter.notifyDataSetChanged();
				
				break;
			default:
				break;
			}
			
		} catch (Exception e) {
		}
		return false;
	}
	
	public void onItemClick(android.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		try {
			//Base64Util.decode(mAudioStr, SPEEX_PATH + "asdff" + ".spx");//解码出音频文件		
			} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		startPlayer(mAdapter.getItem(arg2).getUuid());
	};
	public void startRecor(Handler mHandler) {
		uuid = UUID.randomUUID();
		String fileName = SPEEX_PATH + uuid.toString() + ".spx";
		try {
			recorderInstance = new SpeexRecorder(fileName, mHandler);
			Thread th = new Thread(recorderInstance);
			th.start();
			recorderInstance.setRecording(true);
		} catch (Exception e) {
			recorderInstance.setRecording(false);
			e.printStackTrace();
		} catch (Error e) {
			recorderInstance.setRecording(false);
			e.printStackTrace();
		}
	}

	Handler mHandler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			int what = msg.what;
			mHandler.removeMessages(what);
			switch (what) {
			case 999:
				try {
					mAudioStr = Base64Util.encode(SPEEX_PATH + uuid.toString() + ".spx");//编码成String
				} catch (Exception e) {
					e.printStackTrace();
				}
			default:
				break;
			}

			return false;
		}
	});

	public void startPlayer(String uuid) {
		try {
			System.out.println("startPlay--uuid = " + uuid);
			splayer = new SpeexPlayer(mHandler);
			splayer.setFileName(SPEEX_PATH + uuid + ".spx");
			 splayer.setStreamType(AudioManager.STREAM_MUSIC);
			splayer.startPlay();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
