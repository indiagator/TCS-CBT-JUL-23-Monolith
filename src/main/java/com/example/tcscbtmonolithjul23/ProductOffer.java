package com.example.tcscbtmonolithjul23;

import java.util.List;

public class ProductOffer
{

    Product product;

    OfferDetail offerDetail;

    List<Productofferstatus> productofferstatusList;

    Integer offerAmnt;

    public void initializeOffer(Product product, OfferDetail offerDetail, List<Productofferstatus> productofferstatusList)
    {
        setProduct(product);
        setOfferDetail(offerDetail);
        setProductofferstatusList(productofferstatusList);

        offerAmnt = offerDetail.getQty() * offerDetail.getUnitprice();

    }

    public Integer getOfferAmnt() {
        return offerAmnt;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOfferDetail(OfferDetail offerDetail) {
        this.offerDetail = offerDetail;

    }

    public void setProductofferstatusList(List<Productofferstatus> productofferstatusList)
    {
        this.productofferstatusList = productofferstatusList;
    }

    public Product getProduct() {
        return product;
    }

    public OfferDetail getOfferDetail() {
        return offerDetail;
    }

    public List<Productofferstatus> getProductofferstatusList() {
        return productofferstatusList;
    }
}
