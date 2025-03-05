package com.Version1._Recommandation.FirstLevelReccommadtionModel.DTO;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.InteractionType;

public class InteractionRequest {

    private Long userId;
    private Long productId;
    private InteractionType interactionType;

    // Getters and Setters
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public InteractionType getInteractionType() { return interactionType; }
    public void setInteractionType(InteractionType interactionType) { this.interactionType = interactionType; }

}
