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
        value = "select date_only as date, sum(movement_p_vente) as total_vente " +
                "from (select *, substr(movement_date, 1, 10)  as date_only  from movement) group by date_only ")
public class Rapport implements Parcelable {
    public static final String RAPPORT_EXTRA = "rapport_code";

    private String date;
    @ColumnInfo(name = "total_vente")
    private int totalVente;


    public Rapport(String date, int totalVente) {
        this.date = date;
        this.totalVente = totalVente;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalVente() {
        return totalVente;
    }

    public void setTotalVente(int totalVente) {
        this.totalVente = totalVente;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.date);
        dest.writeInt(this.totalVente);
    }

    public void readFromParcel(Parcel source) {
        this.date = source.readString();
        this.totalVente = source.readInt();
    }


    protected Rapport(Parcel in) {
        this.date = in.readString();
        this.totalVente = in.readInt();
    }

    public String toString() {
        return
                 "Rapport["

                +"mDate="
                + date
                + ","
                +"mTotalAmount="
                + totalVente

                + "]";
    }

    public static final Creator<Rapport> CREATOR = new Creator<Rapport>() {
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
