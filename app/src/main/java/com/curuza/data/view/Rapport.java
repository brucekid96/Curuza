package com.curuza.data.view;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.DatabaseView;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.curuza.data.credit.Credit;
import com.curuza.data.movements.Movement;
import com.curuza.data.stock.Product;

import java.util.UUID;

@DatabaseView(
        viewName = "rapport",
        value = "select SUM(movement_p_vente), CAST(movement_date AS DATE) as 'p vente amount per day' from MOVEMENT "
)
public class Rapport implements Parcelable {
    public static final String RAPPORT_EXTRA = "rapport_code";
    @Embedded
    private Movement mMovement;



    public Rapport(Movement movement) {
        this.mMovement = movement;
    }

    public Movement getMovement() {
        return mMovement;
    }



    public String toString() {
        return "Rapport{" +
                ", mMovement=" + mMovement +
                '}';
    }

    // PARCELABLE IMPLEMENTATION METHODS

    public Rapport(Parcel in) {
        mMovement = in.readParcelable(Movement.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeParcelable(mMovement, 0);

    }


    public static final Parcelable.Creator<Rapport> CREATOR =
            new Parcelable.Creator<Rapport>() {
                @Override
                public Rapport createFromParcel(Parcel source) {
                    return new Rapport(source);
                }

                @Override
                public Rapport[] newArray(int size) {
                    return new Rapport[size];
                }
            };
}
