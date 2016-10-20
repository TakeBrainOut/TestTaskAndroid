package testapp.vka.testmagapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import testapp.vka.testmagapp.Const;
import testapp.vka.testmagapp.Model.Category;
import testapp.vka.testmagapp.R;

/**
 * Created by kirill on 17.10.2016.
 */

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private ArrayList<Category> categories;
    private Context context;

    public CategoriesAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_category, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryIcon.setImageResource(context.getResources().getIdentifier(Const.CATEGORY_IMAGE_PREFIX + category.getId(), "drawable", context.getPackageName()));
        holder.text.setText(category.getName());
    }


    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.category_image)
        public ImageView categoryIcon;

        @BindView(R.id.category_text_view)
        public TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}