package com.curuza.data.credit;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "credit")
public class Credit implements Parcelable  {
    public static final String CREDIT_EXTRA = "credit_code";
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @NonNull
    @ColumnInfo(name = "person_name")
    private String personName;
    @NonNull
    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    @ColumnInfo(name = "amount")
    private int amount;
    @NonNull
    private String date;
    @NonNull
    private String telephone;

    public Credit( String id,  String personName, String description,  int amount,  String date,  String telephone) {
        this.id = id;
        this.personName = personName;
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.telephone = telephone;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId( String id) {
        this.id = id;
    }

    @NonNull
    public String getPersonName() {
        return personName;
    }

    public void setPersonName( String personName) {
        this.personName = personName;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription( String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount( int amount) {
        this.amount = amount;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate( String date) {
        this.date = date;
    }

    @NonNull
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone( String telephone) {
        this.telephone = telephone;
    }

    public String toString() {
        return Credit.class.getSimpleName()
                + "["
                + "mId="
                + id.toString()
                + ","
                +"mName="
                + personName
                + ","
                +"mTEL="
                + telephone
                + ","
                +"mAmount="
                + amount

                + "]";
    }

    // PARCELABLE IMPLEMENTATION METHODS

    public Credit(Parcel in) {
        this.id = in.readString();
        this.personName = in.readString();
        this.description = in.readString();
        this.amount = in.readInt();
        this.date = in.readString();
        this.telephone = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.id);
        dest.writeString(this.personName);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.telephone);
        dest.writeInt(this.amount);

    }

    public static final Parcelable.Creator<Credit> CREATOR =
            new Parcelable.Creator<Credit>() {
                @Override
                public Credit createFromParcel(Parcel source) {
                    return new Credit(source);
                }

                @Override
                public Credit[] newArray(int size) {
                    return new Credit[size];
                }
            };

}
