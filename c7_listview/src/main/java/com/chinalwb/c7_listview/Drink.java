package com.chinalwb.c7_listview;

import android.os.Parcel;
import android.os.Parcelable;

public class Drink implements Parcelable {

    private String name;
    private String description;
    private int imageResourceId;
    private int favorite;

    //drinks is an array of Drinks
    public static final Drink[] drinks = {
            new Drink("Latte", "A couple of espresso shots with steamed milk", R.drawable.latte),
            new Drink("Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino),
            new Drink("Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter)
    };

    public Drink(Parcel in) {
        this.name = in.readString();
        this.description = in.readString();
        this.imageResourceId = in.readInt();
        this.favorite = in.readInt();
    }

    public Drink(String name, String description, int imageResourceId) {
        this(name, description, imageResourceId, 0);
    }

    public Drink(String name, String description, int imageResourceId, int favorite) {
        this.name = name;
        this.description = description;
        this.imageResourceId = imageResourceId;
        this.favorite = favorite;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResourceId() {
        return imageResourceId;
    }

    public int getFavorite() {
        return this.favorite;
    }

    @Override
    public String toString() {
        return this.name;
    }


    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<Drink>() {
        @Override
        public Drink createFromParcel(Parcel source) {
            return new Drink(source);
        }

        @Override
        public Drink[] newArray(int size) {
            return new Drink[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.imageResourceId);
        dest.writeInt(this.favorite);
    }
}
