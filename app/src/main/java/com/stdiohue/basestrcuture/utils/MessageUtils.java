package com.stdiohue.basestrcuture.utils;

import android.app.Activity;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.stdiohue.basestrcuture.R;

/**
 * Created by hung.nguyendk on 3/26/17.
 */

public class MessageUtils {
    public static void showErrorMessage(Activity activity,
                                        CharSequence message) {
        if (message != null) {
            Snackbar snackbar = Snackbar
                    .make(activity.findViewById(android.R.id.content),
                            message,
                            Snackbar.LENGTH_LONG);
            View view = snackbar.getView();
            view.setBackgroundColor(Color.RED);
            snackbar.show();
        }
    }

    public static void showErrorMessage(Activity activity,
                                        @StringRes int message) {
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void showInformationMessage(Activity activity,
                                              @StringRes int message) {
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.BLUE);
        snackbar.show();
    }

    public static void showInformationMessage(Activity activity,
                                              @NonNull CharSequence message) {
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.BLUE);
        snackbar.show();
    }

    public static void showWarningMessage(Activity activity, @StringRes int message) {
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.YELLOW);
        snackbar.show();
    }

    public static void showWarningMessage(Activity activity, @NonNull CharSequence message) {
        Snackbar snackbar = Snackbar
                .make(activity.findViewById(android.R.id.content),
                        message,
                        Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        view.setBackgroundColor(Color.YELLOW);
        snackbar.show();
    }

    public static void showNetworkNotConnectedError(Activity activity) {
        MessageUtils.showErrorMessage(activity,
                activity.getString(R.string.msg_no_network));
    }

}