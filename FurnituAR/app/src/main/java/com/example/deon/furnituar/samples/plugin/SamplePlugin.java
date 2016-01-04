package com.wikitude.samples.plugin;

import android.util.Log;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.PlanarYUVLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.plugin.Frame;
import com.wikitude.architect.plugin.Plugin;
import com.wikitude.architect.plugin.RecognizedTarget;

import java.util.List;

public class SamplePlugin extends Plugin {

    public SamplePlugin(String identifier, ArchitectView architectView) {
        super(identifier, architectView);
    }

    @Override
    protected void initialize() {
        Log.e(this.getClass().getName(), "Plugin.init");
    }

    @Override
    protected void pause() {
        Log.e(this.getClass().getName(), "Plugin.pause");
    }

    @Override
    protected void resume(long pausedTime) {
        Log.e(this.getClass().getName(), "Plugin.resume");
    }

    @Override
    protected void destroy() {
        Log.e(this.getClass().getName(), "Plugin.destroy");
    }

    @Override
    public void cameraFrameAvailable(final Frame cameraFrame) {
        Log.e(this.getClass().getName(), "Plugin.cameraFrameAvailable");
    }

    @Override
    public void update(RecognizedTarget[] recognizedTargets) {
        Log.e(this.getClass().getName(), "Plugin.update");
    }

}
