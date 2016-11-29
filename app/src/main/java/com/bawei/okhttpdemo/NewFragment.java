package com.bawei.okhttpdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gjl on 2016/11/22.
 */
public class NewFragment extends Fragment {

    private String name;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        name = bundle.getString("name");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView view = new TextView(getActivity());
        view.setText("Fragent->"+name);
        return view;
    }

    public static NewFragment getInstance(String name){
        NewFragment nf = null;
        if (nf==null){
            nf=new NewFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString("name",name);
        nf.setArguments(bundle);
        return nf;
    }
}
