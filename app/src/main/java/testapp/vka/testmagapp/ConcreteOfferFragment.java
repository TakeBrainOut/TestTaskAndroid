package testapp.vka.testmagapp;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import testapp.vka.testmagapp.Model.Offer;
import testapp.vka.testmagapp.Model.Param;

public class ConcreteOfferFragment extends Fragment {
    private static final String OFFER_ARG = "OFFER_ARG";

    @BindView(R.id.offer_image)
    SimpleDraweeView offerImage;

    @BindView(R.id.title_text_view)
    TextView titleTextView;

    @BindView(R.id.description_text_view)
    TextView descTextView;

    @BindView(R.id.price_text_view)
    TextView priceTextView;

    @BindView(R.id.weight_text_view)
    TextView weightTextView;

    private Offer offer;


    public ConcreteOfferFragment() {
    }

    public static ConcreteOfferFragment newInstance(Offer offer) {
        ConcreteOfferFragment fragment = new ConcreteOfferFragment();
        Bundle args = new Bundle();
        args.putParcelable(OFFER_ARG, offer);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            offer = getArguments().getParcelable(OFFER_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_concrete_offer, container, false);
        ButterKnife.bind(this, v);
        drawCard();
        return v;
    }

    private void drawCard() {
        if (offer.getPictureUrl() != null) {
            offerImage.setImageURI(Uri.parse(offer.getPictureUrl()));
        } else {
            offerImage.setImageResource(R.drawable.ic_no_image);
        }
        titleTextView.setText(offer.getName());
        descTextView.setText(offer.getDescription());
        priceTextView.setText((String.format("%s руб.", offer.getPrice())));
        Param weightParam = offer.getParamByName(Const.WEIGHT_PARAM);
        if (weightParam != null) {
            weightTextView.setText(weightParam.getText());
        } else {
            weightTextView.setVisibility(View.GONE);
        }

    }

}
