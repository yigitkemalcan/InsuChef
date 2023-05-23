package com.example.insuchef;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DistributionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DistributionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerView recyclerView;
    private EditText totalCarbEditText;
    private FoodAdapter adapter;
    private ProfileManager profileManager;
    private Profile profile;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    int carbRestriction;

    public DistributionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment distributionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DistributionFragment newInstance(String param1, String param2) {
        DistributionFragment fragment = new DistributionFragment();
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
        profileManager = ProfileManager.getInstance(requireContext());
        profile = profileManager.getProfile();
        View view = inflater.inflate(R.layout.fragment_distribution, container, false);

        totalCarbEditText = view.findViewById(R.id.totalCarbEditText);
        recyclerView = view.findViewById(R.id.recyclerView);
        adapter = new FoodAdapter(Food.setData(),requireContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if(MainFragment.whichMeal == 0){
                carbRestriction = profile.getBreakfastRestriction();
        }
        else if (MainFragment.whichMeal == 1) {
            carbRestriction = profile.getLunchRestriction();
        }
        else {
            carbRestriction = profile.getDinnerRestriction();
        }
        if(carbRestriction!=-1){
            totalCarbEditText.setText(String.valueOf(carbRestriction));
            adapter.distribute(carbRestriction);
        }
        totalCarbEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(carbRestriction!=-1){
                    if(adapter.isAllGramInfoFull()){
                        //TODO total karbonhidratı kontrol et ve hepsini aynı oranda arttır veya azalt
                        adapter.distributeProportional(carbRestriction);
                    }
                    else if(adapter.isAllGramInfoNull()){
                        adapter.distribute(carbRestriction);
                    }
                    else{
                        //TODO mevcut choyu hesapla. totalden büyükse yoksay ve distribute. totalden küçükse yalnızca boş olanları distribute.
                        
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        adapter.setOnItemClickListener(new FoodAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(Food food, int position) {
                if(food.isLocked()){
                    food.setLocked(false);
                }
                else if(food.getGram()==-1){
                    Toast.makeText(requireContext(), "Not Possible!", Toast.LENGTH_SHORT).show();
                }
                else{
                    food.setLocked(true);
                }
            }

            @Override
            public void OnButtonCLick(Food food, int position) {
                adapter.removeItem(position);
            }

            @Override
            public void OnGramTextChanged(Food food, int position, String gram) {
                food.setGram(Integer.parseInt(gram));
                adapter.distribute(carbRestriction);
            }

        });

        Button instantInfo = view.findViewById(R.id.instantInfo);
        instantInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainer, new InstantInformationFragment());
                fragmentTransaction.commit();
            }
        });

        return view;
    }

}