package com.example.anyplans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActualizarUsuarioActivity extends AppCompatActivity {
    EditText edtDni, edtNombres, edtApellidos, edtUsuario, edtPassword;
    Button btnActualizarUsuario;
    //TextView dashboard_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_actualizar_usuario);

        edtDni=(EditText)findViewById(R.id.edtDni);
        edtNombres=(EditText)findViewById(R.id.edtNombres);
        edtApellidos=(EditText)findViewById(R.id.edtApellidos);
        edtUsuario=(EditText)findViewById(R.id.edtUsuario);
        edtPassword=(EditText)findViewById(R.id.edtPassword);
        btnActualizarUsuario = findViewById(R.id.btnActualizarUsuario);
        //dashboard_subtitle =  findViewById(R.id.dashboard_subtitle);

        Bundle bundleDni=getIntent().getExtras();
        String DatoDni=bundleDni.getString("valorsesionDni");

        //dashboard_subtitle.setText(DatoDni);

        edtDni.setText(DatoDni);

        consultarUsuario("https://www.grupoxpertos.com/upn/android/developeru/consultar_usuario.php");


        btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!(edtDni.getText().toString().trim().isEmpty()) && !(edtNombres.getText().toString().trim().isEmpty()))) {
                    // Enviar los datos al servidor
                    ejecutarActualizarUsuario("https://www.grupoxpertos.com/upn/android/developeru/actualizar_usuario.php");
                } else {
                    Toast.makeText(ActualizarUsuarioActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                }

            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainActualizarUsuario), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }


    private void consultarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String nombres = jsonObject.getString("nombres");
                        String apellidos = jsonObject.getString("apellidos");
                        String usuario = jsonObject.getString("usuario");
                        String password = jsonObject.getString("password");

                        edtNombres.setText(nombres);
                        edtApellidos.setText(apellidos);
                        edtUsuario.setText(usuario);
                        edtPassword.setText(password);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    Toast.makeText(ActualizarUsuarioActivity.this, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActualizarUsuarioActivity.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("dni", edtDni.getText().toString());
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    private void ejecutarActualizarUsuario(String URL2){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL2, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }

        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }

        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("dni",edtDni.getText().toString());
                parametros.put("nombres",edtNombres.getText().toString());
                parametros.put("apellidos",edtApellidos.getText().toString());
                parametros.put("usuario",edtUsuario.getText().toString());
                parametros.put("password",edtPassword.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}