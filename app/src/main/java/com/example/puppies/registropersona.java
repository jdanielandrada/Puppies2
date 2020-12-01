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

public class registropersona extends AppCompatActivity {
    EditText etnombre,etapellido,etemail,ettelefono,etusuario, etcontra;
    TextView etrefugio;
    Button btnregistrar;

    RequestQueue requestQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registropersona);

        etnombre=findViewById(R.id.etnombre);
        etapellido=findViewById(R.id.etapellido);
        etemail=findViewById(R.id.etemail);
        ettelefono=findViewById(R.id.ettelefono);
        etusuario=findViewById(R.id.etusuario);
        etcontra=findViewById(R.id.etcontra);
        btnregistrar=findViewById(R.id.btnregistrar);
        etrefugio=findViewById(R.id.etrefugio);

        etrefugio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), registrorefugio.class);
                startActivityForResult(intent, 0);
            }
        });

        btnregistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarpersona("https://proyectopuppies.000webhostapp.com/registrarpersona.php");
            }
        });

    }

    private void registrarpersona (String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST,URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),"Se ha registrado correctamente", Toast.LENGTH_SHORT).show();
                limpiarFomulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map <String,String> parametros= new HashMap<String,String>();
                parametros.put("nombre",etnombre.getText().toString());
                parametros.put("apellido",etapellido.getText().toString());
                parametros.put("mail",etemail.getText().toString());
                parametros.put("telefono",ettelefono.getText().toString());
                parametros.put("usuario",etusuario.getText().toString());
                parametros.put("pass",etcontra.getText().toString());

                return parametros;
            }

        };
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void limpiarFomulario(){
        etnombre.setText("");
        etapellido.setText("");
        etemail.setText("");
        ettelefono.setText("");
        etusuario.setText("");
        etcontra.setText("");
    }

}


