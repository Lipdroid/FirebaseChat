package com.example.lipuhossain.firebasechat.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.lipuhossain.firebasechat.R;
import com.example.lipuhossain.firebasechat.adapters.holders.UserStatusHolder;
import com.example.lipuhossain.firebasechat.model.UserObject;

import java.util.List;

/**
 * Created by lipuhossain on 2/17/17.
 */

public class UserListAdapter extends BaseAdapter {

    private  Context mContext;
    private  List<UserObject> mListData;
    private UserStatusHolder mHolder;

    public UserListAdapter(Context context, List<UserObject> mListData) {
        mContext = context;
        this.mListData = mListData;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.item_user, null);
            mHolder = new UserStatusHolder();
            mHolder.tv_user_name = (TextView) convertView.findViewById(R.id.tv_user_name);
            mHolder.status = (ImageView) convertView.findViewById(R.id.status);

            convertView.setTag(mHolder);
        } else {
            mHolder = (UserStatusHolder) convertView.getTag();
        }

        UserObject item = mListData.get(position);
        mHolder.tv_user_name.setText(item.getUser_name());
        if(!item.getUser_status().equals("Online")){
            mHolder.status.setImageResource(R.drawable.offline);
        }else {
            mHolder.status.setImageResource(R.drawable.online);
        }

        return convertView;
    }
}
