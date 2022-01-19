package com.engcria.nomadesmobileapp;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public boolean validarPermissoes(String[] permissoes, Activity activity, int codPermissao){
        if(Build.VERSION.SDK_INT>=23){
            List<String> permissoesList = new ArrayList<>();
            for( String permissao:permissoes){
               boolean temPermissao = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;
                if(!temPermissao){
                    permissoesList.add(permissao);
                }
            }
            if(permissoesList.isEmpty()){
                return true;
            }
            String[] novasPermissoes = new String[permissoesList.size()];
            permissoesList.toArray(novasPermissoes);
            ActivityCompat.requestPermissions(activity,novasPermissoes,codPermissao);
        }
        return true;
    }
}
