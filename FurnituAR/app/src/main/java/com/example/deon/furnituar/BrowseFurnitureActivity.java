package com.example.deon.furnituar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

        Intent intent = getIntent();

//        String[] tableList = {
//                "table 1",
//                "table 2",
//                "table 3",
//                "table 4",
//                "table 5",
//                "table 6"
//        };
//        List<String> furnitureList = new ArrayList<String>(Arrays.asList(tableList));
//        ArrayAdapter furnitureListAdapter = new ArrayAdapter<String>(
//                this,
//                R.layout.content_browse_furniture,
//                R.id.text_view_furniture_description,
//                furnitureList);
//
//        ListView furnitureListView = (ListView) this.findViewById(R.id.list_view_furniture);
//        furnitureListView.setAdapter(furnitureListAdapter);
    }

}
