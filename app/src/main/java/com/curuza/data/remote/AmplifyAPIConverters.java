package com.curuza.data.remote;

import android.net.Uri;

import com.amplifyframework.datastore.generated.model.MovementStatus;
import com.curuza.data.client.Client;
import com.curuza.data.credit.Credit;
import com.curuza.data.depense.Depense;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.movements.Movement;
import com.curuza.data.stock.Product;

public class AmplifyAPIConverters {
  /*public static com.curuza.data.stock.Product toProduct(Product product) {
    return new com.curuza.data.stock.Product(
        product.getId(),

    )
  } */

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
  }

  public static com.amplifyframework.datastore.generated.model.Product toProduct(Product product) {
    return com.amplifyframework.datastore.generated.model.Product.builder()
        .name(product.getName())
        .quantity(product.getQuantity())
        .photoId(product.getId())
        .description(product.getDescription())
        .pVente(product.getPVente())
        .pAchat(product.getPAchat())
        .pVente(product.getPVente())
        .addedAt(product.getDate())
        .id(product.getId())

        .build();
  }

  public static Fournisseur toFournisseur(com.amplifyframework.datastore.generated.model.Fournisseur fournisseur) {
    return new Fournisseur(
        fournisseur.getId(),
        fournisseur.getName(),
        fournisseur.getDescription(),
        fournisseur.getAddedAt(),
        fournisseur.getTelephone());
  }

  public static com.amplifyframework.datastore.generated.model.Fournisseur toFournisseur(Fournisseur fournisseur) {
    return com.amplifyframework.datastore.generated.model.Fournisseur.builder()
        .name(fournisseur.getPersonName())
        .id(fournisseur.getId())
        .description(fournisseur.getDescription())
        .addedAt(fournisseur.getDate())
        .telephone(fournisseur.getTelephone())
        .build();
  }

  public static Client toClient(com.amplifyframework.datastore.generated.model.Client client) {
    return new Client(
        client.getId(),
        client.getName(),
        client.getDescription(),
        client.getAddedAt(),
        client.getTelephone());
  }

  public static com.amplifyframework.datastore.generated.model.Client toClient(Client client) {
    return com.amplifyframework.datastore.generated.model.Client.builder()
        .name(client.getPersonName())
        .id(client.getId())
        .description(client.getDescription())
        .addedAt(client.getDate())
        .telephone(client.getTelephone())
        .build();
  }

  public static Credit toCredit(com.amplifyframework.datastore.generated.model.Credit credit) {
    return new Credit(
        credit.getId(),
        credit.getName(),
        credit.getDescription(),
        credit.getAmount(),
        credit.getAddedAt(),
        credit.getTelephone());
  }

  public static com.amplifyframework.datastore.generated.model.Credit toCredit(Credit credit) {
    return com.amplifyframework.datastore.generated.model.Credit.builder()
        .name(credit.getPersonName())
        .amount(credit.getAmount())
        .id(credit.getId())
        .addedAt(credit.getDate())
        .description(credit.getDescription())
        .telephone(credit.getTelephone())
        .build();
  }

  public static Depense toDepense(com.amplifyframework.datastore.generated.model.Depense depense) {
    return new Depense(
        depense.getId(),
        depense.getDescription(),
        depense.getAmount(),
        depense.getAddedAt());
  }

  public static com.amplifyframework.datastore.generated.model.Depense toDepense(Depense depense) {
    return com.amplifyframework.datastore.generated.model.Depense.builder()
        .amount(depense.getAmount())
        .id(depense.getId())
        .addedAt(depense.getDate())
        .description(depense.getDescription())
        .build();
  }

  public static Movement toMovement(com.amplifyframework.datastore.generated.model.Movement movement) {
    return new Movement(
        movement.getId(),
        movement.getProductId(),
        movement.getQuantity(),
        movement.getPAchat(),
        movement.getPVente(),
        movement.getAddedAt(),
        com.curuza.domain.MovementStatus.valueOf(movement.getStatus().name())

        );
  }

  public static com.amplifyframework.datastore.generated.model.Movement toMovement(Movement movement) {
    return com.amplifyframework.datastore.generated.model.Movement.builder()
        .productId(movement.getProduct_id())
        .quantity(movement.getQuantity())
        .id(movement.getId())
        .pAchat(movement.getPAchat())
        .pVente(movement.getPVente())
        .addedAt(movement.getDate())
        .status(MovementStatus.valueOf(movement.getStatus().name()))
        .build();
  }
}
