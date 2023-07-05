package com.example.tcscbtmonolithjul23;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductOfferService
{
    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferDetailRepository offerDetailRepository;

    @Autowired
    ProductofferstatusRepository productofferstatusRepository;

    public ProductOffer getOffer(String offerid)
    {
        ProductOffer offer = new ProductOffer();
        Optional<OfferDetail> offerDetail;

        if((offerDetail=offerDetailRepository.findById(offerid)).isPresent())
        {
            //offer.setOfferDetail(offerDetail.get());
            Optional<Product> product;
            if((product = productRepository.findById(offerDetail.get().getHscode())).isPresent())
            {
                //offer.setProduct(product.get());

                Optional<List<Productofferstatus>> productofferstatusList;
                if((productofferstatusList = productofferstatusRepository.findByOfferid(offerid)).isPresent())
                {
                    //offer.setProductofferstatusList(productofferstatusList.get());
                    offer.initializeOffer(product.get(),offerDetail.get(),productofferstatusList.get());
                }
            }


        }
            return offer;
    }

}
