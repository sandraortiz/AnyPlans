package com.example.anyplans;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class PrincipalActivity extends AppCompatActivity {
    EditText edtDni;
    Button btnEliminarUsuario, btnModificarUsuario;
    TextView tvSaludo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        //btnPrueba = findViewById(R.id.btnPrueba);
        tvSaludo =  findViewById(R.id.tvSaludo);
        edtDni=(EditText)findViewById(R.id.edtDni);
        btnEliminarUsuario=(Button) findViewById(R.id.btnEliminarUsuario);
        btnModificarUsuario=(Button) findViewById(R.id.btnModificarUsuario);

        Bundle bundle=getIntent().getExtras();
        String DatoNombre=bundle.getString("valorsesion1");

        tvSaludo.setText("Bienvenido " + DatoNombre.toUpperCase());

        //Toast.makeText(getApplicationContext(), DatoNombre, Toast.LENGTH_SHORT).show();

        btnModificarUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getApplicationContext(), ActualizarUsuarioActivity.class);
                intent.putExtra("valorsesionDni",edtDni.getText().toString());
                startActivity(intent);
            }
        });

        btnEliminarUsuario.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                ejecutarServicioEliminarUsuario("https://www.grupoxpertos.com/upn/android/developeru/eliminar_usuario.php");
            }
        });

        /*ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPrincipal), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });*/
    }

    private void ejecutarServicioEliminarUsuario(String URL2){
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
                return parametros;

            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}