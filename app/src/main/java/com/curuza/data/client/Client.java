package com.curuza.data.client;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "client")
public class Client implements Parcelable {
    public static final String CLIENT_EXTRA = "client_code";
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String id;
    @NonNull
    @ColumnInfo(name = "person_name")
    private String personName;

    @ColumnInfo(name = "description")
    private String description;
    @NonNull
    private String date;

    private String telephone;

    public Client( String id,  String personName, String description,  String date,  String telephone) {
        this.id = id;
        this.personName = personName;
        this.description = description;
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
        return com.curuza.data.client.Client.class.getSimpleName()
                + "["
                + "mId="
                + id.toString()
                + ","
                +"mName="
                + personName
                + ","
                +"mTEL="
                + telephone

                + "]";
    }

    // PARCELABLE IMPLEMENTATION METHODS

    public Client(Parcel in) {
        this.id = in.readString();
        this.personName = in.readString();
        this.description = in.readString();
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


    }

    public static final Parcelable.Creator<com.curuza.data.client.Client> CREATOR =
            new Parcelable.Creator<com.curuza.data.client.Client>() {
                @Override
                public com.curuza.data.client.Client createFromParcel(Parcel source) {
                    return new com.curuza.data.client.Client(source);
                }

                @Override
                public com.curuza.data.client.Client[] newArray(int size) {
                    return new com.curuza.data.client.Client[size];
                }
            };

}

