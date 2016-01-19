package com.ttnd.androidround1.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ttnd.androidround1.R;
import com.ttnd.androidround1.helper.DialogHelper;
import com.ttnd.androidround1.util.AppConstant;
import com.ttnd.androidround1.util.NetworkConnection;


/**
 * Created by ttnd on 19/1/16.
 */
public class WebViewActivity extends AppCompatActivity {

    private ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        progress = (ProgressBar) findViewById(R.id.progressBar);
        progress.setVisibility(View.GONE);

        String title = getIntent().getStringExtra(AppConstant.WEBVIEW_TITLE);
        String url = getIntent().getStringExtra(AppConstant.WEBVIEW_URL);
        setTitle(title);
        WebView webView = (WebView) findViewById(R.id.webView);

        if(NetworkConnection.isNetworkAvailable(this)){
            webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            webView.setWebViewClient(new CustomWebViewClient());
            webView.loadUrl(appendUrl(url));
        } else {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            webView.setWebViewClient(new CustomWebViewClient());
            webView.loadUrl(appendUrl(url));
        }

    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progress.setVisibility(View.GONE);
            WebViewActivity.this.progress.setProgress(100);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (!URLUtil.isValidUrl(url)) {
                new DialogHelper().showOneButtonDialog(AppConstant.FINISH_ACTIVITY, WebViewActivity.this);
            }else{
                progress.setVisibility(View.VISIBLE);
                WebViewActivity.this.progress.setProgress(0);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (!URLUtil.isValidUrl(failingUrl)) {
                if (failingUrl.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse(failingUrl));
                    startActivity(intent);
                    return;
                }else{
                    new DialogHelper().showOneButtonDialog(AppConstant.FINISH_ACTIVITY, WebViewActivity.this);
                }
            }else if(!NetworkConnection.isNetworkAvailable(WebViewActivity.this)){
                new DialogHelper().showOneButtonDialog(AppConstant.FINISH_WEBVIEW_ACTIVITY, WebViewActivity.this);
            }

        }
    }

    private String appendUrl(String url) {
        String appendUrl;
        if (!url.contains("?")) {
            appendUrl = url + "?";
        } else {
            appendUrl = url;
        }
        String append = "&userId=276" +
                "&appSecretKey=gvx32RFZLNGhmzYrfDCkb9jypTPa8Q" +
                "currencyCode=USD" +
                "offerId=10736598" +
                "selectedVouchers=[]";
        appendUrl = appendUrl + append;
        return appendUrl;
    }
}
