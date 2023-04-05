package com.curuza.data.remote;

import android.content.Context;
import android.util.Log;

import com.amazonaws.amplify.generated.graphql.CreateClientMutation;
import com.amazonaws.amplify.generated.graphql.CreateCreditMutation;
import com.amazonaws.amplify.generated.graphql.CreateDepenseMutation;
import com.amazonaws.amplify.generated.graphql.CreateFournisseurMutation;
import com.amazonaws.amplify.generated.graphql.CreateMovementMutation;
import com.amazonaws.amplify.generated.graphql.CreateProductMutation;
import com.amazonaws.amplify.generated.graphql.ListClientsQuery;
import com.amazonaws.amplify.generated.graphql.ListCreditsQuery;
import com.amazonaws.amplify.generated.graphql.ListDepensesQuery;
import com.amazonaws.amplify.generated.graphql.ListFournisseursQuery;
import com.amazonaws.amplify.generated.graphql.ListMovementsQuery;
import com.amazonaws.amplify.generated.graphql.ListProductsQuery;
import com.amazonaws.amplify.generated.graphql.UpdateClientMutation;
import com.amazonaws.amplify.generated.graphql.UpdateCreditMutation;
import com.amazonaws.amplify.generated.graphql.UpdateDepenseMutation;
import com.amazonaws.amplify.generated.graphql.UpdateFournisseurMutation;
import com.amazonaws.amplify.generated.graphql.UpdateProductMutation;
import com.amazonaws.mobileconnectors.appsync.AWSAppSyncClient;
import com.apollographql.apollo.GraphQLCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.curuza.data.client.Client;
import com.curuza.data.credit.Credit;
import com.curuza.data.depense.Depense;
import com.curuza.data.fournisseur.Fournisseur;
import com.curuza.data.movements.Movement;
import com.curuza.data.movements.MovementStatus;
import com.curuza.data.stock.Product;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import io.reactivex.Completable;
import io.reactivex.Single;
import type.CreateClientInput;
import type.CreateCreditInput;
import type.CreateDepenseInput;
import type.CreateFournisseurInput;
import type.CreateMovementInput;
import type.CreateProductInput;
import type.UpdateClientInput;
import type.UpdateCreditInput;
import type.UpdateDepenseInput;
import type.UpdateFournisseurInput;
import type.UpdateProductInput;

public class AppSyncApi {
  private static final String DBG_TAG = AppSyncApi.class.getSimpleName();
  private static final Object LOCK = new Object();
  private static AppSyncApi sInstance;

  private static AWSAppSyncClient mAppSyncClient;

  public AppSyncApi(Context context) {
    mAppSyncClient = AppSyncProvider.getAppSyncClient(context);
  }

  public static AppSyncApi getInstance(Context context) {
    if (sInstance == null) {
      synchronized (LOCK) {
        sInstance = new AppSyncApi(context);
      }
    }

    return sInstance;
  }

  public Single<List<Product>> getProducts() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListProductsQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListProductsQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListProductsQuery.Data> response) {
                if (response.data() != null) {
                  List<Product> products = new ArrayList();
                  for(ListProductsQuery.Item item : response.data().listProducts().items()) {
                    products.add(new Product(
                        item.id(),
                        null,
                        item.name(),
                        item.description(),
                        item.quantity(),
                        item.pAchat(),
                        item.pVente(),
                        item.addedAt()

                    ));
                  }


                  source.onSuccess(products);
                } else {
                  Log.d(DBG_TAG, "AppSync getProducts response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getProducts query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable addProduct(Product product) {
    return Completable.create(source -> {
      CreateProductMutation mutation = CreateProductMutation.builder().input(
          CreateProductInput.builder()
              .id(product.getId())
              .name(product.getName())
              .description(product.getDescription())
              .quantity(product.getQuantity())
              .pAchat(product.getPAchat())
              .pVente(product.getPVente())
              .addedAt(product.getDate())
              .build())
          .build();

        mAppSyncClient.mutate(mutation)
        .enqueue(new GraphQLCall.Callback<CreateProductMutation.Data>(){
          @Override
          public void onResponse(@Nonnull Response<CreateProductMutation.Data> response) {
            if (response.data() != null) {
              Log.d(DBG_TAG, "AppSync createProduct mutation was successful");
              CreateProductMutation.CreateProduct prod = response.data().createProduct();
              Log.d(DBG_TAG, "Product created: " + prod);
              source.onComplete();
            } else {
              Log.d(DBG_TAG, "AppSync createProduct response was null");
              source.onError(new ApolloException("AppSync response was null"));
            }
          }

          @Override
          public void onFailure(@Nonnull ApolloException e) {
            Log.d(DBG_TAG, "AppSync createProduct mutation failed: " + e.toString() + ", cause = " + e.getCause());
            source.onError(e);
          }
        });
  });
}


  public static Completable updateProduct(Product product) {
    return Completable.create(source -> {
      UpdateProductMutation mutation = UpdateProductMutation.builder().input(
          UpdateProductInput.builder()
              .id(product.getId())
              .name(product.getName())
              .description(product.getDescription())
              .quantity(product.getQuantity())
              .pAchat(product.getPAchat())
              .pVente(product.getPVente())
              .addedAt(product.getDate())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<UpdateProductMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<UpdateProductMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync sellProduct mutation was successful");
                UpdateProductMutation.UpdateProduct prod = response.data().updateProduct();
                Log.d(DBG_TAG, "Product selled: " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync sellProduct response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync sellProduct mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }


  public Single<List<Credit>> getCredits() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListCreditsQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListCreditsQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListCreditsQuery.Data> response) {
                if (response.data() != null) {
                  List<Credit> credits = new ArrayList();
                  for(ListCreditsQuery.Item item : response.data().listCredits().items()) {
                    credits.add(new Credit(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.amount(),
                        item.telephone(),
                        item.addedAt()

                    ));
                  }


                  source.onSuccess(credits);
                } else {
                  Log.d(DBG_TAG, "AppSync getCredits response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getCredits query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable addCredit(Credit credit) {
    return Completable.create(source -> {
      CreateCreditMutation mutation = CreateCreditMutation.builder().input(
          CreateCreditInput.builder()
              .id(credit.getId())
              .name(credit.getPersonName())
              .description(credit.getDescription())
              .amount(credit.getAmount())
              .addedAt(credit.getDate())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<CreateCreditMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<CreateCreditMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync createCredit mutation was successful");
                CreateCreditMutation.CreateCredit prod = response.data().createCredit();
                Log.d(DBG_TAG, "Credit created: " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync createCredit response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync createCredit mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }


  public Single<List<Credit>> getUpdateCredits() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListCreditsQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListCreditsQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListCreditsQuery.Data> response) {
                if (response.data() != null) {
                  List<Credit> credits = new ArrayList();
                  for(ListCreditsQuery.Item item : response.data().listCredits().items()) {
                    credits.add(new Credit(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.amount(),
                        item.addedAt(),
                        item.telephone()

                    ));
                  }


                  source.onSuccess(credits);
                } else {
                  Log.d(DBG_TAG, "AppSync getProducts response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getProducts query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable updateCredit(Credit credit) {
    return Completable.create(source -> {
      UpdateCreditMutation mutation = UpdateCreditMutation.builder().input(
          UpdateCreditInput.builder()
              .id(credit.getId())
              .name(credit.getPersonName())
              .description(credit.getDescription())
              .amount(credit.getAmount())
              .telephone(credit.getTelephone())
              .addedAt(credit.getDate())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<UpdateCreditMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<UpdateCreditMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync sellCredit mutation was successful");
                UpdateCreditMutation.UpdateCredit prod = response.data().updateCredit();
                Log.d(DBG_TAG, "Credit selled: " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync sellCredit response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync sellCredit mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }

  public Single<List<Movement>> getMovements() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListMovementsQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListMovementsQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListMovementsQuery.Data> response) {
                if (response.data() != null) {
                  List<Movement> movements = new ArrayList();
                  for(ListMovementsQuery.Item item : response.data().listMovements().items()) {
                    movements.add(new Movement(
                        item.id(),
                        item.productId(),
                        item.quantity(),
                        item.pAchat(),
                        item.pVente(),
                        item.addedAt(),
                        MovementStatus.valueOf(item.status().name())
                    ));
                  }


                  source.onSuccess(movements);
                } else {
                  Log.d(DBG_TAG, "AppSync getMovements response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getMovements query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable addMovement(Movement movement) {
    return Completable.create(source -> {
      CreateMovementMutation mutation = CreateMovementMutation.builder().input(
          CreateMovementInput.builder()
              .id(movement.getId())
              .productId(movement.getProduct_id())
              .quantity(movement.getQuantity())
              .pAchat(movement.getPAchat())
              .pVente(movement.getPVente())
              .addedAt(movement.getDate())
              .status(type.MovementStatus.valueOf(movement.getStatus().name()))
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<CreateMovementMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<CreateMovementMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync createMovement mutation was successful");
                CreateMovementMutation.CreateMovement prod = response.data().createMovement();
                Log.d(DBG_TAG, "Movement created: " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync createMovement response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync createMovement mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }


  public Single<List<Depense>> getDepenses() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListDepensesQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListDepensesQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListDepensesQuery.Data> response) {
                if (response.data() != null) {
                  List<Depense> depenses = new ArrayList();
                  for(ListDepensesQuery.Item item : response.data().listDepenses().items()) {
                    depenses.add(new Depense(
                        item.id(),
                        item.description(),
                        item.amount(),
                        item.addedAt())
                    );
                  }


                  source.onSuccess(depenses);
                } else {
                  Log.d(DBG_TAG, "AppSync getDepenses response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getMovements query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable addDepense(Depense depense) {
    return Completable.create(source -> {
      CreateDepenseMutation mutation = CreateDepenseMutation.builder().input(
          CreateDepenseInput.builder()
              .id(depense.getId())
              .description(depense.getDescription())
              .amount(depense.getAmount())
              .addedAt(depense.getDate())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<CreateDepenseMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<CreateDepenseMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync createDepense mutation was successful");
                CreateDepenseMutation.CreateDepense prod = response.data().createDepense();
                Log.d(DBG_TAG, "Depense created: " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync createDepense response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync createDepense mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }

  public Single<List<Depense>> getUpdateDepenses() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListDepensesQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListDepensesQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListDepensesQuery.Data> response) {
                if (response.data() != null) {
                  List<Depense> depenses = new ArrayList();
                  for(ListDepensesQuery.Item item : response.data().listDepenses().items()) {
                    depenses.add(new Depense(
                        item.id(),
                        item.description(),
                        item.amount(),
                        item.addedAt()

                    ));
                  }


                  source.onSuccess(depenses);
                } else {
                  Log.d(DBG_TAG, "AppSync getDepenses response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getProducts query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable updateDepense(Depense depense) {
    return Completable.create(source -> {
      UpdateDepenseMutation mutation = UpdateDepenseMutation.builder().input(
          UpdateDepenseInput.builder()
              .id(depense.getId())
              .description(depense.getDescription())
              .amount(depense.getAmount())
              .addedAt(depense.getDate())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<UpdateDepenseMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<UpdateDepenseMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync Depenses mutation was successful");
                UpdateDepenseMutation.UpdateDepense prod = response.data().updateDepense();
                Log.d(DBG_TAG, "Depense : " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync depense response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync sellCredit mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }

  public Single<List<Fournisseur>> getFournisseurs() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListFournisseursQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListFournisseursQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListFournisseursQuery.Data> response) {
                if (response.data() != null) {
                  List<Fournisseur> fournisseurs = new ArrayList();
                  for(ListFournisseursQuery.Item item : response.data().listFournisseurs().items()) {
                    fournisseurs.add(new Fournisseur(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.telephone(),
                        item.addedAt())
                    );
                  }


                  source.onSuccess(fournisseurs);
                } else {
                  Log.d(DBG_TAG, "AppSync getDepenses response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getMovements query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable addFournisseur(Fournisseur fournisseur) {
    return Completable.create(source -> {
      CreateFournisseurMutation mutation = CreateFournisseurMutation.builder().input(
          CreateFournisseurInput.builder()
              .id(fournisseur.getId())
              .name(fournisseur.getPersonName())
              .description(fournisseur.getDescription())
              .telephone(fournisseur.getTelephone())
              .addedAt(fournisseur.getDate())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<CreateFournisseurMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<CreateFournisseurMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync createFournisseur mutation was successful");
                CreateFournisseurMutation.CreateFournisseur prod = response.data().createFournisseur();
                Log.d(DBG_TAG, "Fournisseur created: " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync createfournisseur response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync createDepense mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }

  public Single<List<Fournisseur>> getUpdateFournisseurs() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListFournisseursQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListFournisseursQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListFournisseursQuery.Data> response) {
                if (response.data() != null) {
                  List<Fournisseur> fournisseurs = new ArrayList();
                  for(ListFournisseursQuery.Item item : response.data().listFournisseurs().items()) {
                    fournisseurs.add(new Fournisseur(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.addedAt(),
                        item.telephone()

                    ));
                  }


                  source.onSuccess(fournisseurs);
                } else {
                  Log.d(DBG_TAG, "AppSync getFournisseurs response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getFournisseurs query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable updateFournisseur(Fournisseur fournisseur) {
    return Completable.create(source -> {
      UpdateFournisseurMutation mutation = UpdateFournisseurMutation.builder().input(
          UpdateFournisseurInput.builder()
              .id(fournisseur.getId())
              .name(fournisseur.getPersonName())
              .description(fournisseur.getDescription())
              .addedAt(fournisseur.getDate())
              .telephone(fournisseur.getTelephone())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<UpdateFournisseurMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<UpdateFournisseurMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync Fournisseurs mutation was successful");
                UpdateFournisseurMutation.UpdateFournisseur prod = response.data().updateFournisseur();
                Log.d(DBG_TAG, "Fournisseur : " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync fournisseur response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync fournisseur mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }

  public Single<List<Client>> getClients() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListClientsQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListClientsQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListClientsQuery.Data> response) {
                if (response.data() != null) {
                  List<Client> clients = new ArrayList();
                  for(ListClientsQuery.Item item : response.data().listClients().items()) {
                    clients.add(new Client(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.telephone(),
                        item.addedAt())
                    );
                  }


                  source.onSuccess(clients);
                } else {
                  Log.d(DBG_TAG, "AppSync getClients response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getClients query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable addClient(Client client) {
    return Completable.create(source -> {
      CreateClientMutation mutation = CreateClientMutation.builder().input(
          CreateClientInput.builder()
              .id(client.getId())
              .name(client.getPersonName())
              .description(client.getDescription())
              .telephone(client.getTelephone())
              .addedAt(client.getDate())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<CreateClientMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<CreateClientMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync createClient mutation was successful");
                CreateClientMutation.CreateClient prod = response.data().createClient();
                Log.d(DBG_TAG, "Client created: " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync createfournisseur response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync createClient mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }

  public Single<List<Client>> getUpdateClients() {
    return Single.create(source ->
        mAppSyncClient
            .query(ListClientsQuery.builder().build())
            .enqueue(new GraphQLCall.Callback<ListClientsQuery.Data>() {
              @Override
              public void onResponse(@Nonnull Response<ListClientsQuery.Data> response) {
                if (response.data() != null) {
                  List<Client> clients = new ArrayList();
                  for(ListClientsQuery.Item item : response.data().listClients().items()) {
                    clients.add(new Client(
                        item.id(),
                        item.name(),
                        item.description(),
                        item.addedAt(),
                        item.telephone()

                    ));
                  }


                  source.onSuccess(clients);
                } else {
                  Log.d(DBG_TAG, "AppSync getClients response was null");
                }
              }

              @Override
              public void onFailure(@Nonnull ApolloException e) {
                Log.d(DBG_TAG, "AppSync getFournisseurs query failed: error = " + e.toString() + ", cause = " + e.getCause());
                source.onError(e);
              }
            }));
  }

  public static Completable updateClient(Client client) {
    return Completable.create(source -> {
      UpdateClientMutation mutation = UpdateClientMutation.builder().input(
          UpdateClientInput.builder()
              .id(client.getId())
              .name(client.getPersonName())
              .description(client.getDescription())
              .addedAt(client.getDate())
              .telephone(client.getTelephone())
              .build())
          .build();

      mAppSyncClient.mutate(mutation)
          .enqueue(new GraphQLCall.Callback<UpdateClientMutation.Data>(){
            @Override
            public void onResponse(@Nonnull Response<UpdateClientMutation.Data> response) {
              if (response.data() != null) {
                Log.d(DBG_TAG, "AppSync Clients mutation was successful");
                UpdateClientMutation.UpdateClient prod = response.data().updateClient();
                Log.d(DBG_TAG, "Client : " + prod);
                source.onComplete();
              } else {
                Log.d(DBG_TAG, "AppSync client response was null");
                source.onError(new ApolloException("AppSync response was null"));
              }
            }

            @Override
            public void onFailure(@Nonnull ApolloException e) {
              Log.d(DBG_TAG, "AppSync client mutation failed: " + e.toString() + ", cause = " + e.getCause());
              source.onError(e);
            }
          });
    });
  }


}
