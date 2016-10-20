package testapp.vka.testmagapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

/**
 * Created by kirill on 17.10.2016.
 */

@Root(name = "param")
public class Param implements Parcelable {
    @Attribute
    String name;

    @Text
    String text;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.text);
    }

    public Param() {
    }

    protected Param(Parcel in) {
        this.name = in.readString();
        this.text = in.readString();
    }

    public static final Parcelable.Creator<Param> CREATOR = new Parcelable.Creator<Param>() {
        @Override
        public Param createFromParcel(Parcel source) {
            return new Param(source);
        }

        @Override
        public Param[] newArray(int size) {
            return new Param[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }
}
