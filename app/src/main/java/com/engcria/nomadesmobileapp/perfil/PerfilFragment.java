package com.engcria.nomadesmobileapp.perfil;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.engcria.nomadesmobileapp.DadosPessoaisActivity;
import com.engcria.nomadesmobileapp.DadosUsuarios;
import com.engcria.nomadesmobileapp.LoguinActivity;
import com.engcria.nomadesmobileapp.R;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.github.rtoshiro.util.format.text.SimpleMaskTextWatcher;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.widget.Toast.*;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
     private EditText cpfUser, celularUser, emailUser, ruaUser, numCasaUser,
             bairroUser, pontoRefUser, nomeUser;
     private CheckBox cidadeUser;
     private FirebaseAuth autenticacao;
     private FirebaseUser user = autenticacao.getInstance().getCurrentUser();
     ProgressDialog progressDialog;
     ImageButton botao;
     private boolean logica;
    public PerfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);
        progressDialog = new ProgressDialog(getActivity());
        nomeUser      = view.findViewById(R.id.inputNome);
        cpfUser       = view.findViewById(R.id.inputCPF);
        celularUser   = view.findViewById(R.id.inputCelular);
        emailUser     = view.findViewById(R.id.inputEmail);
        ruaUser       = view.findViewById(R.id.inputRua);
        numCasaUser   = view.findViewById(R.id.inputNumeroCasa);
        bairroUser    = view.findViewById(R.id.inputBairro);
        pontoRefUser  = view.findViewById(R.id.inputPontoReferencia);
        cidadeUser    = view.findViewById(R.id.checkBoxCidade);
        botao         = (ImageButton) view.findViewById(R.id.imageButton);

        SimpleMaskFormatter maskCPF     = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher     addmaskCps  = new MaskTextWatcher(cpfUser, maskCPF);
        cpfUser.addTextChangedListener(addmaskCps);
        SimpleMaskFormatter maskCelular = new SimpleMaskFormatter("(NN)NNNNN-NNNN");
        MaskTextWatcher     addmaskCel  = new MaskTextWatcher(celularUser, maskCelular);
        cidadeUser.addTextChangedListener(addmaskCel);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                atualizarData();
            }
        });
        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        getDataUser();
    }

    public void getDataUser(){
        DatabaseReference firebase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usuario  = firebase.child("dadosUsuarios").child(user.getUid());
        usuario.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.setMessage("Aguarde...");
                progressDialog.show();
                DadosUsuarios usur = dataSnapshot.getValue(DadosUsuarios.class);
                emailUser.setText(user.getEmail());
                nomeUser.setText(usur.getNomeUser());
                cpfUser.setText(usur.getCpfUser());
                celularUser.setText(usur.getCelularUser());
                ruaUser.setText(usur.getRuaUser());
                numCasaUser.setText(usur.getNumeroCasaUser());
                bairroUser.setText(usur.getBairroUser());
                pontoRefUser.setText(usur.getPontoRefUser());
                if(usur.getCidadeUser()==1){
                    cidadeUser.setChecked(true);
                }
                progressDialog.dismiss();
                logica = true;
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Erro", LENGTH_SHORT).show();
            }
        });
    }
    public boolean verificarDownload(){
        String ponto = pontoRefUser.getText().toString();
        if(logica){
            progressDialog.dismiss();
            return true;
        }else {
            progressDialog.setTitle("Baixando dados");
            progressDialog.setMessage("Aguarde...");
            progressDialog.show();
            return false;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        progressDialog.dismiss();
    }


    public void atualizarData(){
        if(user!=null){
            String usuario;
            usuario = user.getUid();
            String  nomeCompleto = nomeUser.getText().toString();
            String  cpf          = cpfUser.getText().toString();
            String  celular      = celularUser.getText().toString();
            boolean cidade       = cidadeUser.isActivated();
            String  rua          = ruaUser.getText().toString();
            String  numero       = numCasaUser.getText().toString();
            String  bairro       = bairroUser.getText().toString();
            String  pontoRefere  = pontoRefUser.getText().toString();

            DadosUsuarios usu = new DadosUsuarios();
            usu.setUidUsuario(usuario);
            usu.setNomeUser(nomeCompleto);
            usu.setCpfUser(cpf);
            usu.setCelularUser(celular);
            usu.setRuaUser(rua);
            usu.setNumeroCasaUser(numero);
            usu.setBairroUser(bairro);
            usu.setPontoRefUser(pontoRefere);
            if(cidadeUser.isChecked()){
                usu.setCidadeUser(1);
            }
            usu.salvar();
            alerta();
        }else{
            Toast.makeText(getActivity(), "Usuario noup online", Toast.LENGTH_SHORT).show();
        }

    }

    public void alerta(){
        final FirebaseAuth user = FirebaseAuth.getInstance();
        AlertDialog.Builder alerta = new AlertDialog.Builder(getActivity());
        alerta.setTitle("Dados Atualizados!");
        alerta.setMessage("Os seus dados foram atualizados com sucesso!\n");
        alerta.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getDataUser();
            }
        });
        alerta.create();
        alerta.show();
    }
}
