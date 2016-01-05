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
