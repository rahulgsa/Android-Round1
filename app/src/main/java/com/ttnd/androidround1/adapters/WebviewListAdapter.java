package com.ttnd.androidround1.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ttnd.androidround1.R;
import com.ttnd.androidround1.model.WebViewModel;

import java.util.ArrayList;

/**
 * Created by intelligrape on 19/1/16.
 */
public class WebviewListAdapter extends BaseAdapter{

    private ArrayList<WebViewModel> mWebViewModelArrayList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public WebviewListAdapter(Context context, ArrayList<WebViewModel> webViewModelArrayList){
        mContext = context;
        mWebViewModelArrayList = webViewModelArrayList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mWebViewModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null || convertView.getTag() == null) {
            convertView = mLayoutInflater.inflate(R.layout.list_item_main, null);
            holder = new ViewHolder();
            convertView.setTag(holder);

            holder.textView_title = (TextView)convertView.findViewById(R.id.text_webview_list_item);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView_title.setText(((WebViewModel)mWebViewModelArrayList.get(position)).getPage_title());

        return convertView;
    }

    static class ViewHolder {
        TextView textView_title;
    }
}
