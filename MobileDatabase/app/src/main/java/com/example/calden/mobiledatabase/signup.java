package com.example.calden.mobiledatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class signup extends AppCompatActivity {

    Button button;
    DatabaseHelper mydb;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        user = (EditText) findViewById(R.id.user);
        pass = (EditText) findViewById(R.id.pass);
        mydb = new DatabaseHelper(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //
                try {
                    RequestQueue requestQueue = Volley.newRequestQueue(signup.this);
                    String URL = "http://192.168.43.24:8080/insert";
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("user", user.getText().toString());
                    jsonBody.put("pass", pass.getText().toString());
                    final String requestBody = jsonBody.toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.i("VOLLEY", response);
                            insertlocal();
                            Intent intent = new Intent(getBaseContext(), page.class);
                            intent.putExtra("name", user.getText().toString());
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VOLLEY", error.toString());
                        }
                    }) {
                        @Override
                        public String getBodyContentType() {
                            return "application/json; charset=utf-8";
                        }

                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            try {
                                return requestBody == null ? null : requestBody.getBytes("utf-8");
                            } catch (UnsupportedEncodingException uee) {
                                VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                return null;
                            }
                        }

                        @Override
                        protected Response<String> parseNetworkResponse(NetworkResponse response) {
                            String responseString = "";
                            if (response != null) {
                                responseString = String.valueOf(response.statusCode);
                                // can get more details such as response.headers
                                /*
                                try {
                                    String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
                                    JSONObject jo=new JSONObject(json);
                                    Log.d("myresponse", jo.getString("password"));
                                }
                                catch (Exception e){
                                    Log.d("errorinresponse",e.toString());
                                }*/
                            }
                            return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                        }
                    };

                    stringRequest.setRetryPolicy(new RetryPolicy() {
                        @Override
                        public int getCurrentTimeout() {
                            return 50000;
                        }

                        @Override
                        public int getCurrentRetryCount() {
                            return 50000;
                        }

                        @Override
                        public void retry(VolleyError error) throws VolleyError {

                        }
                    });

                    requestQueue.add(stringRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




                //insert in external database

            }
        });
    }

    void insertlocal(){
        //insert in local database
        boolean result = false;
        result = mydb.insert(user.getText().toString(), pass.getText().toString());
        if (result == true) {
            Toast.makeText(signup.this, "Data inserted", Toast.LENGTH_SHORT).show();
            Log.d("data_inserted", "inserted");
        } else {
            Toast.makeText(signup.this, "Data not inserted", Toast.LENGTH_SHORT).show();
            Log.d("data_inserted", "not inserted");
        }
    }
}
