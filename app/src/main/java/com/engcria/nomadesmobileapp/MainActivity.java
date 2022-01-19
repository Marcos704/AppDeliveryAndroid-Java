package com.engcria.nomadesmobileapp;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseUser usuario = FirebaseAuth.getInstance().getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("aviso!");
        dialog.setMessage("Realmente deseja finalizar a aplicação?");
        dialog.setIcon(getDrawable(R.drawable.alert));
        dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "finaliando...", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        dialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        dialog.create();
        dialog.show();
    }
    public void loguin(View view){
        Intent telaLoguin = new Intent(this, LoguinActivity.class);
        InternetStatus internetStatus = new InternetStatus(this);
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        if(internetStatus.internetAcess(this)){
            startActivity(telaLoguin);
            finish();
        }else{
            dialogo.setTitle("Por favor, verifique o seu acesso a internet.");
            dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogo.create();
            dialogo.show();
        }
    }
    public void cadastro(View view){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        InternetStatus internetStatus = new InternetStatus(this);

        if(internetStatus.internetAcess(this)){
            Intent telaCadastro = new Intent(MainActivity.this, CadastroActivity.class);
            startActivity(telaCadastro);
            finish();
        }else{
            dialogo.setTitle("Por favor, verifique seu acesso a internet.");
            dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            dialogo.create();
            dialogo.show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if(user!=null){
            Toast.makeText(MainActivity.this, "Recuperando sessão...", Toast.LENGTH_SHORT).show();
            Intent telaMenu = new Intent(MainActivity.this, MenuActivity.class);
            startActivity(telaMenu);
            finish();
        }else{
            Toast.makeText(MainActivity.this, "Inicie uma sessão", Toast.LENGTH_SHORT).show();
        }
    }
}
