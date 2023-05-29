package com.example.insuchef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalculationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment calculationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculationFragment newInstance(String param1, String param2) {
        CalculationFragment fragment = new CalculationFragment();
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

    ProfileManager profileManager;
    Profile profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_calculation, container, false);
        profileManager = ProfileManager.getInstance(requireContext());
        profile = profileManager.getProfile();
        Calc calc = new Calc();
        calc.setTargetBloodSugar(profile.getTargetBloodSugar());
        calc.setBloodSugar(profile.getInstantBloodSugar());
        calc.setIsf(profile.getInsulinSensivity());
        calc.setRatio(profile.getCarbInsulinRatio());
        double carb = 100;
        calc.setCarbCount(carb);
        double bolus = calc.calculateBolus();

        TextView carbResult = view.findViewById(R.id.carbResult);
        TextView insulinResult = view.findViewById(R.id.insulinResult);
        TextView timeResult = view.findViewById(R.id.timeResult);

        carbResult.setText(calc.getCarbResult(carb));
        insulinResult.setText(calc.getInsulinResult(bolus));
        timeResult.setText(calc.getTimeResult(bolus));



        return view;


    }
}