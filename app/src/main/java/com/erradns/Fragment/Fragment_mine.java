package com.erradns.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.erradns.Activity.MyinfoActivity;
import com.erradns.Sophix.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_mine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_mine extends Fragment implements View.OnClickListener {
    private View rootView;
    private LinearLayout mine_center;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_mine() {
        // Required empty public constructor
    }

    public static Fragment_mine newInstance(String param1, String param2) {
        Fragment_mine fragment = new Fragment_mine();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        mine_center = rootView.findViewById(R.id.mine_center);
        mine_center.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mine_center:
                Intent to_mine_info = new Intent(getActivity(), MyinfoActivity.class);
                startActivity(to_mine_info);
                break;
        }
    }
}