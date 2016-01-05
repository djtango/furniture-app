package com.example.deon.furnituar;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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

                String selection = item[+position];
                Intent loadRenderFurnitureActivity = new Intent(BrowseFurnitureActivity.this, RenderFurnitureActivity.class);

                loadRenderFurnitureActivity.putExtra(SELECTED_FURNITURE, selection);
                startActivity(loadRenderFurnitureActivity);

            }
        });

    }

}
