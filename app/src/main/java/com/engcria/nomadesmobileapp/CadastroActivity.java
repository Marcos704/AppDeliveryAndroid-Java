package com.engcria.nomadesmobileapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import static android.widget.Toast.*;

public class CadastroActivity extends AppCompatActivity {
    private     FirebaseAuth Autenticacao = FirebaseAuth.getInstance();
    EditText    emailUsario;
    EditText    senhaUsario;
    EditText    confirmarSenha;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        progressDialog = new ProgressDialog(this);
        emailUsario     = findViewById(R.id.email);
        senhaUsario     = findViewById(R.id.senha);
        confirmarSenha  = findViewById(R.id.senhaconfirm);
        emailUsario.setText("");
        senhaUsario.setText("");
        confirmarSenha.setText("");
    }

    @Override
    public void onBackPressed() {
        Intent voltar = new Intent(CadastroActivity.this, MainActivity.class);
        startActivity(voltar);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void cadastro(View view){
        String emailPrimario    = String.valueOf(emailUsario.getText());
        String senhaPrimaria    = String.valueOf(senhaUsario.getText());
        String senhaSecundaria  = String.valueOf(confirmarSenha.getText());
        if(emailPrimario.equals("")&&senhaPrimaria.equals("")&&senhaSecundaria.equals("")){
            emailUsario.setError("Campo Obrgatório",getDrawable(R.drawable.alert));
            senhaUsario.setError("Campo Obrigatório",getDrawable(R.drawable.alert));
            confirmarSenha.setError("Campo Obrigatório", getDrawable(R.drawable.alert));
        }else
            if(emailPrimario.equals("")&&!senhaPrimaria.equals("")&&!senhaSecundaria.equals("")){
                emailUsario.setError("Informe um email",getDrawable(R.drawable.alert));
            }else
                if(!emailPrimario.equals("")&&senhaPrimaria.equals("")&&senhaSecundaria.equals("")){
                    senhaUsario.setError("Informe uma senha",getDrawable(R.drawable.alert));
                }else
                    if(!emailPrimario.equals("")){
                        if(senhaPrimaria.equals(senhaSecundaria)){
                            cadastrarUsuario();
                            progressDialog.setMessage("Carregando...");
                            progressDialog.show();
                        }else{
                            senhaUsario.setError("Erro",getDrawable(R.drawable.alert));
                            confirmarSenha.setError("Senhas não compatíveis", getDrawable(R.drawable.alert));
                        }
                    }
    }
    public void cadastrarUsuario(){
        final String email = String.valueOf(emailUsario.getText());
        String senha = String.valueOf(senhaUsario.getText());
        final AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        Autenticacao = FirebaseAuth.getInstance();
        Autenticacao.createUserWithEmailAndPassword(email,senha).
                addOnCompleteListener(CadastroActivity.this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Intent i = new Intent(CadastroActivity.this, DadosPessoaisActivity.class);
                            startActivity(i);
                            finish();
                        }else {
                            String exception = "";
                            try {
                                throw task.getException();
                            } catch (FirebaseAuthWeakPasswordException e){
                                senhaUsario.setError("Digite uma senha mais forte.\n",getDrawable(R.drawable.alert));
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                dialogo.setTitle("Credenciais Inválidas");
                                dialogo.setMessage("Verifique o se digito um email e senhas\nválidos.");
                                dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                dialogo.create();
                                dialogo.show();
                            }catch (FirebaseAuthUserCollisionException e){
                                dialogo.setTitle("Credenciais Inválidas");
                                dialogo.setMessage("O usuário '"+email+"' já está cadastrado.\nTente realizar o login");
                                dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });
                                dialogo.create();
                                dialogo.show();
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
}
