package com.example.nem_mobi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Add_Data_Activity extends AppCompatActivity {

    EditText txtName,txtId;
    Button btnInsert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        txtName = findViewById(R.id.edtName);
        txtId = findViewById(R.id.edtId);
        btnInsert = findViewById(R.id.btnInsert);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                txtName.setText("");
                txtId.setText("");
            }
        });
    }

    private void insertData() {
        final String Id = txtId.getText().toString().trim();
        final String name= txtName.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if(Id.isEmpty()){
            Toast.makeText(this,"Enter User Id", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(name.isEmpty()){
            Toast.makeText(this,"Enter User Name", Toast.LENGTH_SHORT).show();
            return;
        }else{
            StringRequest request = new StringRequest(Request.Method.POST, "https://amithapp.000webhostapp.com/insert.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Data Inserted")){
                                Toast.makeText(Add_Data_Activity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else{
                                Toast.makeText(Add_Data_Activity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Add_Data_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String, String>();

                    params.put("userName",name);
                    params.put("userId",Id);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Add_Data_Activity.this);
            requestQueue.add(request);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
