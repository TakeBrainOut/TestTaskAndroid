package testapp.vka.testmagapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * Created by kirill on 17.10.2016.
 */

@Root(name = "offer", strict = false)
public class Offer implements Parcelable {
    @Attribute
    String id;

    @Element
    String name;

    @Element
    double price;

    @Element(required = false)
    String description;

    @Element(name = "picture", required = false)
    String pictureUrl;

    @Element
    String categoryId;

    @ElementList(entry = "param", inline = true, required = false)
    List<Param> paramList;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeDouble(this.price);
        dest.writeString(this.description);
        dest.writeString(this.pictureUrl);
        dest.writeString(this.categoryId);
        dest.writeTypedList(this.paramList);
    }

    public Offer() {
    }

    protected Offer(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.price = in.readDouble();
        this.description = in.readString();
        this.pictureUrl = in.readString();
        this.categoryId = in.readString();
        this.paramList = in.createTypedArrayList(Param.CREATOR);
    }

    public static final Parcelable.Creator<Offer> CREATOR = new Parcelable.Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public List<Param> getParamList() {
        return paramList;
    }

    public Param getParamByName(String name) {
        if (paramList != null) {
            for (Param p : paramList) {
                if (p.getName().equals(name)) {
                    return p;
                }
            }
        }
        return null;
    }
}
