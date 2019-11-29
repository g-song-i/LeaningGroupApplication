
package com.example.leaninggroupapplication;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

//import android.support.v4.app.AppCompatActivity;

public class CreateGroup extends AppCompatActivity {

    Button cg_cancelBtn;
    Button cg_OkBtn;
    Spinner category_spinner;
    Spinner cg_start_time;
    Spinner cg_start_time_m;
    Spinner cg_end_time;
    Spinner cg_end_time_m;

    EditText cg_title;
    EditText cg_content;
    EditText cg_numberOfUser;
    EditText cg_date;
    String category;
    String start_time;
    String start_time_m;
    String end_time;
    String end_time_m;
    String starttime;
    String endtime;

    String group_roomnumber;


    TextView cg_writer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_group);


        category_spinner = (Spinner) findViewById(R.id.category_spinner);
        cg_content = (EditText) findViewById(R.id.cg_content);
        cg_title = (EditText) findViewById(R.id.cg_title);
        cg_date = (EditText) findViewById(R.id.cg_date);
        cg_start_time = (Spinner) findViewById(R.id.cg_start_time);
        cg_end_time = (Spinner) findViewById(R.id.cg_end_time);
        cg_start_time_m = (Spinner) findViewById(R.id.cg_start_time_m);
        cg_end_time_m = (Spinner) findViewById(R.id.cg_end_time_m);
        cg_numberOfUser = (EditText) findViewById(R.id.cg_numberOfUser);
        cg_writer = (TextView) findViewById(R.id.cg_writer);


        cg_cancelBtn = findViewById(R.id.cg_cancelBtn);
        cg_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                startActivity(intent);
            }
        });
        cg_OkBtn = findViewById(R.id.cg_OkBtn);
        cg_OkBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                String title = cg_title.getText().toString();
                String content = cg_content.getText().toString();
                String numberOfUser = cg_numberOfUser.getText().toString();
                String date = cg_date.getText().toString();
                //String starttime = cg_starttime.getText().toString();
                //String endtime = cg_endtime.getText().toString();
                String writer = cg_writer.getText().toString();


                emptycheak(title, content, date, numberOfUser);
                if(cg_numberOfUser.getText().toString().equals("0")){
                    Toast.makeText(getApplicationContext(), "인원수는 최소 1명이여야 합니다.", Toast.LENGTH_SHORT).show();
                }

               Response.Listener<String> responseListener = new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                        try {
                        JSONObject jsonResponse = new JSONObject(response);

                        boolean success = jsonResponse.getBoolean("success");


                            if (success) {

                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                                builder.setMessage("모임 등록에 성공했습니다.").setPositiveButton("확인", null).create().show();
                                Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                                startActivity(intent);
                            } else {

                                //AlertDialog.Builder builder = new AlertDialog.Builder(CreateGroup.this);
                                //builder.setMessage("모임 등록에 실패했습니다.").setNegativeButton("확인", null).create().show();
                                //Intent intent = new Intent(CreateGroup.this, MainActivity.class);
                                //startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("EWfw");
                        }
                    }
                };
                InsertData insertData = new InsertData(category, title, content, numberOfUser, date, starttime, endtime, writer, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateGroup.this);
                queue.add(insertData);


            }
        });

        category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cg_start_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start_time = (String) parent.getItemAtPosition(position);
                starttime = start_time;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cg_start_time_m.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                start_time_m = "00";
                start_time_m = (String) parent.getItemAtPosition(position);
                starttime = start_time + start_time_m + "00";
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        cg_end_time.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end_time = (String) parent.getItemAtPosition(position);
                endtime = end_time;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cg_end_time_m.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                end_time_m = "00";
                end_time_m = (String) parent.getItemAtPosition(position);
                endtime += end_time_m + "00";
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        final PrefManager prefManager = PrefManager.getInstance(CreateGroup.this);
        User user = prefManager.getUser();

        if (prefManager.isLoggedIn()) {
            cg_writer.setText(String.valueOf(user.getEmail()));
        }


    }

    class InsertData extends StringRequest {

        final static private String URL = "http://rkdlem1613.dothome.co.kr/insert6.php";
        private Map<String, String> parameters;

        public InsertData(String category, String title, String content, String numberOfUser, String date,
                          String starttime, String endtime, String writer, Response.Listener<String> listener) {
            super(Method.POST, URL, listener, null);
            parameters = new HashMap<>();
            parameters.put("category", category);
            parameters.put("title", title);
            parameters.put("content", content);
            parameters.put("numberOfUser", numberOfUser);
            parameters.put("date", date);
            parameters.put("starttime", starttime);
            parameters.put("endtime", endtime);
            parameters.put("writer", writer);
        }

        public Map<String, String> getParams() {
            return parameters;
        }
    }
    private void emptycheak(String title, String content,String date,String numberOfUser) {

        final String titlee = cg_title.getText().toString().trim();
        final String contentt = cg_content.getText().toString().trim();
        final String datee = cg_date.getText().toString().trim();
        final String numberOfUserr = cg_numberOfUser.getText().toString().trim();


        if (TextUtils.isEmpty(titlee)) {

            cg_title.setError("Please enter this component");
            cg_title.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(contentt)) {

            cg_content.setError("Please enter this component");
            cg_content.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(datee)) {

            cg_date.setError("Please enter this component");
            cg_date.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(numberOfUserr)) {

            cg_numberOfUser.setError("Please enter this component");
            cg_numberOfUser.requestFocus();
            return;
        }

    }
}


