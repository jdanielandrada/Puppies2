package com.example.puppies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class iniciopersona extends AppCompatActivity {
    private static final String URL_perros="https://proyectopuppies.000webhostapp.com/publicaciones.php";
    List<Perros> perroList;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;


    FloatingActionButton publicar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciopersona);

        refreshLayout=findViewById(R.id.refreshLayout);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        perroList=new ArrayList<>();

        loadperros();


        publicar= findViewById(R.id.publicar);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), formupersona.class);
                startActivityForResult(intent, 0);

            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
            }
        });


    }

    private void loadperros() {
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_perros,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject perro = array.getJSONObject(i);

                                perroList.add(new Perros(
                                        perro.getString("foto"),
                                        perro.getString("nombre"),
                                        perro.getString("desc")
                                ));
                            }
                            Adapter adapter = new Adapter(iniciopersona.this, perroList);
                            recyclerView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplication(),error.toString(),Toast.LENGTH_SHORT).show();

                    }
                });
        Volley.newRequestQueue(this).add(stringRequest);
    }


}
