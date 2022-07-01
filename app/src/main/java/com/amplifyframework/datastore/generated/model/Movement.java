package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;

import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Movement type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Movements", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.DELETE, ModelOperation.UPDATE, ModelOperation.READ })
})
public final class Movement implements Model {
  public static final QueryField ID = field("Movement", "id");
  public static final QueryField PRODUCT_ID = field("Movement", "productId");
  public static final QueryField QUANTITY = field("Movement", "quantity");
  public static final QueryField P_ACHAT = field("Movement", "pAchat");
  public static final QueryField P_VENTE = field("Movement", "pVente");
  public static final QueryField ADDED_AT = field("Movement", "addedAt");
  public static final QueryField STATUS = field("Movement", "status");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID", isRequired = true) String productId;
  private final @ModelField(targetType="Int", isRequired = true) Integer quantity;
  private final @ModelField(targetType="Int") Integer pAchat;
  private final @ModelField(targetType="Int") Integer pVente;
  private final @ModelField(targetType="String") String addedAt;
  private final @ModelField(targetType="MovementStatus") MovementStatus status;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getProductId() {
      return productId;
  }
  
  public Integer getQuantity() {
      return quantity;
  }
  
  public Integer getPAchat() {
      return pAchat;
  }
  
  public Integer getPVente() {
      return pVente;
  }
  
  public String getAddedAt() {
      return addedAt;
  }
  
  public MovementStatus getStatus() {
      return status;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Movement(String id, String productId, Integer quantity, Integer pAchat, Integer pVente, String addedAt, MovementStatus status) {
    this.id = id;
    this.productId = productId;
    this.quantity = quantity;
    this.pAchat = pAchat;
    this.pVente = pVente;
    this.addedAt = addedAt;
    this.status = status;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Movement movement = (Movement) obj;
      return ObjectsCompat.equals(getId(), movement.getId()) &&
              ObjectsCompat.equals(getProductId(), movement.getProductId()) &&
              ObjectsCompat.equals(getQuantity(), movement.getQuantity()) &&
              ObjectsCompat.equals(getPAchat(), movement.getPAchat()) &&
              ObjectsCompat.equals(getPVente(), movement.getPVente()) &&
              ObjectsCompat.equals(getAddedAt(), movement.getAddedAt()) &&
              ObjectsCompat.equals(getStatus(), movement.getStatus()) &&
              ObjectsCompat.equals(getCreatedAt(), movement.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), movement.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getProductId())
      .append(getQuantity())
      .append(getPAchat())
      .append(getPVente())
      .append(getAddedAt())
      .append(getStatus())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Movement {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("productId=" + String.valueOf(getProductId()) + ", ")
      .append("quantity=" + String.valueOf(getQuantity()) + ", ")
      .append("pAchat=" + String.valueOf(getPAchat()) + ", ")
      .append("pVente=" + String.valueOf(getPVente()) + ", ")
      .append("addedAt=" + String.valueOf(getAddedAt()) + ", ")
      .append("status=" + String.valueOf(getStatus()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static ProductIdStep builder() {
      return new Builder();
  }
  
  /** 
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Movement justId(String id) {
    return new Movement(
      id,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      productId,
      quantity,
      pAchat,
      pVente,
      addedAt,
      status);
  }
  public interface ProductIdStep {
    QuantityStep productId(String productId);
  }
  

  public interface QuantityStep {
    BuildStep quantity(Integer quantity);
  }
  

  public interface BuildStep {
    Movement build();
    BuildStep id(String id);
    BuildStep pAchat(Integer pAchat);
    BuildStep pVente(Integer pVente);
    BuildStep addedAt(String addedAt);
    BuildStep status(MovementStatus status);
  }
  

  public static class Builder implements ProductIdStep, QuantityStep, BuildStep {
    private String id;
    private String productId;
    private Integer quantity;
    private Integer pAchat;
    private Integer pVente;
    private String addedAt;
    private MovementStatus status;
    @Override
     public Movement build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Movement(
          id,
          productId,
          quantity,
          pAchat,
          pVente,
          addedAt,
          status);
    }
    
    @Override
     public QuantityStep productId(String productId) {
        Objects.requireNonNull(productId);
        this.productId = productId;
        return this;
    }
    
    @Override
     public BuildStep quantity(Integer quantity) {
        Objects.requireNonNull(quantity);
        this.quantity = quantity;
        return this;
    }
    
    @Override
     public BuildStep pAchat(Integer pAchat) {
        this.pAchat = pAchat;
        return this;
    }
    
    @Override
     public BuildStep pVente(Integer pVente) {
        this.pVente = pVente;
        return this;
    }
    
    @Override
     public BuildStep addedAt(String addedAt) {
        this.addedAt = addedAt;
        return this;
    }
    
    @Override
     public BuildStep status(MovementStatus status) {
        this.status = status;
        return this;
    }
    
    /** 
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, String productId, Integer quantity, Integer pAchat, Integer pVente, String addedAt, MovementStatus status) {
      super.id(id);
      super.productId(productId)
        .quantity(quantity)
        .pAchat(pAchat)
        .pVente(pVente)
        .addedAt(addedAt)
        .status(status);
    }
    
    @Override
     public CopyOfBuilder productId(String productId) {
      return (CopyOfBuilder) super.productId(productId);
    }
    
    @Override
     public CopyOfBuilder quantity(Integer quantity) {
      return (CopyOfBuilder) super.quantity(quantity);
    }
    
    @Override
     public CopyOfBuilder pAchat(Integer pAchat) {
      return (CopyOfBuilder) super.pAchat(pAchat);
    }
    
    @Override
     public CopyOfBuilder pVente(Integer pVente) {
      return (CopyOfBuilder) super.pVente(pVente);
    }
    
    @Override
     public CopyOfBuilder addedAt(String addedAt) {
      return (CopyOfBuilder) super.addedAt(addedAt);
    }
    
    @Override
     public CopyOfBuilder status(MovementStatus status) {
      return (CopyOfBuilder) super.status(status);
    }
  }
  
}
