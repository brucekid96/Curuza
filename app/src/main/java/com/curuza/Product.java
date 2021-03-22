package com.curuza;

public class Product {

    private int resourceId;
    private String name;
    private String description;
    private int quantity;
    private int p_achat;
    private int p_vente;


    public Product(int resourceId, String name, String description, int quantity, int p_achat, int p_vente) {
        this.resourceId = resourceId;
        this.name = name;
        this.description=description;
        this.quantity= quantity;
        this.p_achat = p_achat;
        this.p_vente = p_vente;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description= description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getP_achat() {
        return p_achat;
    }

    public void setP_achat(int p_achat) {
        this.p_achat = p_achat;
    }

    public int getP_vente() {
        return p_vente;
    }

    public void setP_vente(int p_vente) {
        this.p_vente = p_vente;
    }


}
