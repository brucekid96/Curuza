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

/** This is an auto generated class representing the Product type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Products", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
public final class Product implements Model {
  public static final QueryField ID = field("Product", "id");
  public static final QueryField PHOTO_ID = field("Product", "photoId");
  public static final QueryField NAME = field("Product", "name");
  public static final QueryField DESCRIPTION = field("Product", "description");
  public static final QueryField QUANTITY = field("Product", "quantity");
  public static final QueryField P_ACHAT = field("Product", "pAchat");
  public static final QueryField P_VENTE = field("Product", "pVente");
  public static final QueryField ADDED_AT = field("Product", "addedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="ID") String photoId;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="Int") Integer quantity;
  private final @ModelField(targetType="Int") Integer pAchat;
  private final @ModelField(targetType="Int") Integer pVente;
  private final @ModelField(targetType="String") String addedAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getPhotoId() {
      return photoId;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
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
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Product(String id, String photoId, String name, String description, Integer quantity, Integer pAchat, Integer pVente, String addedAt) {
    this.id = id;
    this.photoId = photoId;
    this.name = name;
    this.description = description;
    this.quantity = quantity;
    this.pAchat = pAchat;
    this.pVente = pVente;
    this.addedAt = addedAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Product product = (Product) obj;
      return ObjectsCompat.equals(getId(), product.getId()) &&
              ObjectsCompat.equals(getPhotoId(), product.getPhotoId()) &&
              ObjectsCompat.equals(getName(), product.getName()) &&
              ObjectsCompat.equals(getDescription(), product.getDescription()) &&
              ObjectsCompat.equals(getQuantity(), product.getQuantity()) &&
              ObjectsCompat.equals(getPAchat(), product.getPAchat()) &&
              ObjectsCompat.equals(getPVente(), product.getPVente()) &&
              ObjectsCompat.equals(getAddedAt(), product.getAddedAt()) &&
              ObjectsCompat.equals(getCreatedAt(), product.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), product.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getPhotoId())
      .append(getName())
      .append(getDescription())
      .append(getQuantity())
      .append(getPAchat())
      .append(getPVente())
      .append(getAddedAt())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Product {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("photoId=" + String.valueOf(getPhotoId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("quantity=" + String.valueOf(getQuantity()) + ", ")
      .append("pAchat=" + String.valueOf(getPAchat()) + ", ")
      .append("pVente=" + String.valueOf(getPVente()) + ", ")
      .append("addedAt=" + String.valueOf(getAddedAt()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Product justId(String id) {
    return new Product(
      id,
      null,
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
      photoId,
      name,
      description,
      quantity,
      pAchat,
      pVente,
      addedAt);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Product build();
    BuildStep id(String id);
    BuildStep photoId(String photoId);
    BuildStep description(String description);
    BuildStep quantity(Integer quantity);
    BuildStep pAchat(Integer pAchat);
    BuildStep pVente(Integer pVente);
    BuildStep addedAt(String addedAt);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String photoId;
    private String description;
    private Integer quantity;
    private Integer pAchat;
    private Integer pVente;
    private String addedAt;
    @Override
     public Product build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Product(
          id,
          photoId,
          name,
          description,
          quantity,
          pAchat,
          pVente,
          addedAt);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep photoId(String photoId) {
        this.photoId = photoId;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
        return this;
    }
    
    @Override
     public BuildStep quantity(Integer quantity) {
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
    private CopyOfBuilder(String id, String photoId, String name, String description, Integer quantity, Integer pAchat, Integer pVente, String addedAt) {
      super.id(id);
      super.name(name)
        .photoId(photoId)
        .description(description)
        .quantity(quantity)
        .pAchat(pAchat)
        .pVente(pVente)
        .addedAt(addedAt);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder photoId(String photoId) {
      return (CopyOfBuilder) super.photoId(photoId);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
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
  }
  
}
