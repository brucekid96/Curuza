package com.curuza.data.movements;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.curuza.data.view.ProductMovement;
import com.curuza.data.view.Rapport;

import java.util.List;

@Dao
public interface MovementDao {

    @Insert
    void insert(Movement movement);

    @Delete
    int delete(Movement movement);
    @Update
    int update(Movement movement);
    @Query("select p.id,p.resourceId,p.name,p.description," +
            "p.quantity,p.p_vente,p.p_achat " +
            ", m.movement_id,m.product_id,m.movement_quantity," +
            "m.movement_date,m.movement_status,m.movement_p_vente,m.movement_p_achat " +
            "from products_table p " +
            "inner join movement m " +
            "on p.id = m.product_id " +
            "order by m.movement_date desc    ")
    LiveData <List<ProductMovement>> getProductMovements();

    @Query("SELECT * from  movement where movement_id = :MovementId ")
    LiveData<Movement> getMovement(String MovementId);
    @Query("select * from rapport ")
    LiveData<List<Rapport>>getRapportList();

    @Query("SELECT * from  product_movement where movement_status= 'Enter'")
    LiveData<List<ProductMovement>>getEnterProductMovements();

    @Query("SELECT * from  product_movement where movement_status= 'Exit'")
    LiveData<List<ProductMovement>>getExitProductMovements();

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
