package com.curuza.data.movements;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.curuza.data.stock.Product;
import com.curuza.domain.RequestStatus;


import java.util.UUID;
@Entity(tableName = "movement")
public class Movement implements Parcelable {

    public static final String MOVEMENT_EXTRA = "movement_code";

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movement_id")
    private String mId;
    @NonNull
    @ColumnInfo(name = "product_id")
    private String product_id;
    @NonNull
    @ColumnInfo(name = "movement_quantity")
    private int mQuantity;
    @NonNull
    @ColumnInfo(name = "movement_p_achat")
    private int mPAchat;
    @NonNull
    @ColumnInfo(name = "movement_p_vente")
    private int mPVente;
    @NonNull
    @ColumnInfo(name = "movement_date")
    private String mDate;
    @NonNull
    @ColumnInfo(name = "movement_status")
    private RequestStatus mStatus;





    public Movement(String id,String product_id, int mQuantity, int mPAchat, int mPVente, String mDate, RequestStatus mStatus) {

        this.mId = id ;
        this.product_id  = product_id;
        this.mQuantity = mQuantity;
        this.mPAchat = mPAchat;
        this.mPVente = mPVente;
        this.mDate = mDate;
        this.mStatus = mStatus;

    }


    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }
    @NonNull
    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(@NonNull String product_id) {
        this.product_id = product_id;
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


    public RequestStatus getStatus() {
        return mStatus;
    }

    public void setStatus(RequestStatus mStatus) {
        this.mStatus = mStatus;
    }



    public String toString() {
        return Movement.class.getSimpleName()
                + "["
                + "mId="
                + mId.toString()
                +"mEventImageUri="
                + ","
                +"mTitle="
                + ","
                +"mDescription="
                + ","
                +"mQuantity="
                + mQuantity

                + "]";
    }

    // PARCELABLE IMPLEMENTATION METHODS

    public Movement(Parcel in) {
        this.mId = in.readString();
        this.mQuantity = in.readInt();
        this.mPAchat = in.readInt();
        this.mPVente = in.readInt();
        this.mDate = in.readString();




    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(this.mId);
        dest.writeInt(this.mQuantity);
        dest.writeInt(this.mPAchat);
        dest.writeInt(this.mPVente);
        dest.writeString(this.mDate);



    }

    public static final Parcelable.Creator<Movement> CREATOR =
            new Parcelable.Creator<Movement>() {
                @Override
                public Movement createFromParcel(Parcel source) {
                    return new Movement(source);
                }

                @Override
                public Movement[] newArray(int size) {
                    return new Movement[size];
                }
            };


}
