package com.example.deon.furnituar;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FurnitureListAdapter extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] itemNames;
    private final Integer[] imageIDs;
    private final String[] descriptions;
    private int resource;
    private int namesTextViewId;
    private int imageViewId;
    private int descriptionTextViewId;


    public FurnitureListAdapter(
                                Activity context,
                                int resource,
                                int namesTextViewResourceId,
                                int imageViewResourceId,
                                int descriptionsTextViewResourceId,
                                String[] itemNames,
                                Integer[] imageIDs,
                                String[] descriptions) {
        super(context, resource, itemNames);

        this.resource = resource;
        this.context = context;
        this.itemNames = itemNames;
        this.imageIDs = imageIDs;
        this.descriptions = descriptions;
        this.namesTextViewId = namesTextViewResourceId;
        this.imageViewId = imageViewResourceId;
        this.descriptionTextViewId = descriptionsTextViewResourceId;

    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(this.resource, null, true);

        TextView textTitle = (TextView) rowView.findViewById(this.namesTextViewId);
        ImageView imageView = (ImageView) rowView.findViewById(this.imageViewId);
        TextView textDesc = (TextView) rowView.findViewById(this.descriptionTextViewId);

        textTitle.setText(itemNames[position]);
        imageView.setImageResource(imageIDs[position]);
        textDesc.setText(descriptions[position]);

        return rowView;
    }
}
