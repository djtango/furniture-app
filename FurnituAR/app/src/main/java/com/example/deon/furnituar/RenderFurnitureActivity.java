package com.example.deon.furnituar;

import android.content.Intent;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.StartupConfiguration;

public class RenderFurnitureActivity extends AbstractArchitectCamActivity {
    ArchitectView architectView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_furniture);

        this.architectView = (ArchitectView)this.findViewById(R.id.architectView);
        final StartupConfiguration config = new StartupConfiguration();//licence key;
        this.architectView.onCreate( config );

        Intent intent = getIntent();
        String selection = intent.getStringExtra(BrowseFurnitureActivity.SELECTED_FURNITURE);
        Log.d("TESTING PUTEXTRA", selection);
    }

    @Override
    protected StartupConfiguration.CameraPosition getCameraPosition() {
        return StartupConfiguration.CameraPosition.BACK;
    }

    @Override
    protected boolean hasGeo() {
        return true;
    }

    @Override
    protected boolean hasIR() {
        return false;
    }

    @Override
    public String getActivityTitle() {
        return null;
    }

    @Override
    public String getARchitectWorldPath() {
        return null;
    }

    @Override
    public ArchitectView.ArchitectUrlListener getUrlListener() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public String getWikitudeSDKLicenseKey() {
        return WikitudeSDKConstants.WIKITUDE_SDK_KEY;
    }

    @Override
    public int getArchitectViewId() {
        return 0;
    }

    @Override
    public ILocationProvider getLocationProvider(LocationListener locationListener) {
        return null;
    }

    @Override
    public ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener() {
        return null;
    }

    @Override
    public float getInitialCullingDistanceMeters() {
        return 0;
    }

    this.architectView.onPostCreate();
    this.architectView.load("index.html")
}
