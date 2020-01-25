package com.ir_sj.personaldiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ir_sj.personaldiary.R;

public class SeeFile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_file);
        Intent intent = getIntent();
        this.setTitle(intent.getStringExtra("GetFileName"));
        TextView txt = (TextView)findViewById(R.id.textView2);
        txt.setText(intent.getStringExtra("GetData"));
    }
}
