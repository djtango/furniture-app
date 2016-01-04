package com.example.deon.furnituar;

/**
 * Created by rajeevhejib on 04/01/16.
 */
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] item;
    private final Integer[] imageId;
    private final String[] desc;

    public CustomList(Activity context,
                      String[] item, Integer[] imageId, String[] desc) {
        super(context, R.layout.browse_furniture_list_item, item);

        this.context = context;
        this.item = item;
        this.imageId = imageId;
        this.desc = desc;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.browse_furniture_list_item, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        TextView txtDesc = (TextView) rowView.findViewById(R.id.des);

        txtTitle.setText(item[position]);
        imageView.setImageResource(imageId[position]);
        txtDesc.setText(desc[position]);

        return rowView;
    }
}
