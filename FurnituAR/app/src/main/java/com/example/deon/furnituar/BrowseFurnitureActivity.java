package com.example.deon.furnituar;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BrowseFurnitureActivity extends AppCompatActivity {

    public final static String SELECTED_FURNITURE = "com.example.deon.furnituar.BrowseFurnitureActivity.SELECTED_FURNITURE";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_furniture);
        if (savedInstanceState == null) {
            PlaceholderFragment newFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.list_container, newFragment)
                    .commit();
       }
    }

    public static class PlaceholderFragment extends Fragment implements SensorEventListener{
        private FurnitureListAdapter furnitureListAdapter;
        public PlaceholderFragment() {
        }

        private String[] itemNames;
        private Integer[] imageIDs;
        private String[] descriptions;

        private SensorManager mSensorManager;
        Sensor accelerometer;
        Sensor magnetometer;
        float azimuth;


        public void onAccuracyChanged(Sensor sensor, int accuracy) {  }

        float[] mGravity;
        float[] mGeomagnetic;
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
                mGravity = event.values;
            if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                mGeomagnetic = event.values;
            if (mGravity != null && mGeomagnetic != null) {
                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
                if (success) {
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    azimuth = orientation[0];
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            ListView furnitureListView;

            itemNames = SampleData.ITEM_NAMES;
            imageIDs = SampleData.IMAGE_IDS;
            descriptions = SampleData.DESCRIPTIONS;

            furnitureListAdapter = new FurnitureListAdapter(
                    getActivity(),
                    R.layout.browse_furniture_list_item,
                    R.id.furniture_list_name_text_view,
                    R.id.furniture_list_image_view,
                    R.id.furniture_list_description_text_view,
                    itemNames,
                    imageIDs,
                    descriptions);
            View rootView = inflater.inflate(R.layout.fragment_browse_furniture, container, false);
            furnitureListView = (ListView) rootView.findViewById(R.id.list_view_furniture);
            furnitureListView.setAdapter(furnitureListAdapter);
            furnitureListView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int listIndex, long id) {
                            String selection = itemNames[+listIndex];
                            try {
                                writeJStoExtCache(selection);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Intent loadRenderFurnitureActivity = new Intent(getActivity(), SampleCamActivity.class);
                            loadRenderFurnitureActivity.putExtra(SELECTED_FURNITURE, selection);
                            Log.d("BROWSEFURNITUREACTIVITY", selection);
                            startActivity(loadRenderFurnitureActivity);
                        }
                    }
            );

            return rootView;
        }

        @Override
        public void onResume() {
            super.onResume();
            mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
            mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
        }

        public void onPause() {
            super.onPause();
            mSensorManager.unregisterListener(this);
        }


        private void writeJStoExtCache(String selection) throws IOException {
            File file = new File(getContext().getExternalCacheDir(), "output.js");
            FileOutputStream stream = new FileOutputStream(file);
            String jsCode = "console.log('test file written');\n" +
                        "var selectionData = {\n" +
                        "'selection': '" + selection + "',\n" +
                        "'bearingN': " + Math.cos(azimuth) + ",\n" +
                        "'bearingE': " + Math.sin(azimuth) + "};";
            try {
                stream.write(jsCode.getBytes());
            } finally {
                stream.close();
                Log.d("BROWSEFURNITUREACTIVITY", "JS written: " + jsCode);
            }


        }
    }

}
