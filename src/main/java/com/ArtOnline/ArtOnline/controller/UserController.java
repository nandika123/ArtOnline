package com.ArtOnline.ArtOnline.controller;


import com.ArtOnline.ArtOnline.model.*;
import com.ArtOnline.ArtOnline.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.security.Principal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private GalleryDao galleryDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PhoneDao phoneDao;

    @Autowired
    private PaintingDao paintingDao;

    private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat tf = new SimpleDateFormat("HH:mm:ss");

    @RequestMapping("/self/profile")
    public String userProfile(Model m, Principal p) {
        int user_id = userDao.getUserIDByEmailID(p.getName());
        m.addAttribute("user_email", p.getName());
        m.addAttribute("user", userDao.findByUsername(p.getName()));
        m.addAttribute("addresses", addressDao.getAddressesByUserID(user_id));
        m.addAttribute("phones", phoneDao.getPhoneByUserID(user_id));
        return "userProfile";
    }
    // DONE
    @GetMapping("/self/add_address")
    public String addAddress(Model m, Principal p) {
        m.addAttribute("user_email", p.getName());
        m.addAttribute("address", new Address());
        return "userAddress";
    }
    // DONE

    @PostMapping("/self/add_address")
    public String addAddress(@ModelAttribute("address") Address a, Principal p) {
        int address_id = addressDao.saveAddress(a.getPlot_no(), a.getStreet(), a.getCity(),  a.getState());
        addressDao.saveAddressOfUser(address_id, a.getAddress_type(), userDao.getUserIDByEmailID(p.getName()));
        return "redirect:/self/profile";
    }
    // DONE

    @GetMapping("/self/add_phone")
    public String addPhone(Model m, Principal p){
        m.addAttribute("user_email", p.getName());
        m.addAttribute("phone_no", new UserPhone());
        return "userPhone";
    }
    // DONE

    @PostMapping("/self/add_phone")
    public String addPhone(@RequestParam String phone_no, Principal p){
        System.out.print(phone_no);
        int user_id = userDao.getUserIDByEmailID(p.getName());
        userDao.saveUserPhone(phone_no, user_id);
        return "redirect:/self/profile";
    }
    // DONE


    @GetMapping("/self/orders")
    public String userOrders(Model m, Principal p){
        int user_id = userDao.getUserIDByEmailID(p.getName());
        m.addAttribute("user_email", p.getName());
        m.addAttribute("orders", userDao.getOrdersByUserID(user_id));
        return "userOrders";
    }
    // DONE

    @RequestMapping("/self/order/{order_id}")
    public String userOrderDetail(Model m, Principal p,@PathVariable int order_id){
        m.addAttribute("user_email", p.getName());
        m.addAttribute("order", orderDao.getOrderByID(order_id));
        m.addAttribute("paintings", orderDao.getOrderInfo(order_id));
       //m.addAttribute("check_review", orderDao.checkReview(orderDao.getOrderInfo(order_id)));
        return "userOrdersDetail";
    }
    // DONE

    @GetMapping("/self/order/{order_id}/add_review/{painting_id}")
    public String userOrderAddReview(Model m, Principal p, @PathVariable int order_id, @PathVariable int painting_id){
        m.addAttribute("order_id", order_id);
        m.addAttribute("user_email", p.getName());
        m.addAttribute("reviews", new Reviews());
        return "userReview";
    }
    // DONE

    @PostMapping("/self/order/{order_id}/add_review/{painting_id}")
    public String userOrderAddReview(@ModelAttribute("reviews") Reviews r, @PathVariable int order_id, Principal p, @PathVariable int painting_id) {
        userDao.saveUserReview(r.getComment(), r.getRating(), painting_id);
        return "redirect:/self/order/"+order_id;
    }
    // DONE

    @GetMapping("/self/add_gallery")
    public String addGallery(Model m, Principal p) {
        m.addAttribute("gallery", new Gallery());
        m.addAttribute("address", new Address());
        m.addAttribute("user_email", p.getName());
        System.out.println("get add");
        return "createGallery";
    }
    // DONE

    @PostMapping("/self/add_gallery")
    public String addGallery(@ModelAttribute("gallery") Gallery s, @ModelAttribute("address") Address a, Principal p) {

        int address_id = addressDao.saveAddress(a.getPlot_no(), a.getStreet(), a.getCity(),  a.getState());
        int gallery_id = galleryDao.save(s.getGallery_name(), s.getEmail_id(), s.getLicense(), address_id);
        int user_id = userDao.getUserIDByEmailID(p.getName());
        System.out.println("post add");
    
        galleryDao.addOwner(user_id, gallery_id);
        userDao.changeRoleToGallery(user_id);
        return "redirect:/self/galleries";
    }
    // DONE

    @RequestMapping("/self/galleries")
    public String selfGalleries(Model m, Principal p){
        User u = userDao.findByUsername(p.getName());
        m.addAttribute("user", u);
        m.addAttribute("user_email", p.getName());
        m.addAttribute("galleries", userDao.getGalleriesOfUser(u.getUser_id()));
        System.out.println("galleries");
        return "userGalleries";
    }
    // DONE

    @RequestMapping("self/cart")
    public String userCart(Model m, Principal p){
        m.addAttribute("user_email", p.getName());
        m.addAttribute("cart", userDao.getCart(p.getName()));
        m.addAttribute("paintings", userDao.getOrderItems(p.getName()));
        return "userCart";
    }

    @RequestMapping("self/cart/add/{painting_id}")
    public String cartAdd(Model m, Principal p, @PathVariable int painting_id){
        m.addAttribute("user_email", p.getName());
        orderDao.cartAdd( painting_id, p.getName());
        return "redirect:/paintings";
    }

    @RequestMapping("self/order/place_order")
    public String placeOrder(Model m, Principal p){
        m.addAttribute("user_email", p.getName());
        orderDao.placeOrder(p.getName());
        return "redirect:/self/orders";
    }

    @RequestMapping("/self/cart/remove/{painting_id}")
    public String removeCart(Model m, Principal p, @PathVariable int painting_id){
        m.addAttribute("user_email", p.getName());
        orderDao.removePaintingFromCart(painting_id, userDao.getUserIDByEmailID(p.getName()));
        return "redirect:/self/cart";
    }
    @RequestMapping("/self/cancel/order/{order_id}")
    public String cancelOrder(Model m, Principal p, @PathVariable int order_id){
        orderDao.removeOrder(order_id);
        return "redirect:/self/orders";
    }

    @RequestMapping("/self/gallery/{gallery_id}/order/{order_id}/complete")
    public String completeOrder(@PathVariable int gallery_id, @PathVariable int order_id){
        orderDao.changeOrderStatus(order_id, "ordered");
        return "redirect:/self/order/"+order_id;
    }


    @RequestMapping("/self/order/{order_id}/mark/cancel/{painting_id}")
    public String markCancelled(@PathVariable int painting_id, @PathVariable int order_id){
        orderDao.changeOrderStatus(painting_id, "cancelled");
        return "redirect:/self/order/"+order_id;
    }
    // DONE
    @RequestMapping("/self/order/{order_id}/mark/delivered/{painting_id}")
    public String markDelivered(@PathVariable int painting_id, @PathVariable int order_id){
        orderDao.changeOrderStatus(painting_id, "delivered");
        return "redirect:/self/order/"+order_id;
    }
    // DONE
}
