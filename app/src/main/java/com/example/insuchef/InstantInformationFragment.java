package com.example.insuchef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

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

    private ProfileManager profileManager;
    private Profile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_instant_information, container, false);

        profileManager = ProfileManager.getInstance(requireContext());
        profile = profileManager.getProfile();

        EditText instantSugar = view.findViewById(R.id.instantSugar);
        EditText carbRatio = view.findViewById(R.id.carbRatio);
        EditText targetSugar = view.findViewById(R.id.targetSugar);
        EditText weight = view.findViewById(R.id.weight);

        targetSugar.setText(String.valueOf(profile.getTargetBloodSugar()));
        weight.setText(String.valueOf(profile.getInsulinSensivity()));

        Button calculate = view.findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(instantSugar.getText().toString()) || TextUtils.isEmpty(carbRatio.getText().toString()) || TextUtils.isEmpty(targetSugar.getText().toString()) || TextUtils.isEmpty(weight.getText().toString()) ){
                    Toast.makeText(getContext(), "Information is missing!", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(instantSugar.getText().toString()) <= 0 || Integer.parseInt(carbRatio.getText().toString()) <= 0 || Integer.parseInt(targetSugar.getText().toString()) <= 0 || Integer.parseInt(weight.getText().toString()) <= 0 ){
                    Toast.makeText(getContext(), "Information is wrong!", Toast.LENGTH_SHORT).show();
                }
                else {
                    profile.setInstantBloodSugar(Integer.parseInt(instantSugar.getText().toString()));
                    profile.setCarbInsulinRatio(Integer.parseInt(carbRatio.getText().toString()));
                    profile.setTargetBloodSugar(Integer.parseInt(targetSugar.getText().toString()));
                    profile.setInsulinSensivity(Integer.parseInt(weight.getText().toString()));
                    profileManager.saveProfile(profile);

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragmentContainer, new CalculationFragment());
                    fragmentTransaction.commit();
                }

            }
        });


        return view;
    }
}