package com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_product_interaction")
public class UserProductInteraction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;

    @Enumerated(EnumType.STRING) // Storing interaction as a String ENUM
    private InteractionType interactionType;

    private LocalDateTime interactionTime;


    // Constructors
    public UserProductInteraction() {}

    public UserProductInteraction(Long userId, Long productId, InteractionType interactionType) {
        this.userId = userId;
        this.productId = productId;
        this.interactionType = interactionType;
        this.interactionTime = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public InteractionType getInteractionType() { return interactionType; }
    public void setInteractionType(InteractionType interactionType) { this.interactionType = interactionType; }

    public LocalDateTime getInteractionTime() { return interactionTime; }
    public void setInteractionTime(LocalDateTime interactionTime) { this.interactionTime = interactionTime; }

}
