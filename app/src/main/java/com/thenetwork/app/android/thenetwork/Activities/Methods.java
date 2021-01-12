package com.thenetwork.app.android.thenetwork.Activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

class Methods {

    private Context mContext;

    public Methods(Context context){
        this.mContext = context;
    }

    public boolean checkIfAlreadyHavePermission() {
        int result = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result != PackageManager.PERMISSION_GRANTED;
    }

}