package com.example.calden.mobiledatabase;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper mydb;
    EditText user,pass;
    Button button, buttonsu;
    ListView lv;
    ArrayList<String> ar = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);
        mydb= new DatabaseHelper(this);

        //user =(EditText)findViewById(R.id.user);
        //pass =(EditText)findViewById(R.id.pass);
        button=(Button)findViewById(R.id.button2);
        buttonsu=(Button)findViewById(R.id.button3);
        lv=(ListView)findViewById(R.id.lisvi);

        Cursor res=mydb.getAllData();
        if(res.getCount()==0){
            Log.d("List_result","no data");
        }
        while(res.moveToNext()){
           ar.add(res.getString(0));
        }


        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, R.layout.list, ar);
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("hey",ar.get(position));
                Intent intent = new Intent(getBaseContext(), page.class);
                intent.putExtra("name", ar.get(position));
                startActivity(intent);
            }
            });


        button.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(MainActivity.this, accounts.class);
                        startActivity(intent);

                        /*
                        boolean result =false;
                        result=mydb.insert(user.getText().toString(),pass.getText().toString());
                        if(result==true)
                        {
                            Toast.makeText(MainActivity.this,"Data inserted",Toast.LENGTH_SHORT).show();
                            Log.d("data_inserted", "inserted");
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Data not inserted",Toast.LENGTH_SHORT).show();
                            Log.d("data_inserted", "not inserted");
                        }*/
                    }
                }
        );
        buttonsu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, signup.class);
                        startActivity(intent);
                    }
                }
        );


    }
}
