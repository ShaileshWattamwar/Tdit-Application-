package com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity;

import jakarta.persistence.*;

@Entity
public class UserInteraction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    private String interactionType;

    public String getInteractionType() {
        return interactionType;
    }

    public void setInteractionType(String interactionType) {
        this.interactionType = interactionType;
    }

    public UserInteraction() {
    }

    public UserInteraction(Long id, User user, Product product, String interactionType) {
        this.id = id;
        this.user = user;
        this.product = product;
        this.interactionType = interactionType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
