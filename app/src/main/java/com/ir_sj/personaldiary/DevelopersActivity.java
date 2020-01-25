package com.ir_sj.personaldiary;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DevelopersActivity extends AppCompatActivity {

    ListView listView;
    String[] names={"Ishu Raj","Ashwini Pal"};
    Integer[] images={R.drawable.billi,R.drawable.billi};
    String[] lead={"Lead Developer"," "};

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developers);
        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
        this.setTitle("Developers");
        listView = findViewById(R.id.developers);
        MyAdapter adap=new MyAdapter(this,names,images,lead);
        listView.setAdapter(adap);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                switch(pos) {
                    case 0:
                        Toast.makeText(DevelopersActivity.this, "Lead Developer", Toast.LENGTH_SHORT).show();
                }
            }


        });

    }
}