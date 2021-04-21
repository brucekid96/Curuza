package com.curuza.data.fournisseur;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "fournisseur")
public class Fournisseur implements Parcelable {
    public static final String FOURNISSEUR_EXTRA = "fournisseur_code";
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
    private String date;
    @NonNull
    private String telephone;

    public Fournisseur( String id,  String personName, String description,   String date,  String telephone) {
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
        return com.curuza.data.fournisseur.Fournisseur.class.getSimpleName()
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

    public Fournisseur(Parcel in) {
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

    public static final Parcelable.Creator<com.curuza.data.fournisseur.Fournisseur> CREATOR =
            new Parcelable.Creator<com.curuza.data.fournisseur.Fournisseur>() {
                @Override
                public com.curuza.data.fournisseur.Fournisseur createFromParcel(Parcel source) {
                    return new com.curuza.data.fournisseur.Fournisseur(source);
                }

                @Override
                public com.curuza.data.fournisseur.Fournisseur[] newArray(int size) {
                    return new com.curuza.data.fournisseur.Fournisseur[size];
                }
            };

}

