package com.curuza.data.movements;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.curuza.data.view.ProductMovement;
import com.curuza.data.view.Rapport;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;

@Dao
public interface MovementDao {

    @Insert
    Completable insert(Movement movement);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable bulkInsert(List<Movement> movements);

    @Delete
    Completable delete(Movement movement);

    @Query("delete from movement where movement_id = :MovementId")
    Completable delete(String MovementId);
    @Update
    Completable update(Movement movement);
    @Query("select * from product_movement order by movement_date desc")
    Observable<List<ProductMovement>> getProductMovements();

    @Query("SELECT * from  movement where movement_id = :MovementId ")
    Maybe<Movement> getMovement(String MovementId);
    @Query("select * from rapport ")
    Observable<List<Rapport>>getRapportList();

    @Query("SELECT * from  product_movement where movement_status= 'Enter'")
    Observable<List<ProductMovement>>getEnterProductMovements();

    @Query("SELECT * from  product_movement where movement_status= 'Enter' and substr(movement_date,1,10) = :date")
    Observable<List<ProductMovement>>getEnterProductMovementsByDate(String date);

    @Query("SELECT * from  product_movement where movement_status= 'Exit'")
    Observable<List<ProductMovement>>getExitProductMovements();

    @Query("SELECT *, substr(movement_date, 1, 10) as movement_date_only from  product_movement where movement_status= 'Exit' and movement_date_only = :date")
    Observable<List<ProductMovement>>getExitProductMovementsByDate(String date);

    @Query("select p.id,p.resourceId,p.name,p.description," +
            "p.quantity,p.p_vente,p.p_achat " +
            ", m.movement_id,m.product_id,m.movement_quantity," +
            "m.movement_date,m.movement_status,m.movement_p_vente,m.movement_p_achat " +
            "from products_table p " +
            "inner join movement m " +
            "on p.id = m.product_id " +
            "where p.id = :productId")
    public ProductMovement getProductMovement(String productId);



}
