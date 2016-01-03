package com.example.deon.furnituar;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BrowseFurnitureActivity extends AppCompatActivity {

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

    public static class PlaceholderFragment extends Fragment {
        private ArrayAdapter<String> furnitureListAdapter;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                   R.layout.content_browse_furniture,
                   R.id.text_view_furniture_description,
                   furnitureList);

            View rootView = inflater.inflate(R.layout.fragment_browse_furniture, container, false);
            ListView furniture_listView = (ListView) rootView.findViewById(R.id.list_view_furniture);
            furniture_listView.setAdapter(furnitureListAdapter);

            return rootView;
        }

    }
}
