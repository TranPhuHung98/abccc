package com.stdiohue.basestrcuture.utils;

import android.support.annotation.StringRes;

/**
 * Created by hung.nguyendk on 2/25/17.
 */

public interface AlertBridgeListener {
    void showSnackErrorMessage(@StringRes int messageId);

    void showSnackInformationMessage(@StringRes int messageId);

    void showWarningMessage(@StringRes int messageId);


    void showSnackErrorMessage(String messageId);

    void showSnackInformationMessage(String messageId);

    void showWarningMessage(String messageId);
}
