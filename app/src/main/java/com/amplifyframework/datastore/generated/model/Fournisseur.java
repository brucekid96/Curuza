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

/** This is an auto generated class representing the Fournisseur type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Fournisseurs", authRules = {
  @AuthRule(allow = AuthStrategy.OWNER, ownerField = "owner", identityClaim = "cognito:username", provider = "userPools", operations = { ModelOperation.CREATE, ModelOperation.DELETE, ModelOperation.UPDATE, ModelOperation.READ })
})
public final class Fournisseur implements Model {
  public static final QueryField ID = field("Fournisseur", "id");
  public static final QueryField NAME = field("Fournisseur", "name");
  public static final QueryField DESCRIPTION = field("Fournisseur", "description");
  public static final QueryField ADDED_AT = field("Fournisseur", "addedAt");
  public static final QueryField TELEPHONE = field("Fournisseur", "telephone");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String") String description;
  private final @ModelField(targetType="String") String addedAt;
  private final @ModelField(targetType="String") String telephone;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  public String getId() {
      return id;
  }
  
  public String getName() {
      return name;
  }
  
  public String getDescription() {
      return description;
  }
  
  public String getAddedAt() {
      return addedAt;
  }
  
  public String getTelephone() {
      return telephone;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Fournisseur(String id, String name, String description, String addedAt, String telephone) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.addedAt = addedAt;
    this.telephone = telephone;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Fournisseur fournisseur = (Fournisseur) obj;
      return ObjectsCompat.equals(getId(), fournisseur.getId()) &&
              ObjectsCompat.equals(getName(), fournisseur.getName()) &&
              ObjectsCompat.equals(getDescription(), fournisseur.getDescription()) &&
              ObjectsCompat.equals(getAddedAt(), fournisseur.getAddedAt()) &&
              ObjectsCompat.equals(getTelephone(), fournisseur.getTelephone()) &&
              ObjectsCompat.equals(getCreatedAt(), fournisseur.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), fournisseur.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDescription())
      .append(getAddedAt())
      .append(getTelephone())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Fournisseur {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("description=" + String.valueOf(getDescription()) + ", ")
      .append("addedAt=" + String.valueOf(getAddedAt()) + ", ")
      .append("telephone=" + String.valueOf(getTelephone()) + ", ")
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
  public static Fournisseur justId(String id) {
    return new Fournisseur(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      description,
      addedAt,
      telephone);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Fournisseur build();
    BuildStep id(String id);
    BuildStep description(String description);
    BuildStep addedAt(String addedAt);
    BuildStep telephone(String telephone);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private String description;
    private String addedAt;
    private String telephone;
    @Override
     public Fournisseur build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Fournisseur(
          id,
          name,
          description,
          addedAt,
          telephone);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
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
    
    @Override
     public BuildStep telephone(String telephone) {
        this.telephone = telephone;
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
    private CopyOfBuilder(String id, String name, String description, String addedAt, String telephone) {
      super.id(id);
      super.name(name)
        .description(description)
        .addedAt(addedAt)
        .telephone(telephone);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder description(String description) {
      return (CopyOfBuilder) super.description(description);
    }
    
    @Override
     public CopyOfBuilder addedAt(String addedAt) {
      return (CopyOfBuilder) super.addedAt(addedAt);
    }
    
    @Override
     public CopyOfBuilder telephone(String telephone) {
      return (CopyOfBuilder) super.telephone(telephone);
    }
  }
  
}
