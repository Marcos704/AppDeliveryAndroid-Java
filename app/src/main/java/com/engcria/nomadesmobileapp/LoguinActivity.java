package com.engcria.nomadesmobileapp;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Context;

import java.sql.Connection;

public class LoguinActivity extends AppCompatActivity {
    FirebaseAuth Autenticacao;
    EditText emailUsuario;
    EditText senhaUsario;
    private ProgressBar progressBarCarregamento;
    private FirebaseAuth autenticacao;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loguin);

        progressBarCarregamento = findViewById(R.id.progressBarCarregamento);
        emailUsuario = findViewById(R.id.email);
        senhaUsario = findViewById(R.id.senha);
        progressBarCarregamento.setVisibility(View.GONE);
        emailUsuario.setText("");
        senhaUsario.setText("");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(LoguinActivity.this, MainActivity.class);
        startActivity(voltar);
        finish();
    }

    public void telaRedefinirPassword(View view) {
        Intent telaRedefinir = new Intent(LoguinActivity.this, RedefinirPasswordActivity.class);
        startActivity(telaRedefinir);
        finish();
    }

    public void telaMenu(View view) {
        loginUser();
    }

    public void loginUser() {
        final String email = String.valueOf(emailUsuario.getText());
        String senha = String.valueOf(senhaUsario.getText());
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        progressDialog = new ProgressDialog(this);
        Autenticacao = FirebaseAuth.getInstance();
        if (email.equals("") && senha.equals("")) {
            emailUsuario.setError("Preencha todos os campos!");
            senhaUsario.setError("Preencha todos os campos!");
        } else if (email.equals("") && !senha.equals("")) {
            emailUsuario.setError("Informe o seu endereço de email.");
        } else if (!email.equals("") && senha.equals("")) {
            senhaUsario.setError("Informe a sua senha");
        } else {
            progressDialog.setMessage("Carregando...");
            progressDialog.show();
            Autenticacao.signInWithEmailAndPassword(email, senha).
                    addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressBarCarregamento.setVisibility(View.GONE);
                                progressDialog.dismiss();
                                Toast.makeText(LoguinActivity.this, "Sucesso!", Toast.LENGTH_SHORT).show();
                                Intent menu = new Intent(LoguinActivity.this, MenuActivity.class);
                                startActivity(menu);
                                finish();
                            } else {
                                progressBarCarregamento.setVisibility(View.GONE);
                                String exeption = "";
                                try {
                                    throw task.getException();
                                } catch (FirebaseAuthInvalidUserException E) {
                                    progressDialog.dismiss();
                                    dialogo.setTitle("Usuário Inválido");
                                    dialogo.setMessage("Não foi possível encontrar contas\nrelacionadas a " + email);
                                    dialogo.setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    dialogo.create();
                                    dialogo.show();
                                } catch (FirebaseAuthInvalidCredentialsException E) {
                                    progressDialog.dismiss();
                                    dialogo.setTitle("Credenciais Inválidas");
                                    dialogo.setMessage("Verifique se digitou corretamente o seu email e senha.");
                                    dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    });
                                    dialogo.create();
                                    dialogo.show();
                                } catch (Exception e) {
                                    progressDialog.dismiss();
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
        }
    }
}
