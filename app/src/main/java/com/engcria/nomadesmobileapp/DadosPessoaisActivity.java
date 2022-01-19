package com.engcria.nomadesmobileapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DadosPessoaisActivity extends AppCompatActivity {
    private FirebaseAuth usarioAutenticacao;
    private FirebaseUser userAcessivel = usarioAutenticacao.getInstance().getCurrentUser();

    EditText emailUsuario, nomeUsuario, cpfUsuario, celularUsuario,ruaUsuario, numeroCasaUsuario
            , bairroUsario, pontoReferenciaUsuario;
    CheckBox cidadeUsuario;

    String emailUserNotEditable;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_pessoais);

        Mensagem();

        emailUsuario            = findViewById(R.id.inputEmail);
        nomeUsuario             = findViewById(R.id.inputNome);
        cpfUsuario              = findViewById(R.id.inputCPF);
        celularUsuario          = findViewById(R.id.inputCelular);
        ruaUsuario              = findViewById(R.id.inputRua);
        numeroCasaUsuario       = findViewById(R.id.inputNumeroCasa);
        bairroUsario            = findViewById(R.id.inputBairro);
        pontoReferenciaUsuario  = findViewById(R.id.inputPontoReferencia);
        cidadeUsuario           = findViewById(R.id.checkBoxCidade);
        SimpleMaskFormatter maskCPF     = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher     addmaskCps  = new MaskTextWatcher(cpfUsuario, maskCPF);
        cpfUsuario.addTextChangedListener(addmaskCps);
        SimpleMaskFormatter maskCelular = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher     addmaskCel  = new MaskTextWatcher(celularUsuario, maskCelular);
        celularUsuario.addTextChangedListener(addmaskCel);

        emailUserNotEditable = userAcessivel.getEmail();
        emailUsuario.setText(emailUserNotEditable);
        if(userAcessivel!=null){
            Toast.makeText(DadosPessoaisActivity.this, "UsuárioLongado", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(DadosPessoaisActivity.this, "Usuário não logado", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("Aviso");
        dialogo.setMessage("O cadastro dos dados pessoais é obrigatório.");
        dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogo.create();
        dialogo.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void Mensagem(){
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setIcon(getDrawable(R.drawable.alert));
        dialogo.setTitle("Adicione seus dados");
        dialogo.setMessage("\tOlá!\n\t\tPara finalizar o seu cadastro, adicione seus dados pessoais.");
        dialogo.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogo.create();
        dialogo.show();
    }
    public void adicionarDados(View view){
        if(userAcessivel!=null){
            String usuario;
            usuario = userAcessivel.getUid();
            String  nomeCompleto = nomeUsuario.getText().toString();
            String  cpf          = cpfUsuario.getText().toString();
            String  celular      = celularUsuario.getText().toString();
            boolean cidade       = cidadeUsuario.isActivated();
            String  rua          = ruaUsuario.getText().toString();
            String  numero       = numeroCasaUsuario.getText().toString();
            String  bairro       = bairroUsario.getText().toString();
            String  pontoRefere  = pontoReferenciaUsuario.getText().toString();

            DadosUsuarios usu = new DadosUsuarios();
            usu.setUidUsuario(usuario);
            usu.setNomeUser(nomeCompleto);
            usu.setCpfUser(cpf);
            usu.setCelularUser(celular);
            usu.setRuaUser(rua);
            usu.setNumeroCasaUser(numero);
            usu.setBairroUser(bairro);
            usu.setPontoRefUser(pontoRefere);
            if(cidadeUsuario.isChecked()){
                usu.setCidadeUser(1);
            }
            usu.salvar();
            alerta();
        }else{
            Toast.makeText(DadosPessoaisActivity.this, "Usuario noup online", Toast.LENGTH_SHORT).show();
        }

    }
    public void alerta(){
        final FirebaseAuth user = FirebaseAuth.getInstance();
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle("Dados salvos!");
        alerta.setMessage("Os seus dados foram salvos com sucesso!\n\t\tRedirecionando para o" +
                "login");
        alerta.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                user.signOut();
                Intent loginScreen =
                        new Intent(DadosPessoaisActivity.this, LoguinActivity.class);
                startActivity(loginScreen);
                finish();
            }
        });
        alerta.create();
        alerta.show();
    }

}
