package com.example.tcscbtmonolithjul23;

public class ComposedOrder
{
    ProductOffer productOffer;
    User buyer;

    Order order;

    public void setOrder(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public void setProductOffer(ProductOffer productOffer) {
        this.productOffer = productOffer;
    }

    public User getBuyer() {
        return buyer;
    }

    public ProductOffer getProductOffer() {
        return productOffer;
    }
}
