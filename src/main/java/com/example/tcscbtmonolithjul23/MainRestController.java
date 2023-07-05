package com.example.tcscbtmonolithjul23;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/0.1")
public class MainRestController
{
    @Autowired
    CredentialRepository credentialRepository;
    @Autowired
    UserdetailRepository userdetailRepository;
    @Autowired
    UsertypelinkRepository usertypelinkRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OfferDetailRepository offerDetailRepository;

    @Autowired
    ProductofferstatusRepository productofferstatusRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderstatusRepository orderstatusRepository;

    @Autowired
    UserService userService;

    @Autowired
    ProductOfferService productOfferService;

    @Autowired
    ComposeOrderService composeOrderService;

    @GetMapping("/")
    public ResponseEntity<String> greet()
    {
        return new ResponseEntity<>("Hello from TCS CBT", HttpStatus.OK);
    }

    @PostMapping("/") // improper use of the post mapping
    public ResponseEntity<String> greetPost()
    {
        return new ResponseEntity<>("Hello from TCS CBT from the Post Handler", HttpStatus.OK);
    }

    @PostMapping("signup")
    public ResponseEntity<String> signup(@RequestParam("username") String username, @RequestParam("password") String password )
    {
        Credential credential = new Credential();
        credential.setUsername(username);
        credential.setPassword(password);
        credentialRepository.save(credential);
        return new ResponseEntity<>("New User Successfully Signed Up", HttpStatus.OK);
    }

    @PostMapping("save/userdetails")
    public ResponseEntity<String> saveUserDetails( @RequestParam("username") String username,
                                                   @RequestParam("fname") String fname,
                                                   @RequestParam("lname") String lname,
                                                   @RequestParam("email") String email,
                                                   @RequestParam("phone") String phone)
    {

        Userdetail userdetail = new Userdetail();
        userdetail.setId(username);
        userdetail.setFname(fname);
        userdetail.setLname(lname);
        userdetail.setEmail(email);
        userdetail.setPhone(phone);
        userdetailRepository.save(userdetail);

        return new ResponseEntity<>("Userdetails Updated", HttpStatus.OK);

    }

    @PostMapping("save/usertype")
    public ResponseEntity<String> saveUserType( @RequestParam("username") String username,
                                                @RequestParam("usertype") String usertype)
    {
        Usertypelink usertypelink = new Usertypelink();
        usertypelink.setId(String.valueOf(Integer.valueOf((int) (Math.random()*10000))));
        usertypelink.setUsername(username);
        usertypelink.setType(usertype);
        usertypelinkRepository.save(usertypelink);

        return new ResponseEntity<>("New User Type Link Saved", HttpStatus.OK);

    }

    @PostMapping("/get/{username}")
    public User getUser(@PathVariable("username") String username)
    {
        return userService.getUser(username);
    }

    @GetMapping("get/product/all")
    public List<Product> getProducts()
    {
        return productRepository.findAll();
    }

    @PostMapping("save/offer")
    public ResponseEntity<String> saveOffer(@RequestBody OfferDetail offer)
    {
        offerDetailRepository.save(offer);
        Productofferstatus productofferstatus = new Productofferstatus();
        productofferstatus.setOfferid(offer.getId());
        productofferstatus.setStatus("OPEN");
        productofferstatus.setId(String.valueOf(((int) (Math.random()*10000))));
        productofferstatusRepository.save(productofferstatus);
        return new ResponseEntity<>("New Offer Created",HttpStatus.OK);
    }

    @GetMapping("get/offer/{offerid}")
    public ResponseEntity<ProductOffer> getOffer(@PathVariable("offerid") String offerid)
    {
         ProductOffer productOffer = productOfferService.getOffer(offerid);

         if(productOffer.getOfferDetail() != null)
         {
             return new ResponseEntity<>(productOffer,HttpStatus.OK);
         }
         else
         {
             return new ResponseEntity<>(productOffer,HttpStatus.NO_CONTENT);
         }
    }


    @GetMapping("get/offer/all")
    public List<ProductOffer> getAllOffers()
    {
       List<OfferDetail> offerDetailList = offerDetailRepository.findAll();

       return offerDetailList.stream().map((offerDetail) -> {

           return productOfferService.getOffer(offerDetail.getId());

       }).collect(Collectors.toList());

    }

    @PostMapping("save/order")
    public ResponseEntity<String> saveOrder(@RequestBody Order order)
    {
        orderRepository.save(order);
        return new ResponseEntity<>("New Order Saved", HttpStatus.OK);
    }

    @GetMapping("get/order/sellerwise/{sellername}")
    public List<ComposedOrder> getOrdersSellerwise(@PathVariable String sellername)
    {
          List<Order> orderList = orderRepository.findAll();
          return orderList.stream().filter(order -> productOfferService.
                  getOffer(order.getOfferid()).getOfferDetail().
                  getSellername().equals(sellername)).
                  map(order -> {

                      return composeOrderService.composeOrder(order.getOfferid(), order.getBuyername(), order);

                  } ).collect(Collectors.toList());
    }

    @PostMapping("save/order/status")
    public ResponseEntity<String> setOrderStatus(@RequestBody Orderstatus orderstatus)
    {
        orderstatusRepository.save(orderstatus);
        return new ResponseEntity<>("Order Status Updated", HttpStatus.OK);
    }


}
