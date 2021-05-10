package com.curuza.data.depense;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.curuza.data.credit.Credit;

@Entity(tableName = "depense")
public class Depense implements Parcelable {
    public static final String DEPENSE_EXTRA = "depense_code";
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "amount")
    private int amount;
    @NonNull
    private String date;

    public Depense(String id, String description, int amount,String date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



    public String toString() {
        return Credit.class.getSimpleName()
                + "["
                + "mId="
                + id.toString()
                + ","
                +"mDescription="
                + description
                + ","
                +"mAmount="
                + amount

                + "]";
    }

    public Depense(Parcel in) {
        this.id = in.readString();
        this.description = in.readString();
        this.amount = in.readInt();
        this.date = in.readString();


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.id);
        dest.writeString(this.description);
        dest.writeInt(this.amount);
        dest.writeString(this.date);


    }

    public static final Parcelable.Creator<Depense> CREATOR =
            new Parcelable.Creator<Depense>() {
                @Override
                public Depense createFromParcel(Parcel source) {
                    return new Depense(source);
                }

                @Override
                public Depense[] newArray(int size) {
                    return new Depense[size];
                }
            };

}
