package testapp.vka.testmagapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import testapp.vka.testmagapp.Model.Category;
import testapp.vka.testmagapp.adapters.CategoriesAdapter;
import testapp.vka.testmagapp.gui.RecyclerItemClickListener;

public class CategoriesFragment extends Fragment {
    public static final String CATEGORIES_ARG = "CATEGORIES_ARG";

    @BindView(R.id.categories_rec_view)
    RecyclerView categoriesRecView;

    private ArrayList<Category> categories;
    private Unbinder unbinder;

    private OnChooseCategoryListener mListener;

    public static CategoriesFragment newInstance(ArrayList<Category> categories) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(CATEGORIES_ARG, categories);
        CategoriesFragment fr = new CategoriesFragment();
        fr.setArguments(args);
        return fr;
    }

    public CategoriesFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categories = getArguments().getParcelableArrayList(CATEGORIES_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        unbinder = ButterKnife.bind(this, v);
        drawRecView();
        return v;
    }

    private void drawRecView() {
        CategoriesAdapter adapter = new CategoriesAdapter(categories, getContext());
        categoriesRecView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        categoriesRecView.setAdapter(adapter);
        categoriesRecView.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), (view, position) -> {
            mListener.onChooseCategory(categories.get(position), position);
        }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnChooseCategoryListener) {
            mListener = (OnChooseCategoryListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnChooseCategoryListener {
        void onChooseCategory(Category category, int position);
    }

    @Override
    public void onResume() {
        super.onResume();
        drawTitleBar();
    }

    private void drawTitleBar() {
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.catalog);

    }

}
