package com.ttnd.androidround1.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.ttnd.androidround1.R;
import com.ttnd.androidround1.adapters.WebviewListAdapter;
import com.ttnd.androidround1.app.AppController;
import com.ttnd.androidround1.helper.AppProgressDialog;
import com.ttnd.androidround1.helper.DialogHelper;
import com.ttnd.androidround1.model.WebViewModel;
import com.ttnd.androidround1.util.AppConstant;
import com.ttnd.androidround1.util.NetworkConnection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private String mUrl = "http://appcontent.hotelquickly.com/en/1/android/index.json";
    private ArrayList<WebViewModel> webViewModelArrayList;
    private ListView mListView;
    private ProgressDialog mProgressDialog;
    private AlertDialog mAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webViewModelArrayList = new ArrayList<WebViewModel>();
        mListView = (ListView)findViewById(R.id.listview_main);

        if(NetworkConnection.isNetworkAvailable(this)){
            hitApi();
        } else {
            mAlertDialog = new DialogHelper().showOneButtonDialog(AppConstant.NETWORK_RETRY,this);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,WebViewActivity.class);
                intent.putExtra(AppConstant.WEBVIEW_TITLE,((WebViewModel)webViewModelArrayList.get(position)).getPage_title());
                intent.putExtra(AppConstant.WEBVIEW_URL,((WebViewModel)webViewModelArrayList.get(position)).getUrl());
                startActivity(intent);
            }
        });
    }

    public void hitApi(){
        if(mAlertDialog != null && mAlertDialog.isShowing())
            mAlertDialog.dismiss();
        mProgressDialog = AppProgressDialog.getInstance().showProgressDialog("Loading...",this);
        getDataFromServer();
    }

    private void getDataFromServer() {
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                mUrl, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                        Iterator x = response.keys();

                        while (x.hasNext()){
                            String key = (String) x.next();
                            try {
                                WebViewModel model = new WebViewModel();
                                JSONObject jsonObject = response.getJSONObject(key);
                                model.setUrl(jsonObject.getString("url"));
                                model.setPage_title(key);
                                webViewModelArrayList.add(model);
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }

                        WebviewListAdapter webviewListAdapter = new WebviewListAdapter(MainActivity.this, webViewModelArrayList);
                        mListView.setAdapter(webviewListAdapter);
                        mProgressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                mProgressDialog.dismiss();
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

}
