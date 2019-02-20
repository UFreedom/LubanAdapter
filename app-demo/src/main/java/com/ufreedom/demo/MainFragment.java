package com.ufreedom.demo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ufreedom.demo.model.ImageModel;
import com.ufreedom.demo.model.ImageTextMixedModel;
import com.ufreedom.demo_res.Zootopia;
import com.ufreedom.lubanadapter.IRVModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by UFreedom on 2/17/19.
 */
public class MainFragment extends Fragment {

    private RecyclerView recyclerView;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = rootView.findViewById(R.id.recycler_view);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DemoAdapter demoAdapter = new DemoAdapter(getContext());

        List<Zootopia> zootopiaList = Zootopia.getZootopiaRoleList(requireContext());

        List<IRVModel> irvModelList = new ArrayList<>();

        for (Zootopia zootopia : zootopiaList) {
            irvModelList.add(new ImageModel(zootopia.getRolePicture()));
            irvModelList.add(new ImageTextMixedModel(zootopia.getRolePicture(),zootopia.getRoleName()));
        }

        Collections.shuffle(irvModelList);
        demoAdapter.setModelList(irvModelList);

        recyclerView.setAdapter(demoAdapter);
    }
}
