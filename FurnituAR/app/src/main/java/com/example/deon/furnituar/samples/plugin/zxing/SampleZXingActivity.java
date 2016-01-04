package com.wikitude.samples.plugin.zxing;

import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.example.deon.furnituar.SampleCamActivity;

public class SampleZXingActivity extends SampleCamActivity implements ZXingResultListener {

    private SampleZXingPlugin zXingPlugin;
    private Toast messageToast;
    private String lastText = "";

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.zXingPlugin = new SampleZXingPlugin("com.plugin.zxing", architectView, this);
        architectView.registerPlugin(zXingPlugin);
    }

    @Override
    public void onZxingDecodeTaskCompleted(Result result) {
        if (result != null) {
            if (messageToast != null) {
                messageToast.setText(result.getText());
            } else {
                messageToast = Toast.makeText(getApplicationContext(), result.getText(), Toast.LENGTH_SHORT);
            }
            messageToast.show();
        }
    }

}
