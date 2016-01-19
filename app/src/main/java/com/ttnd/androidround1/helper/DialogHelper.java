package com.ttnd.androidround1.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.ttnd.androidround1.R;
import com.ttnd.androidround1.ui.MainActivity;
import com.ttnd.androidround1.util.AppConstant;
import com.ttnd.androidround1.util.NetworkConnection;

/**
 * Created by ttnd on 19/1/16.
 */
public class DialogHelper {

    public AlertDialog showOneButtonDialog(final int requestCode, final Activity activity) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        String btnLabel = "";
        if (requestCode == AppConstant.NETWORK_RETRY) {
            builder.setMessage(activity.getString(R.string.label_not_network));
            btnLabel = activity.getString(R.string.label_retry);
        } else if (requestCode == AppConstant.FINISH_ACTIVITY) {
            builder.setMessage(activity.getString(R.string.label_not_valid_url));
            btnLabel = activity.getString(R.string.label_ok);
        } else if (requestCode == AppConstant.FINISH_WEBVIEW_ACTIVITY) {
            builder.setMessage(activity.getString(R.string.label_not_network));
            btnLabel = activity.getString(R.string.label_ok);
        }

        builder.setPositiveButton(btnLabel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (requestCode == AppConstant.FINISH_ACTIVITY) {
                    activity.finish();
                } else if (requestCode == AppConstant.NETWORK_RETRY) {
                    if (NetworkConnection.isNetworkAvailable(activity)) {
                        ((MainActivity) activity).hitApi();
                    } else {
                        showOneButtonDialog(requestCode, activity);
                    }
                } else if(requestCode == AppConstant.FINISH_WEBVIEW_ACTIVITY){
                    activity.finish();
                }
            }
        });
        final AlertDialog alert = builder.create();
        alert.show();
        return alert;
    }

}
