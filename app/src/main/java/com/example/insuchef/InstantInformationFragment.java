package com.example.insuchef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstantInformationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstantInformationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public InstantInformationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment instantInformationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InstantInformationFragment newInstance(String param1, String param2) {
        InstantInformationFragment fragment = new InstantInformationFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instant_information, container, false);


        //Profile profile = new Profile();

        EditText instantSugar = view.findViewById(R.id.instantEdTxt);
        EditText carbRatio = view.findViewById(R.id.carbEdTxt);
        EditText targetSugar = view.findViewById(R.id.targetEdTxt);
        EditText weight = view.findViewById(R.id.weight);


        Button calculate = view.findViewById(R.id.calculateButton);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new CalculationFragment());
                fragmentTransaction.commit();

            }
        });


        return view;
    }
}