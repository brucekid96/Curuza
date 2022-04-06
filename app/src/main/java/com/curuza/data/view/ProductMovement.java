package com.curuza.data.view;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.DatabaseView;
import androidx.room.Embedded;
import androidx.room.Ignore;

import com.curuza.data.stock.Product;
import com.curuza.data.movements.Movement;


@DatabaseView(
        viewName = "product_movement",
        value = "select p.id,p.resourceId,p.name,p.description," +
        "p.quantity,p.p_vente,p.p_achat,p_date " +
        ", m.movement_id,m.product_id,m.movement_quantity," +
        "m.movement_date,m.movement_status,m.movement_p_vente,m.movement_p_achat " +
        "from products_table p " +
        "inner join movement m " +
        "on p.id = m.product_id  "+
                "order by m.movement_date desc    "
)
public class ProductMovement implements Parcelable  {
    public static final String ProductMovement_EXTRA = "product_movement_code";

    @Embedded
    private Product mProduct;

    @Embedded
    private Movement mMovement;


    public ProductMovement(Product product, Movement movement) {
        this.mProduct = product;
        this.mMovement = movement;
    }

    public Product getProduct() {
        return mProduct;
    }


    public Movement getMovement() {
        return mMovement;
    }

    @Override
    public String toString() {
        return "ProductMovement{" +
                "mProduct=" + mProduct +
                ", mMovement=" + mMovement +
                '}';
    }

    // Parcelable interface methods
    @Ignore
    public ProductMovement(Parcel in) {
        mProduct = in.readParcelable(Product.class.getClassLoader());
        mMovement = in.readParcelable(Movement.class.getClassLoader());

    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(mProduct, 0);
        dest.writeParcelable(mMovement, 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }



    public static final Parcelable.Creator<ProductMovement> CREATOR =
            new Parcelable.Creator<ProductMovement>() {
                @Override
                public ProductMovement createFromParcel(Parcel source) {
                    return new ProductMovement(source);
                }

                @Override
                public ProductMovement[] newArray(int size) {
                    return new ProductMovement[size];
                }
            };


}
