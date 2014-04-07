package com.xuek.main;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xuek.voice.R;

public class MsgAdapter extends BaseAdapter {
	private List<VoiceMsg> msgs;
	private LayoutInflater mFlater;
	public MsgAdapter(Context context, List<VoiceMsg> msgs) {
		this.msgs = msgs;
		mFlater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return msgs.size();
	}

	@Override
	public VoiceMsg getItem(int arg0) {
		return msgs.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (null == convertView) {
			convertView = mFlater.inflate(R.layout.item	, null);
			holder = new ViewHolder();
			holder.text = (TextView) convertView.findViewById(R.id.text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.text.setText(getItem(position).getUuid());
		return convertView;
	}
	static class ViewHolder{
		TextView text;
	}
}
