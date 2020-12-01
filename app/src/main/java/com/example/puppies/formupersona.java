package com.example.puppies;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

public class formupersona extends AppCompatActivity implements View.OnClickListener  {

    int secs = 2; // Delay in seconds

    private Button btnBuscar;
    private Button btnSubir;

    private ImageView imageView;

    private EditText editTextName;
    private EditText editTextRaza;
    private EditText editTextDesc;

    private EditText groupSexo;

    private Bitmap bitmap;

    private int PICK_IMAGE_REQUEST = 1;

    private String UPLOAD_URL ="https://proyectopuppies.000webhostapp.com/upload.php";

    private String KEY_IMAGEN = "foto";
    private String KEY_NOMBRE = "nombre";
    private String KEY_RAZA = "raza";
    private String KEY_DESC = "desc";
    private String KEY_SEXO = "sexo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formupersona);

        btnBuscar = (Button) findViewById(R.id.btnBuscar);
        btnSubir = (Button) findViewById(R.id.btnSubir);

        editTextName = (EditText) findViewById(R.id.editText);
        editTextRaza = (EditText) findViewById(R.id.editTextRaza);
        editTextDesc = (EditText) findViewById(R.id.editTextDesc);

        groupSexo = findViewById(R.id.radioGroup);

        imageView  = (ImageView) findViewById(R.id.imageView);

        btnBuscar.setOnClickListener(this);
        btnSubir.setOnClickListener(this);
    }

    public String getStringImagen(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void uploadImage () {
        //Mostrar el diálogo de progreso
        final ProgressDialog loading = ProgressDialog.show(this, "Subiendo...", "Espere por favor...", false, false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                //Descartar el diálogo de progreso
                loading.dismiss();
                //Mostrando el mensaje de la respuesta
                Toast.makeText(formupersona.this,s,Toast.LENGTH_SHORT).show();
            }
        },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Descartar el diálogo de progreso
                        loading.dismiss();

                        //Showing toast
                        //Toast.makeText(formupersona.this, volleyError.getMessage().toString(), Toast.LENGTH_LONG).show();
                        Toast.makeText(formupersona.this,"Complete todos los campos para poder publicar",Toast.LENGTH_SHORT).show();

                    }
                }) {


            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Convertir bits a cadena
                String imagen = getStringImagen(bitmap);

                //Obtieneodo lo que hay adentro del editText
                String nombre = editTextName.getText().toString().trim();
                String raza = editTextRaza.getText().toString().trim();
                String desc = editTextDesc.getText().toString().trim();
                String sexo = groupSexo.getText().toString().trim();

                //Creación de parámetros
                Map<String, String> params = new Hashtable<String, String>();

                //Agregando de parámetros
                params.put(KEY_IMAGEN, imagen);
                params.put(KEY_NOMBRE, nombre);
                params.put(KEY_RAZA, raza);
                params.put(KEY_DESC, desc);
                params.put(KEY_SEXO, sexo);

                //Parámetros de retorno
                return params;
            }
        };

        //Creación de una cola de solicitudes
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Agregar solicitud a la cola
        requestQueue.add(stringRequest);

    }


    private void showFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            //Cómo obtener el mapa de bits de la Galería
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //Configuración del mapa de bits en ImageView
            imageView.setImageBitmap(bitmap);
        }
    }

    public static class Utils {

        // Delay mechanism

        public interface DelayCallback{
            void afterDelay();
        }

        public static void delay(int secs, final DelayCallback delayCallback){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    delayCallback.afterDelay();
                }
            }, secs * 1000); // afterDelay will be executed after (secs*1000) milliseconds.
        }
    }





    @Override
    public void onClick(View v) {

        if (v == btnBuscar) {
            showFileChooser();
        }

        if (v == btnSubir) {
            uploadImage();
            Utils.delay(secs, new Utils.DelayCallback() {
                @Override
                public void afterDelay() {
                    Intent intent = new Intent(formupersona.this, iniciopersona.class);
                    startActivity(intent);

                }
            });

        }
    }
}
