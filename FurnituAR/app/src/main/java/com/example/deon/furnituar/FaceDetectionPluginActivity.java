package com.example.deon.furnituar;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
import android.view.WindowManager;

import com.wikitude.sdksamples.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FaceDetectionPluginActivity extends SampleCamActivity {

    private File _cascadeFile;
    private StrokedRectangle rectangle = new StrokedRectangle(StrokedRectangle.Type.FACE);

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        this.architectView.registerNativePlugins("wikitudePlugins", "face_detection");
        try {
            // load cascade file from application resources
            InputStream is = getResources().openRawResource(R.raw.high_database);
            File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
            _cascadeFile = new File(cascadeDir, "lbpcascade_frontalface.xml");
            FileOutputStream os = new FileOutputStream(_cascadeFile);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            is.close();
            os.close();

            initNative(_cascadeFile.getAbsolutePath());

            cascadeDir.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }

        setInterfaceOrientationInPlugin();
    }

    private void setInterfaceOrientationInPlugin() {
        Display display = ((WindowManager)
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int screenOrientation = display.getRotation();

        switch (screenOrientation) {
            case Surface.ROTATION_0: // Portrait
                setFlipFlag(1);
                break;
            case Surface.ROTATION_90: // Landscape right
                setFlipFlag(999);
                break;
            case Surface.ROTATION_180: // Portrait upside down
                setFlipFlag(0);
                break;
            case Surface.ROTATION_270: // Landscape left
                setFlipFlag(-1);
                break;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setInterfaceOrientationInPlugin();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rectangle == null) {
            rectangle = new StrokedRectangle(StrokedRectangle.Type.FACE);
            setInterfaceOrientationInPlugin();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        rectangle = null;
    }

    public void onFaceDetected(float[] modelViewMatrix) {
        if (rectangle != null) {
            rectangle.setViewMatrix(modelViewMatrix);
        }
    }

    public void onFaceLost() {
        if (rectangle != null) {
            rectangle.onFaceLost();
        }
    }

    public void onProjectionMatrixChanged(float[] projectionMatrix) {
        if (rectangle != null) {
            rectangle.setProjectionMatrix(projectionMatrix);
        }
    }

    public void renderDetectedFaceAugmentation() {
        if (rectangle != null) {
            rectangle.onDrawFrame();
        }
    }

    private native void initNative(String casecadeFilePath);
    private native void setFlipFlag(int flag);

}
