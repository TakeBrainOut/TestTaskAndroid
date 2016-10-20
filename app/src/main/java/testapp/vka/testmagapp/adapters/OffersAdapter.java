package testapp.vka.testmagapp.adapters;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import testapp.vka.testmagapp.Const;
import testapp.vka.testmagapp.Model.Offer;
import testapp.vka.testmagapp.Model.Param;
import testapp.vka.testmagapp.R;

/**
 * Created by kirill on 18.10.2016.
 */

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {
    private ArrayList<Offer> offers;

    public OffersAdapter(ArrayList<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_view_offer, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Offer offer = offers.get(position);
        if (offer.getPictureUrl() != null) {
            holder.iconView.setImageURI(Uri.parse(offer.getPictureUrl()));
        } else {
            holder.iconView.setImageResource(R.drawable.ic_no_image);
        }
        holder.priceTextView.setText(String.format("%s руб.", offer.getPrice()));
        holder.titleTextView.setText(offer.getName());
        Param weightParam = offer.getParamByName(Const.WEIGHT_PARAM);
        if (weightParam != null) {
            holder.weightTextView.setText(weightParam.getText());
        } else {
            holder.weightTextView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return offers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.offer_icon)
        SimpleDraweeView iconView;

        @BindView(R.id.offer_title_text_view)
        TextView titleTextView;

        @BindView(R.id.price_text_view)
        TextView priceTextView;

        @BindView(R.id.weight_text_view)
        TextView weightTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
