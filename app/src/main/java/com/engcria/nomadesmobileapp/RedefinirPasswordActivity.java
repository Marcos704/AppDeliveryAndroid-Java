package com.engcria.nomadesmobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RedefinirPasswordActivity extends AppCompatActivity {
    FirebaseAuth emailAutenticacao = FirebaseAuth.getInstance();
    EditText emailRecuperacao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redefinir_password);
        emailRecuperacao = findViewById(R.id.email);
        emailRecuperacao.setText("");
    }
    public void redefinirSenha(View view) {
        final String emailRec = String.valueOf(emailRecuperacao.getText());
        final AlertDialog.Builder alerta = new AlertDialog.Builder(this);

        if (emailRec.equals("")) {
            Toast.makeText(RedefinirPasswordActivity.this, "Insira um email!", Toast.LENGTH_SHORT).show();
        }else{
        emailAutenticacao.sendPasswordResetEmail(emailRec).addOnCompleteListener(new OnCompleteListener<Void>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    alerta.setTitle("Suporte@nômades.com");
                    alerta.setIcon(getDrawable(R.drawable.alert));
                    alerta.setMessage("Enviamos um email de recuperação para:\n\t" + emailRec);
                    alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(RedefinirPasswordActivity.this, ".....", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alerta.create();
                    alerta.show();
                } else {
                    alerta.setTitle("Suporte@nômades.com");
                    alerta.setIcon(getDrawable(R.drawable.alert));
                    alerta.setMessage("Não foi possível enviar o email de recuperação.\nPor favor, entre em contato com o nosso suporte:\n\tsuporte@nômades.com.");
                    alerta.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alerta.create();
                    alerta.show();
                }
                }
        });
        }
    }
    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(RedefinirPasswordActivity.this, LoguinActivity.class);
        startActivity(voltar);
        finish();
    }
}
