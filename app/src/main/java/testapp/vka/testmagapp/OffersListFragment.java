package testapp.vka.testmagapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import testapp.vka.testmagapp.Model.Offer;
import testapp.vka.testmagapp.adapters.OffersAdapter;
import testapp.vka.testmagapp.gui.DividerItemDecoration;
import testapp.vka.testmagapp.gui.RecyclerItemClickListener;

public class OffersListFragment extends Fragment {
    @BindView(R.id.offers_rec_view)
    RecyclerView offersRecyclerView;

    private static final String OFFERS_ARG = "OFFERS_ARG";
    private ArrayList<Offer> offers;
    private String categoryName;
    public static final String CATEGORY_NAME_ARG = "CATEGORY_NAME_ARG";


    public OffersListFragment() {
    }

    public static OffersListFragment newInstance(ArrayList<Offer> offers, String category) {
        OffersListFragment fragment = new OffersListFragment();
        Bundle args = new Bundle();
        args.putString(CATEGORY_NAME_ARG, category);
        args.putParcelableArrayList(OFFERS_ARG, offers);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            offers = getArguments().getParcelableArrayList(OFFERS_ARG);
            categoryName = getArguments().getString(CATEGORY_NAME_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_offers_list, container, false);
        ButterKnife.bind(this, v);
        drawRecyclerView();
        return v;
    }

    private void drawRecyclerView() {
        OffersAdapter adapter = new OffersAdapter(offers);
        offersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        offersRecyclerView.setAdapter(adapter);
        offersRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            drawFragment(ConcreteOfferFragment.newInstance(offers.get(position)));
        }));
        offersRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
    }

    public void drawFragment(Fragment fragment) {
        getFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        drawTitleBar();
    }

    private void drawTitleBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(categoryName);

    }

}
