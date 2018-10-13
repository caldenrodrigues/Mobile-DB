package com.example.calden.mobiledatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);
        String name=getIntent().getStringExtra("name");
        TextView t=(TextView)findViewById(R.id.textView);
        t.setText(name);
    }
}
