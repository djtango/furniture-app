package com.wikitude.samples.plugin.zxing;

import android.os.AsyncTask;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.wikitude.architect.plugin.Frame;

public class ZXingDecodeTask extends AsyncTask<Frame, Void, Result> {

    private final ZXingResultListener resultListener;

    public ZXingDecodeTask(ZXingResultListener resultListener) {
        this.resultListener = resultListener;
    }

    @Override
    protected Result doInBackground(Frame... params) {
        Result result = null;
        Frame cameraFrame = params[0];

        PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(cameraFrame.getData(),
                cameraFrame.getSize().getWidth(), cameraFrame.getSize().getHeight(), 0, 0,
                cameraFrame.getSize().getWidth(), cameraFrame.getSize().getHeight(), false);

        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        MultiFormatReader reader = new MultiFormatReader();
        try {
            result = reader.decode(bitmap);
        } catch (NotFoundException e) {
        }

        return result;
    }

    @Override
    protected void onPostExecute(Result result) {
        resultListener.onZxingDecodeTaskCompleted(result);
    }
}
