package com.stdiohue.basestrcuture.view.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.stdiohue.basestrcuture.kqbus.Dispatcher;
import com.stdiohue.basestrcuture.utils.AlertBridgeListener;


/**
 * Created by kaquay on 11/7/16.
 */

public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    protected T viewDataBinding;

    private AlertBridgeListener mAlertBridgeListener;


    @LayoutRes
    protected abstract int getLayoutId();

    protected abstract void init(@Nullable View view);

    protected abstract void screenResume();

    protected abstract void screenPause();

    protected abstract void screenStart(@Nullable Bundle saveInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return viewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        screenStart(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    @Override
    public void onResume() {
        super.onResume();
        Dispatcher.getInstance().register(this);
        screenResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.gc();
    }

    @Override
    public void onPause() {
        super.onPause();
        Dispatcher.getInstance().unregister(this);
        dismissProgress();
        screenPause();
    }

    protected AlertBridgeListener getAlertBridge() {
        return mAlertBridgeListener;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AlertBridgeListener) {
            mAlertBridgeListener = (AlertBridgeListener) context;
        }
    }

    /**
     * A dialog showing a progress indicator and an optional text message or
     * view.
     */
    protected ProgressDialog mProgressDialog;
    /**
     * Showing a quick little message for the user. It's will be cancel when
     * finish activity.
     */
    private Toast mToast;

    /**
     * show progress dialog.
     *
     * @param msgResId
     */
    public void showProgress(int msgResId) {
        showProgress(msgResId, null);
    }


    /**
     * show progress dialog.
     *
     * @param msgResId
     */
    public void showProgress(String msgResId) {
        showProgress(msgResId, null);
    }

    /**
     *
     */
    public void showProgress() {
        //TODO add string later, or create a bridge for non-freeze loading. Maybe we not use it because not good for UX
//        showProgress(R.string.loading, null);
    }

    /**
     * @param msgResId
     * @param keyListener
     */
    public void showProgress(int msgResId,
                             DialogInterface.OnKeyListener keyListener) {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);

        if (msgResId != 0) {
            mProgressDialog.setMessage(getResources().getString(msgResId));
        }

        if (keyListener != null) {
            mProgressDialog.setOnKeyListener(keyListener);

        } else {
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    /**
     * @param msgResId
     * @param keyListener
     */
    public void showProgress(@NonNull String msgResId,
                             DialogInterface.OnKeyListener keyListener) {

        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setIndeterminate(true);
        // mProgressDialog.setCancelable(false);

        if (keyListener != null) {
            mProgressDialog.setOnKeyListener(keyListener);

        } else {
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    /**
     * @param isCancel
     */
    public void setCancelableProgress(boolean isCancel) {
        if (mProgressDialog != null) {
            mProgressDialog.setCancelable(true);
        }
    }

    /**
     * cancel progress dialog.
     */
    public void dismissProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    /**
     * Show message by the Toast object, it will be canceled when finish this
     * activity.
     *
     * @param msg message want to show
     */
    public final void showToastMessage(final CharSequence msg) {
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), "",
                    Toast.LENGTH_LONG);
        }

        if (mToast != null) {
            // Cancel last message toast
            if (mToast.getView().isShown()) {
                mToast.cancel();
            }
            mToast.setText(msg);
            mToast.show();
        }
    }

    /**
     * Show message by the Toast object, it will be canceled when finish this
     * activity.
     *
     * @param resId id of message wants to show
     */
    public final void showToastMessage(final int resId) {
        if (!isAdded())
            return;
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), "",
                    Toast.LENGTH_LONG);
        }

        if (mToast != null) {
            if (mToast.getView().isShown()) {
                mToast.cancel();
            }
            mToast.setText(getString(resId));
            mToast.show();
        }
    }

    /**
     * Cancel toast.
     */
    public final void cancelToast() {
        if (mToast != null && mToast.getView().isShown()) {
            mToast.cancel();
        }
    }

    /**
     * Show toast message, the message is not canceled when finish this
     * activity.
     *
     * @param msg message wants to show
     */
    public final void showToastMessageNotRelease(final CharSequence msg) {
        Toast toast = Toast.makeText(getActivity(), "",
                Toast.LENGTH_LONG);
        if (toast != null) {
            // Cancel last message toast
            if (toast.getView().isShown()) {
                toast.cancel();
            }
            toast.setText(msg);
            toast.show();
        }
    }

    /**
     * Show toast message, the message is not canceled when finish this
     * activity.
     *
     * @param resId message wants to show
     */
    public final void showToastMessageNotRelease(final int resId) {
        Toast toast = Toast.makeText(getActivity(), "",
                Toast.LENGTH_LONG);
        if (toast != null) {
            if (!toast.getView().isShown()) {
                toast.cancel();
            }
            toast.setText(getString(resId));
            toast.show();
        }
    }
}
