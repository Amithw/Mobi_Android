package com.example.nem_mobi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ListView listView;
    MyAdapter adapter;
    public static ArrayList<Employee> employeeArrayList = new ArrayList<>();
    String url = "https://amithapp.000webhostapp.com/retrieve.php";
    Employee employee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        listView = findViewById(R.id.myListView);
        adapter = new MyAdapter(this, employeeArrayList);
        listView.setAdapter(adapter);

        retrieveData();
    }

    public void retrieveData(){
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        employeeArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if(success.equals("1")){

                                for(int i=0; i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String usrId = object.getString("userId");
                                    String usrName = object.getString("userName");

                                    employee = new Employee(usrId, usrName);
                                    employeeArrayList.add(employee);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void btn_add_activity(View view) {
        startActivity(new Intent(getApplicationContext(),Add_Data_Activity.class));
    }
}