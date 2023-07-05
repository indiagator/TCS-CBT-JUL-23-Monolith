package com.example.tcscbtmonolithjul23;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComposeOrderService
{
    @Autowired
    ProductOfferService productOfferService;

    @Autowired
    UserService userService;

    public ComposedOrder composeOrder(String offerid, String buyername, Order order)
    {
        ComposedOrder composedOrder = new ComposedOrder();
        composedOrder.setProductOffer(productOfferService.getOffer(offerid));
        composedOrder.setBuyer(userService.getUser(buyername));
        composedOrder.setOrder(order);
        return composedOrder;
    }
}
