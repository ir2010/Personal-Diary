package com.ir_sj.personaldiary;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ir_sj.personaldiary.R;

public class MyAdapter extends ArrayAdapter {
    String[] names;
    Integer[] images;
    String[] lead;
    AppCompatActivity context;
    public MyAdapter(AppCompatActivity context, String[] names, Integer[] images, String[] lead)
    {
        super(context,R.layout.activity_developers_listitem,names);
        this.names=names;
        this.images=images;
        this.context=context;
        this.lead= lead;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li=context.getLayoutInflater();
        View rowview = li.inflate(R.layout.activity_developers_listitem,null,true);
        TextView name = rowview.findViewById(R.id.names);
        ImageView image =  rowview.findViewById(R.id.images);
        TextView leadText = rowview.findViewById(R.id.lead);
        name.setText(names[position]);
        image.setImageResource(images[position]);
        leadText.setText(lead[position]);
        return rowview;
    }
}

