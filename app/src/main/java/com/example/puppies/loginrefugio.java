package com.example.puppies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class loginrefugio extends AppCompatActivity {
    EditText etusuario, etcontra;
    Button btingresar;
    TextView etpersona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginrefugio);

        etusuario = findViewById(R.id.etusuario);
        etcontra = findViewById(R.id.etcontra);
        btingresar = findViewById(R.id.btingresar);
        etpersona = findViewById(R.id.etpersona);

        etpersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), loginpersona.class);
                startActivityForResult(intent, 0);
            }
        });

        btingresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login(){
        StringRequest request= new StringRequest(Request.Method.POST,"https://proyectopuppies.000webhostapp.com/loginrefugio.php",
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
                params.put("pass",etcontra.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(request);
    }
    private void limpiarFomulario(){
        etusuario.setText("");
        etcontra.setText("");
    }
}
