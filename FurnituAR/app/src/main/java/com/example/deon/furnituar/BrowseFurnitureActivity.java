package com.example.deon.furnituar;

import android.content.Intent;
<<<<<<< HEAD
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
=======
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
>>>>>>> 0446e944e77f233f1de262f0e518b065c1ae8cca
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

<<<<<<< HEAD
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrowseFurnitureActivity extends AppCompatActivity {

=======
/**
 * Created by rajeevhejib on 04/01/16.
 */
public class BrowseFurnitureActivity extends AppCompatActivity{

>>>>>>> 0446e944e77f233f1de262f0e518b065c1ae8cca
    public final static String SELECTED_FURNITURE = "com.example.deon.furnituar.BrowseFurnitureActivity.SELECTED_FURNITURE";

        ListView list;
        String[] item = {
                "Cloudscape Chair, white",
                "Copenhagen Table, ash",
                "Paris Bed, burgundy",
                "Upholstered Armchair, grey",
                "Wooden Table, oak",
                "Rocking-chair, oak veneer",
                "Wing chair, dark grey",
                "Armchair, dark grey",
                "Two-seat sofa, grey ",
                "Two-seat sofa, grey",
                "Coffee table, black-brown, glass",
                "Side table, beige",
                "Coffee table, black-brown",
                "Bookcase, oak veneer",
                "Bookcase, oak"
        } ;
        Integer[] imageId = {
                R.drawable.a_cloudscape_chair,
                R.drawable.a_copenhagen_table,
                R.drawable.a_linen_paris,
                R.drawable.a_upholstered_armchair,
                R.drawable.a_wooden_table,
                R.drawable.pic1,
                R.drawable.pic2,
                R.drawable.pic3,
                R.drawable.pic4,
                R.drawable.pic5,
                R.drawable.pic6,
                R.drawable.pic7,
                R.drawable.pic8,
                R.drawable.pic9,
                R.drawable.pic10,


        };
        String[] desc = {
                "You can relax, sit or lie like on clouds with the Cloudscape armchair. The extra soft pillows adapt to the shape of the body",
                "95 x 200 side table that has a light and airy expression.",
                "This Parisian blue was inspired by an Autumn afternoon in Paris, painted in a deep resonant red",
                "Classic French style armchair inspired by Louis XV. Features hand carved detailing. ",
                "A small Solid oak table with laquer finish",
                "The frame is made of layer-glued bent oak which is a very strong and durable material",
                "Leg: Solid hardwood, Tinted clear lacquer. Total composition: 75% polyester, 19% modacrylic, 6% cotton",
                "Extra covers to alternate with mean it's easy to give both your sofa and room a new look",
                "The high back gives good support for your neck and head. Seat cushions with cold foam and a top layer of memory foam; moulds to the precise contours of your body and regains its shape when you get up",
                "Very durable thanks to the metal construction and strong supporting fabric",
                "The table top in tempered glass is stain resistant and easy to clean",
                "Separate shelf for magazines, etc. helps you keep your things organised and the table top clear",
                "Solid wood has a natural feel. Separate shelf for magazines, etc. helps you keep your things organised and the table top clear",
                "A simple unit can be enough storage for a limited space or the foundation for a larger storage solution if your needs change.",
                "Adjustable shelves, so you can customise your storage as needed. Surface made from natural wood veneer"
        } ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_furniture);
<<<<<<< HEAD
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
//                    Log.d("BROWSEFURNITUREACTIVITY",Double.toString(azimuth));
                }
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mSensorManager = (SensorManager) getActivity().getSystemService(SENSOR_SERVICE);
            accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            String[] tableList = {
                   "earth",
                   "plantcolour",
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
                            Log.d("BROWSEFURNITUREACTIVITY", selection);
                            startActivity(loadRenderFurnitureActivity);
                        }
                    }
            );
=======

        CustomList adapter = new
                CustomList(BrowseFurnitureActivity.this, item, imageId, desc);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                String selection = item[+position];
                Intent loadRenderFurnitureActivity = new Intent(BrowseFurnitureActivity.this, RenderFurnitureActivity.class);

                loadRenderFurnitureActivity.putExtra(SELECTED_FURNITURE, selection);
                startActivity(loadRenderFurnitureActivity);
>>>>>>> 0446e944e77f233f1de262f0e518b065c1ae8cca

            }
        });

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
