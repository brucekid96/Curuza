package com.curuza.data.remote;

import android.net.Uri;

import com.curuza.data.client.Client;
import com.curuza.data.credit.Credit;
import com.curuza.data.depense.Depense;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.movements.Movement;
import com.curuza.data.movements.MovementStatus;
import com.curuza.data.stock.Product;

public class AppSyncConverters {
  public static Product toProduct(com.amplifyframework.datastore.generated.model.Product product) {
    return new Product(
        product.getId(),
        Uri.EMPTY,
        product.getName(),
        product.getDescription(),
        product.getQuantity(),
        product.getPAchat(),
        product.getPVente(),
        product.getAddedAt());
  };

  public static Movement toMovement(com.amplifyframework.datastore.generated.model.Movement movement) {
    return new Movement(
        movement.getId(),
        movement.getProductId(),
        movement.getQuantity(),
        movement.getPAchat(),
        movement.getPVente(),
        movement.getAddedAt(),
        MovementStatus.valueOf(movement.getStatus().name()));
  };

  public static Credit toCredit(com.amplifyframework.datastore.generated.model.Credit credit) {
    return new Credit(
        credit.getId(),
        credit.getName(),
        credit.getDescription(),
        credit.getAmount(),
        credit.getAddedAt(),
        credit.getTelephone());
  }

  public static Depense toDepense(com.amplifyframework.datastore.generated.model.Depense depense) {
    return new Depense(
        depense.getId(),
        depense.getDescription(),
        depense.getAmount(),
        depense.getAddedAt());
  }

  public static Fournisseur toFournisseur(com.amplifyframework.datastore.generated.model.Fournisseur fournisseur) {
    return new Fournisseur(
        fournisseur.getId(),
        fournisseur.getName(),
        fournisseur.getDescription(),
        fournisseur.getAddedAt(),
        fournisseur.getTelephone());
  }

  public static Client toClient(com.amplifyframework.datastore.generated.model.Client client) {
    return new Client(
        client.getId(),
        client.getName(),
        client.getDescription(),
        client.getAddedAt(),
        client.getTelephone());
  }
}
