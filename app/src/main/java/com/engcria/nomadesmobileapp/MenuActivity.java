package com.engcria.nomadesmobileapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.engcria.nomadesmobileapp.perfil.PerfilFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MenuActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menuinicial,
                R.id.perfil,
                R.id.pedidos,
                R.id.historico,
                R.id.chat,
                R.id.Hamburger,
                R.id.Espaguetes,
                R.id.Pizzas,
                R.id.PizzasEspeciais,
                R.id.configuracoes,
                R.id.instragram,
                R.id.facebook,
                R.id.lonOut,
                R.id.encerrar,
                R.id.sobre)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        usuarioAutenticado();
    }
    public void usuarioAutenticado(){
        if(auth!=null){
            Toast.makeText(MenuActivity.this, "Usuário Autenticado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MenuActivity.this, "Usuário noup Autenticado", Toast.LENGTH_SHORT).show();
        }
    }
    public void editarDados(MenuItem item){
        Intent editarDados = new Intent(MenuActivity.this, DadosPessoaisActivity.class);
        startActivity(editarDados);
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuActivity.this);
        dialogo.setTitle("|Alert|->");
        dialogo.setMessage("Deseja fechar a aplicação?");
        dialogo.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialogo.setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MenuActivity.this, "Ação Cancelada!", Toast.LENGTH_SHORT).show();
            }
        });
        dialogo.create();
        dialogo.show();
    }
    public void Sobre(MenuItem item){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(MenuActivity.this);
        dialogo.setIcon(R.drawable.sobre);
        dialogo.setTitle("Sobre");
        dialogo.setMessage("Nômades Mobile\nVersão: 1.0.0(beta-release)\nDev: EngCria.com");
        dialogo.setPositiveButton("fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogo.create();
        dialogo.show();
    }

    public void LongOut(MenuItem item){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth usuario = FirebaseAuth.getInstance();
        usuario.signOut();
        Intent telaLoguin = new Intent(MenuActivity.this, LoguinActivity.class);
        Toast.makeText(MenuActivity.this, "SingOut...", Toast.LENGTH_SHORT).show();
        startActivity(telaLoguin);
        finish();
    }
    public void instagramNomades(MenuItem item){
        Intent nomades = new Intent(Intent.ACTION_VIEW);
        nomades.setData(Uri.parse("https://instagram.com/nomadespizzas?igshid=12p7m3bnly32z"));
        startActivity(nomades);
    }
    public void facebookNomades(MenuItem item){
        Intent nomades = new Intent(Intent.ACTION_VIEW);
        nomades.setData(Uri.parse("https://www.facebook.com/nomades.pizzas"));
        startActivity(nomades);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
