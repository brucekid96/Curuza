package com.curuza.data.stock;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "products_table")
public class Product implements Parcelable {

    public static final String PRODUCT_EXTRA = "product_code";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    private String mId;
    @ColumnInfo(name = "resourceId")
    private Uri mProductImageUri;
    @NonNull
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "description")
    private String mDescription;
    @NonNull
    @ColumnInfo(name = "quantity")
    private int mQuantity;
    @NonNull
    @ColumnInfo(name = "p_achat")
    private int mPAchat;
    @NonNull
    @ColumnInfo(name = "p_vente")
    private int mPVente;

    @ColumnInfo(name = "p_date")
    private String mDate;




    public Product(String id, Uri mProductImageUri, String name, String mDescription, int mQuantity, int mPAchat, int mPVente,String mDate) {

        mId = id;
        this.mProductImageUri = mProductImageUri;
        this.name = name;
        this.mDescription = mDescription;
        this.mQuantity = mQuantity;
        this.mPAchat = mPAchat;
        this.mPVente = mPVente;
        this.mDate = mDate;

    }


    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public Uri getProductImageUri() {
        return mProductImageUri;
    }

    public void setProductImageUri(Uri mProductImageUri) {
        this.mProductImageUri = mProductImageUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public int getPAchat() {
        return mPAchat;
    }

    public void setPAchat(int mPAchat) {
        this.mPAchat = mPAchat;
    }

    public int getPVente() {
        return mPVente;
    }

    public void setPVente(int mPVente) {
        this.mPVente = mPVente;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }




    public String toString() {
        return Product.class.getSimpleName()
                + "["
                + "mId="
                + mId.toString()
                + ","
                +"mEventImageUri="
                + (mProductImageUri == null ? "null" : mProductImageUri.toString())
                + ","
                +"mTitle="
                + name
                + ","
                +"mDescription="
                + mDescription
                + ","
                +"mQuantity="
                + mQuantity

                + "]";
    }

    // PARCELABLE IMPLEMENTATION METHODS

    public Product(Parcel in) {
        this.mId = in.readString();
        this.mProductImageUri = in.readParcelable(Uri.class.getClassLoader());
        this.name = in.readString();
        this.mDescription = in.readString();
        this.mQuantity = in.readInt();
        this.mPAchat = in.readInt();
        this.mPVente = in.readInt();
        this.mDate =in.readString();






    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.mId);
        dest.writeParcelable(this.mProductImageUri, flags);
        dest.writeString(this.name);
        dest.writeString(this.mDescription);
        dest.writeInt(this.mQuantity);
        dest.writeInt(this.mPAchat);
        dest.writeInt(this.mPVente);
        dest.writeString(this.mDate);




    }

    public static final Parcelable.Creator<Product> CREATOR =
            new Parcelable.Creator<Product>() {
                @Override
                public Product createFromParcel(Parcel source) {
                    return new Product(source);
                }

                @Override
                public Product[] newArray(int size) {
                    return new Product[size];
                }
            };


}
