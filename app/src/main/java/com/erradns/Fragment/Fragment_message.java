package com.erradns.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.erradns.Sophix.R;

public class Fragment_message extends Fragment {
    private View rootView;
    private Toolbar title_bar;
    private TextView title;
    private ImageView back;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public Fragment_message() {
    }


    public static Fragment_message newInstance(String param1, String param2) {
        Fragment_message fragment = new Fragment_message();
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
        rootView = inflater.inflate(R.layout.fragment_message, container, false);
        back = rootView.findViewById(R.id.back_img);
        back.setVisibility(View.INVISIBLE);
        title = rootView.findViewById(R.id.title);
        title.setText("消息");
        //
        //mapView=rootView.findViewById(R.id.map_test);
        //mapView.onCreate(savedInstanceState);

        return rootView;
    }
}