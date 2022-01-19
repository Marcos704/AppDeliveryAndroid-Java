package com.engcria.nomadesmobileapp.hamburge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.engcria.nomadesmobileapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HamburgesFragment extends Fragment {

    public HamburgesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_hamburges, container, false);
    }
}
