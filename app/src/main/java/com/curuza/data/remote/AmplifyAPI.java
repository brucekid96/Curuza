package com.curuza.data.remote;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Fournisseur;
import com.amplifyframework.datastore.generated.model.Movement;
import com.amplifyframework.datastore.generated.model.Product;
import com.curuza.data.client.Client;
import com.curuza.data.credit.Credit;
import com.curuza.data.depense.Depense;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

public class AmplifyAPI {
  public static Single<List<com.curuza.data.stock.Product>> getProducts() {
    return Single.create(source ->
        Amplify.API.query(
          ModelQuery.list(Product.class),
          response -> {
            List<com.curuza.data.stock.Product> products = new ArrayList<>();
            for(Product product : response.getData()) {
              products.add(AmplifyAPIConverters.toProduct(product));
            }
            source.onSuccess(products);
        },
        error -> source.onError(error)));
  }

  public static Completable addProduct(com.curuza.data.stock.Product product) {
    return Completable.create(source ->
        Amplify.API.mutate(
            ModelMutation.create(AmplifyAPIConverters.toProduct(product)),
            response -> source.onComplete(),
            source::onError));

  }

  public static Single<List<com.curuza.data.fournisseur.Fournisseur>> getFournisseurs() {
    return Single.create(source ->
        Amplify.API.query(
            ModelQuery.list(Fournisseur.class),
            response -> {
              List<com.curuza.data.fournisseur.Fournisseur> fournisseurs = new ArrayList<>();
              for(Fournisseur fournisseur : response.getData()) {
                fournisseurs.add(AmplifyAPIConverters.toFournisseur(fournisseur));
              }
              source.onSuccess(fournisseurs);
            },
            error -> source.onError(error)));
  }

  public static Completable addFournisseur(com.curuza.data.fournisseur.Fournisseur fournisseur) {
    return Completable.create(source ->
        Amplify.API.mutate(
            ModelMutation.create(AmplifyAPIConverters.toFournisseur(fournisseur)),
            response -> source.onComplete(),
            source::onError));

  }

  public static Single<List<Client>> getClients() {
    return Single.create(source ->
        Amplify.API.query(
            ModelQuery.list(com.amplifyframework.datastore.generated.model.Client.class),
            response -> {
              List<Client> clients = new ArrayList<>();
              for(com.amplifyframework.datastore.generated.model.Client client : response.getData()) {
                clients.add(AmplifyAPIConverters.toClient(client));
              }
              source.onSuccess(clients);
            },
            error -> source.onError(error)));
  }

  public static Completable addClient(Client client) {
    return Completable.create(source ->
        Amplify.API.mutate(
            ModelMutation.create(AmplifyAPIConverters.toClient(client)),
            response -> source.onComplete(),
            source::onError));

  }

  public static Single<List<Credit>> getCredits() {
    return Single.create(source ->
        Amplify.API.query(
            ModelQuery.list(com.amplifyframework.datastore.generated.model.Credit.class),
            response -> {
              List<Credit> credits = new ArrayList<>();
              for(com.amplifyframework.datastore.generated.model.Credit credit : response.getData()) {
                credits.add(AmplifyAPIConverters.toCredit(credit));
              }
              source.onSuccess(credits);
            },
            error -> source.onError(error)));
  }

  public static Completable addCredit(Credit credit) {
    return Completable.create(source ->
        Amplify.API.mutate(
            ModelMutation.create(AmplifyAPIConverters.toCredit(credit)),
            response -> source.onComplete(),
            source::onError));

  }

  public static Single<List<Depense>> getDepenses() {
    return Single.create(source ->
        Amplify.API.query(
            ModelQuery.list(com.amplifyframework.datastore.generated.model.Depense.class),
            response -> {
              List<Depense> depenses = new ArrayList<>();
              for(com.amplifyframework.datastore.generated.model.Depense depense : response.getData()) {
                depenses.add(AmplifyAPIConverters.toDepense(depense));
              }
              source.onSuccess(depenses);
            },
            error -> source.onError(error)));
  }

  public static Completable addDepense(Depense depense) {
    return Completable.create(source ->
        Amplify.API.mutate(
            ModelMutation.create(AmplifyAPIConverters.toDepense(depense)),
            response -> source.onComplete(),
            source::onError));

  }

  public static Single<List<com.curuza.data.movements.Movement>> getMovements() {
    return Single.create(source ->
        Amplify.API.query(
            ModelQuery.list(Movement.class),
            response -> {
              List<com.curuza.data.movements.Movement> movements = new ArrayList<>();
              for(Movement movement : response.getData()) {
                movements.add(AmplifyAPIConverters.toMovement(movement));
              }
              source.onSuccess(movements);
            },
            error -> source.onError(error)));
  }

  public static Completable addMovement(com.curuza.data.movements.Movement movement) {
    return Completable.create(source ->
        Amplify.API.mutate(
            ModelMutation.create(AmplifyAPIConverters.toMovement(movement)),
            response -> source.onComplete(),
            source::onError));

  }
}
 