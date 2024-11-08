package com.example.anyplans;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PrincipalUsuarioActivity extends AppCompatActivity {
    TextView tvSaludoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal_usuario);

        tvSaludoUsuario =  findViewById(R.id.tvSaludoUsuario);

        Bundle bundle=getIntent().getExtras();
        String DatoNombre=bundle.getString("valorsesion1");

        tvSaludoUsuario.setText("Bienvenido(a) " + DatoNombre.toUpperCase());



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainPrincipalUsuario), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}