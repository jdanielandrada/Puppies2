package com.example.puppies;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class loginpersona extends AppCompatActivity {
    EditText etusuario, etpass;
    Button btningresar;
    TextView etrefugio;


    CookieManager sesion= CookieManager.getInstance();

    RequestQueue requestQueue;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginpersona);
        etusuario = findViewById(R.id.etusuario);
        etpass = findViewById(R.id.etpass);
        btningresar = findViewById(R.id.btningresar);
        etrefugio = findViewById(R.id.etrefugio);

        etrefugio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), loginrefugio.class);
                startActivityForResult(intent, 0);
            }
        });

        btningresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               login();
            }
        });


    }

    public void login(){
        StringRequest request= new StringRequest(Request.Method.POST,"https://proyectopuppies.000webhostapp.com/loginpersona.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    if (response.contains("1")){
                        startActivity(new Intent(getApplicationContext(),iniciopersona.class));
                        limpiarFomulario();
                    }else{
                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrecto", Toast.LENGTH_SHORT).show();
                    }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
              Map<String,String> params = new HashMap<>();
              params.put("usuario",etusuario.getText().toString());
              params.put("pass",etpass.getText().toString());
              return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
    private void limpiarFomulario(){
        etusuario.setText("");
        etpass.setText("");
    }
}
