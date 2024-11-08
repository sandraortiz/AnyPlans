package com.example.anyplans;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario, edtPassword;
    TextView tvResultado;
    Button btnLogin, btnRegistrarUsuario;
    String nombrecompleto="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //EdgeToEdge.enable(this);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnRegistrarUsuario = findViewById(R.id.btnRegistrarUsuario);
        tvResultado =  findViewById(R.id.tvResultado);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((!(edtUsuario.getText().toString().trim().isEmpty()) && !(edtPassword.getText().toString().trim().isEmpty()))) {
                    // Enviar los datos al servidor
                    validarUsuario("https://www.grupoxpertos.com/upn/android/developeru/validar_usuario.php");

                    //Intent intent=new Intent(getApplicationContext(), PrincipalActivity.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
                    tvResultado.setText("Por favor, complete todos los campos");
                }

            }
        });


        btnRegistrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RegistrarUsuarioActivity.class);
                startActivity(intent);
            }
        });

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    private void validarUsuario(String URL){
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    //Intent sin enviar parámetro
                    //Intent intent=new Intent(getApplicationContext(), PrincipalActivity.class);
                    //startActivity(intent);
                    String rol = "";
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String nombres = jsonObject.getString("nombres");
                        String apellidos = jsonObject.getString("apellidos");
                        rol = jsonObject.getString("rol");
                        nombrecompleto = nombres + ' ' + apellidos;
                        tvResultado.setText(nombrecompleto.toString());
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    if (rol.equals("Administrador")) {
                        //Intent con parámetro
                        Intent intent=new Intent(getApplicationContext(), PrincipalActivity.class);
                        intent.putExtra("valorsesion1",nombrecompleto);
                        startActivity(intent);
                    } else {
                        //Intent con parámetro
                        Intent intent=new Intent(getApplicationContext(), PrincipalUsuarioActivity.class);
                        intent.putExtra("valorsesion1",nombrecompleto);
                        startActivity(intent);
                    }


                }else{
                    Toast.makeText(MainActivity.this, "Usuario y/o clave incorrecta", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                tvResultado.setText(error.toString());
            }
        }){
            //@Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String, String>();
                parametros.put("usuario", edtUsuario.getText().toString());
                parametros.put("password", edtPassword.getText().toString());
                //return super.getParams();
                return parametros;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}