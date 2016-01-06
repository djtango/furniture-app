package com.example.deon.furnituar;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonWriter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrowseFurnitureActivity extends AppCompatActivity {

    public final static String SELECTED_FURNITURE = "com.example.deon.furnituar.BrowseFurnitureActivity.SELECTED_FURNITURE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_furniture);
        if (savedInstanceState == null) {
            PlaceholderFragment newFragment = new PlaceholderFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.furniture_container, newFragment)
                    .commit();
       }
    }

    public static class PlaceholderFragment extends Fragment implements SensorEventListener{
        private ArrayAdapter<String> furnitureListAdapter;
        public PlaceholderFragment() {
        }

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
                    azimuth = orientation[0]; // orientation contains: azimut, pitch and roll
//                    Log.d("BROWSEFURNITUREACTIVITY","x: " + Double.toString(Math.sin(azimuth)));
//                    Log.d("BROWSEFURNITUREACTIVITY","y: " + Double.toString(Math.cos(azimuth)));
                    Log.d("BROWSEFURNITUREACTIVITY",Double.toString(azimuth));

                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            String[] tableList = {
                   "table 1",
                   "table 2",
                   "table 3",
                   "table 4",
                   "table 5",
                   "table 6"};

            List<String> furnitureList = new ArrayList<String>(Arrays.asList(tableList));
            furnitureListAdapter = new ArrayAdapter<String>(
                   getActivity(),
                   R.layout.browse_furniture_list_item,
                   R.id.text_view_furniture_description,
                   furnitureList);

            View rootView = inflater.inflate(R.layout.fragment_browse_furniture, container, false);
            ListView furniture_listView = (ListView) rootView.findViewById(R.id.list_view_furniture);
            furniture_listView.setAdapter(furnitureListAdapter);
            furniture_listView.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int listIndex, long id) {
                            String selection = furnitureListAdapter.getItem(listIndex);
                            try {
                                writeJStoExtCache(selection);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Intent loadRenderFurnitureActivity = new Intent(getActivity(), SampleCamActivity.class);
                            loadRenderFurnitureActivity.putExtra(SELECTED_FURNITURE, selection);
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
            File file = new File(getContext().getExternalCacheDir(), "test_write.js");
            FileOutputStream stream = new FileOutputStream(file);
            try {
                stream.write(("console.log('test file written');\n" +
                        "var selectionData = {\n" +
                        "'selection': '" + selection + "',\n" +
                        "'bearingN': " + Math.cos(azimuth) + ",\n" +
                        "'bearingE': " + Math.sin(azimuth) + "};").getBytes());
            } finally {
                stream.close();
            }


        }
    }
}
