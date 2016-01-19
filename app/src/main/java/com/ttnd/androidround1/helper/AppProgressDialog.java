package com.ttnd.androidround1.helper;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by ttnd on 19/1/16.
 */
public class AppProgressDialog {
    private static AppProgressDialog ourInstance = new AppProgressDialog();

    public static AppProgressDialog getInstance() {
        return ourInstance;
    }

    private AppProgressDialog() {
    }

    public ProgressDialog showProgressDialog(String label,Context context){
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(label);
        progressDialog.show();
        return progressDialog;
    }
}
