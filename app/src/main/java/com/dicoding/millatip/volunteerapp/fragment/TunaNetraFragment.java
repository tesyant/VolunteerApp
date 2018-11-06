package com.dicoding.millatip.volunteerapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.millatip.volunteerapp.R;
import com.dicoding.millatip.volunteerapp.adapter.RequestAdapter;
import com.dicoding.millatip.volunteerapp.model.RequestItems;
import com.dicoding.millatip.volunteerapp.model.RequestModel;
import com.dicoding.millatip.volunteerapp.rest.ApiClient;
import com.dicoding.millatip.volunteerapp.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TunaNetraFragment extends Fragment {
    ApiInterface mApiInterface;
    private RecyclerView mRecyclerView;
    private RequestAdapter mAdapter;


    public TunaNetraFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.tuna_netra_request, container, false);

        mRecyclerView = v.findViewById(R.id.rv_request_tuna_netra);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        refresh();

        return v;
    }

    public void refresh() {
        Call<RequestModel> requestModelCall = mApiInterface.getRequests();
        requestModelCall.enqueue(new Callback<RequestModel>() {
            @Override
            public void onResponse(Call<RequestModel> call, Response<RequestModel> response) {
                List<RequestItems> requestItemsList = response.body().getRequestItems();
                Log.d("Retrofit Get", "Request Count: " + String.valueOf(requestItemsList.size()));
                mAdapter = new RequestAdapter(requestItemsList, getContext());
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<RequestModel> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });

    }

}