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

/** This is an auto generated class representing the Depense type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Depenses", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.DELETE, ModelOperation.UPDATE, ModelOperation.READ })
})
public final class Depense implements Model {
  public static final QueryField ID = field("Depense", "id");
  public static final QueryField DESCRIPTION = field("Depense", "description");
  public static final QueryField AMOUNT = field("Depense", "amount");
  public static final QueryField ADDED_AT = field("Depense", "addedAt");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="Int", isRequired = true) Integer amount;
  private final @ModelField(targetType="String") String addedAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getDescription() {
      return description;
  }
  
  public Integer getAmount() {
      return amount;
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
  
  private Depense(String id, String description, Integer amount, String addedAt) {
    this.id = id;
    this.description = description;
    this.amount = amount;
    this.addedAt = addedAt;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Depense depense = (Depense) obj;
      return ObjectsCompat.equals(getId(), depense.getId()) &&
              ObjectsCompat.equals(getDescription(), depense.getDescription()) &&
              ObjectsCompat.equals(getAmount(), depense.getAmount()) &&
              ObjectsCompat.equals(getAddedAt(), depense.getAddedAt()) &&
              ObjectsCompat.equals(getCreatedAt(), depense.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), depense.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDescription())
      .append(getAmount())
      .append(getAddedAt())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Depense {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("amount=" + String.valueOf(getAmount()) + ", ")
      .append("addedAt=" + String.valueOf(getAddedAt()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static AmountStep builder() {
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
  public static Depense justId(String id) {
    return new Depense(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      description,
      amount,
      addedAt);
  }
  public interface AmountStep {
    BuildStep amount(Integer amount);
  }
  

  public interface BuildStep {
    Depense build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep addedAt(String addedAt);
  }
  

  public static class Builder implements AmountStep, BuildStep {
    private String id;
    private Integer amount;
    private String description;
    private String addedAt;
    @Override
     public Depense build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Depense(
          id,
          description,
          amount,
          addedAt);
    }
    
    @Override
     public BuildStep amount(Integer amount) {
        Objects.requireNonNull(amount);
        this.amount = amount;
        return this;
    }
    
    @Override
     public BuildStep description(String description) {
        this.description = description;
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
    private CopyOfBuilder(String id, String description, Integer amount, String addedAt) {
      super.id(id);
      super.amount(amount)
        .description(description)
        .addedAt(addedAt);
    }
    
    @Override
     public CopyOfBuilder amount(Integer amount) {
      return (CopyOfBuilder) super.amount(amount);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder addedAt(String addedAt) {
      return (CopyOfBuilder) super.addedAt(addedAt);
    }
  }
  
}
