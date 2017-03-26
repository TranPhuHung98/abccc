package com.stdiohue.basestrcuture.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.stdiohue.basestrcuture.R;


/**
 * Created by kaquay on 6/16/16.
 */
public class DialogUtils {


    public static void showErrorDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    public static void showErrorDialog(Context context, int title, int message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.ic_launcher)
                .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    public static void showOkDialog(Context context, String title, String message, DialogInterface.OnClickListener onOkListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setNegativeButton(android.R.string.ok, onOkListener)
                .create().show();
    }

    public static Dialog showTips(Context context, String title, String des) {
        return showTips(context, title, des, null, null);
    }

    public static Dialog showTips(Context context, int title, int des) {
        return showTips(context, context.getString(title), context.getString(des));
    }

    public static Dialog showTips(Context context, int title, int des, int btn, DialogInterface.OnDismissListener dismissListener) {
        return showTips(context, context.getString(title), context.getString(des), context.getString(btn), dismissListener);
    }

    public static Dialog showTips(Context context, String title, String des, String btn, DialogInterface.OnDismissListener dismissListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, des);
        builder.setCancelable(true);
        builder.setPositiveButton(btn, null);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDismissListener(dismissListener);
        return dialog;
    }

    public static Dialog showConfirm(Context context, String title, String msg, DialogInterface.OnClickListener onYesListener, DialogInterface.OnClickListener onNoListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, msg);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.yes, onYesListener);
        builder.setNegativeButton(android.R.string.no, onNoListener);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog showConfirmNotCancelable(Context context, String title, String msg, DialogInterface.OnClickListener onYesListener, DialogInterface.OnClickListener onNoListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, msg);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, onYesListener);
        builder.setNegativeButton(android.R.string.no, onNoListener);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog showOkConfirm(Context context, String title, String msg, DialogInterface.OnClickListener onYesListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, msg);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.ok, onYesListener);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static Dialog showConfirm(Context context, int title, int msg, DialogInterface.OnClickListener onYesListener, DialogInterface.OnClickListener onNoListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, msg);
        builder.setCancelable(true);
        builder.setPositiveButton(android.R.string.yes, onYesListener);
        builder.setNegativeButton(android.R.string.no, onNoListener);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }

    public static Dialog showConfirmNotCancelable(Context context, int title, int msg, DialogInterface.OnClickListener onYesListener, DialogInterface.OnClickListener onNoListener) {
        AlertDialog.Builder builder = dialogBuilder(context, title, msg);
        builder.setCancelable(false);
        builder.setPositiveButton(android.R.string.yes, onYesListener);
        builder.setNegativeButton(android.R.string.no, onNoListener);
        Dialog dialog = builder.show();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, String title, String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (msg != null) {
            builder.setMessage(msg);
        }
        if (title != null) {
            builder.setTitle(title);
        }
        return builder;
    }

    public static AlertDialog.Builder dialogBuilder(Context context, int title, int msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(msg);
        builder.setTitle(title);
        return builder;
    }


}
