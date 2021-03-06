package com.dsc.suka.volunteerapp.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dsc.suka.volunteerapp.R;
import com.dsc.suka.volunteerapp.adapter.LatestContributionAdapter;
import com.dsc.suka.volunteerapp.model.ContributionItems;
import com.dsc.suka.volunteerapp.network.ApiClient;
import com.dsc.suka.volunteerapp.network.ApiInterface;
import com.dsc.suka.volunteerapp.presenter.ContributionPresenter;
import com.dsc.suka.volunteerapp.service.ApiInterfaceService;
import com.dsc.suka.volunteerapp.view.ContributionView;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements ContributionView {
    private ApiInterface mApiInterfaceService;
    private RecyclerView recyclerView;
    private LatestContributionAdapter mAdapter;
    private List<ContributionItems> mContributionItems;
    private ContributionPresenter presenter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = v.findViewById(R.id.rv_latest_contributions);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        mApiInterfaceService = ApiClient.getClient().create(ApiInterface.class);
        presenter = new ContributionPresenter(this, mApiInterfaceService);
        presenter.getLatestContributionList();

        return v;
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showContributionList(List<ContributionItems> contributionData) {
        mContributionItems = contributionData;
        mAdapter = new LatestContributionAdapter(mContributionItems, getContext());
        recyclerView.setAdapter(mAdapter);
    }
}
