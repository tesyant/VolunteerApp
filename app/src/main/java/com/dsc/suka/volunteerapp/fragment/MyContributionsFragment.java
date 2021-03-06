package com.dsc.suka.volunteerapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsc.suka.volunteerapp.R;
import com.dsc.suka.volunteerapp.activity.MyContributionExtendedActivity;
import com.dsc.suka.volunteerapp.adapter.MyContributionAdapter;

import com.dsc.suka.volunteerapp.model.ContributionItems;

import com.dsc.suka.volunteerapp.network.ApiInterface;
import com.dsc.suka.volunteerapp.service.ApiClientService;
import com.dsc.suka.volunteerapp.service.ApiInterfaceService;

import com.dsc.suka.volunteerapp.presenter.ContributionPresenter;
import com.dsc.suka.volunteerapp.util.ItemClickSupport;
import com.dsc.suka.volunteerapp.view.ContributionView;


import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyContributionsFragment extends Fragment implements ContributionView {
    ApiInterface mApiInterfaceService;
    private RecyclerView mRecyclerView;
    private MyContributionAdapter mAdapter;
    private List<ContributionItems> mContributionItemsList;
    private ContributionPresenter presenter;

    public List<ContributionItems> getmContributionItemsList() {
        return mContributionItemsList;
    }

    public void setmContributionItemsList(List<ContributionItems> mContributionItemsList) {
        this.mContributionItemsList = mContributionItemsList;
    }

    public MyContributionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_contributions, container, false);

        mRecyclerView = view.findViewById(R.id.rv_my_contribution);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Intent intent = new Intent(getContext(), MyContributionExtendedActivity.class);
                intent.putExtra(MyContributionExtendedActivity.EXTRA_CONTRIBUTION, getmContributionItemsList().get(position));
                startActivity(intent);
            }
        });

        mApiInterfaceService = ApiClientService.getClient().create(ApiInterface.class);
        presenter = new ContributionPresenter(this, mApiInterfaceService);
        presenter.getMyContributionList();

        return view;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showContributionList(List<ContributionItems> contributionData) {
        setmContributionItemsList(contributionData);
        mAdapter = new MyContributionAdapter(mContributionItemsList, getContext());
        mRecyclerView.setAdapter(mAdapter);
    }
}
