package com.example.a1171832jumanafinalproj.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a1171832jumanafinalproj.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DescriptionPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DescriptionPageFragment extends Fragment {




    public DescriptionPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DescriptionPageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DescriptionPageFragment newInstance(String param1, String param2) {
        DescriptionPageFragment fragment = new DescriptionPageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_description_page, container, false);
        setHasOptionsMenu(true);

        return root ;
    }
}