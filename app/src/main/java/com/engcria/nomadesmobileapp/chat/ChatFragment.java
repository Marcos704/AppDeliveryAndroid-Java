package com.engcria.nomadesmobileapp.chat;

import android.Manifest;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.engcria.nomadesmobileapp.Permissao;
import com.engcria.nomadesmobileapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChatFragment extends Fragment {
    public String[] permissoesAdd = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CAMERA
    };
    public ChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        Permissao permissoes = new Permissao();
        permissoes.validarPermissoes(permissoesAdd, getActivity(),1);
        return view;
    }
}
