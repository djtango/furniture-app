package com.example.deon.furnituar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by rajeevhejib on 04/01/16.
 */
public class BrowseFurnitureActivity extends AppCompatActivity{

    public final static String SELECTED_FURNITURE = "com.example.deon.furnituar.BrowseFurnitureActivity.SELECTED_FURNITURE";

        ListView list;
        String[] item = {
                "Google Plus",
                "Twitter",
                "Windows",
                "Bing",
                "Itunes",
                "Wordpress",
                "Drupal"
        } ;
        Integer[] imageId = {
                R.drawable.pic1,
                R.drawable.pic2,
                R.drawable.pic3,
                R.drawable.pic4,
                R.drawable.pic5,
                R.drawable.pic6,
                R.drawable.pic7

        };
        String[] desc = {
                "Description Google Plus",
                "Description Twitter",
                "Description Windows",
                "Description Bing",
                "Description Itunes",
                "Description Wordpress",
                "Description Drupal"
        } ;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_browse_furniture);

            CustomList adapter = new
                    CustomList(BrowseFurnitureActivity.this, item, imageId, desc);
            list=(ListView)findViewById(R.id.list);
            list.setAdapter(adapter);
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(BrowseFurnitureActivity.this, "You Clicked at " + item[+position], Toast.LENGTH_SHORT).show();

                }
            });

        }

    }






//
//
//import android.content.Intent;
//import android.support.v4.app.Fragment;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//import android.app.Activity;
//
//public class BrowseFurnitureActivity extends AppCompatActivity {
//    public final static String SELECTED_FURNITURE = "com.example.deon.furnituar.BrowseFurnitureActivity.SELECTED_FURNITURE";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_browse_furniture);
//        Log.d("onCreate", "inside onCreate");
//       if (savedInstanceState == null) {
//           PlaceholderFragment newFragment = new PlaceholderFragment();
//           getSupportFragmentManager().beginTransaction()
//                   .add(R.id.furniture_container, newFragment)
//                   .commit();
//       }
//    }
//
//    public static class PlaceholderFragment extends Fragment {
//        private ArrayAdapter<String> furnitureListAdapter;
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//            String[] tableList = {
//                   "table 1",
//                   "table 2",
//                   "table 3",
//                   "table 4",
//                   "table 5",
//                   "table 6"};
//
//           List<String> furnitureList = new ArrayList<String>(Arrays.asList(tableList));
//           furnitureListAdapter = new ArrayAdapter<String>(
//                   getActivity(),
//                   R.layout.browse_furniture_list_item,
//                   R.id.text_view_furniture_description,
//                   furnitureList);
//
//            View rootView = inflater.inflate(R.layout.fragment_browse_furniture, container, false);
//            ListView furniture_listView = (ListView) rootView.findViewById(R.id.list_view_furniture);
//            furniture_listView.setAdapter(furnitureListAdapter);
//            furniture_listView.setOnItemClickListener(
//                    new AdapterView.OnItemClickListener() {
//                        @Override
//                        public void onItemClick(AdapterView<?> parent, View view, int listIndex, long id) {
//                            String selection = furnitureListAdapter.getItem(listIndex);
//                            Intent loadRenderFurnitureActivity = new Intent(getActivity(), RenderFurnitureActivity.class);
//                            loadRenderFurnitureActivity.putExtra(SELECTED_FURNITURE, selection);
//                            startActivity(loadRenderFurnitureActivity);
//                        }
//                    }
//            );
//
//            return rootView;
//        }
//
//    }
//}

