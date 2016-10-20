package testapp.vka.testmagapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kirill on 17.10.2016.
 */
@Root(name = "yml_catalog", strict = false)
public class ResponseModel implements Parcelable {
    @ElementList(name = "categories")
    @Path(value = "shop")
    ArrayList<Category> categories;

    @ElementList(name = "offers")
    @Path(value = "shop")
    ArrayList<Offer> offers;

    public ArrayList<Category> getCategories() {
        return categories;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.categories);
        dest.writeTypedList(this.offers);
    }

    public ResponseModel() {
    }

    protected ResponseModel(Parcel in) {
        this.categories = in.createTypedArrayList(Category.CREATOR);
        this.offers = in.createTypedArrayList(Offer.CREATOR);
    }

    public static final Parcelable.Creator<ResponseModel> CREATOR = new Parcelable.Creator<ResponseModel>() {
        @Override
        public ResponseModel createFromParcel(Parcel source) {
            return new ResponseModel(source);
        }

        @Override
        public ResponseModel[] newArray(int size) {
            return new ResponseModel[size];
        }
    };

    public ArrayList<Offer> getOfferByCategory(String id) {
        ArrayList<Offer> filtered = new ArrayList<>();
        for (Offer offer : offers) {
            if (offer.getCategoryId().equals(id)) {
                filtered.add(offer);
            }
        }
        return filtered;
    }
}
