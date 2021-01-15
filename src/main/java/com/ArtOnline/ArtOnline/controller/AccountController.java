package com.ArtOnline.ArtOnline.controller;

import com.ArtOnline.ArtOnline.model.*;
import com.ArtOnline.ArtOnline.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.ArtOnline.ArtOnline.storage.StorageService;
import com.ArtOnline.ArtOnline.storage.StorageFileNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
@Controller
public class AccountController {
    @Autowired
    private StorageService storageService;
    @Autowired
    private GalleryDao galleryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private PaintingDao paintingDao;

    @Autowired
    private PhoneDao phoneDao;

    @RequestMapping("/account/{gallery_id}/profile")
    public String accountProfile(Model m, Principal p, @PathVariable int gallery_id) {
        System.out.println("account/gallery_id/profile: ");
        System.out.println(gallery_id);
        System.out.println("yup");
        User u = userDao.findByUsername(p.getName());
        System.out.println("user done");
        m.addAttribute("user", u);
        m.addAttribute("user_email", p.getName());
        if(!galleryDao.isOwner(u.getUser_id(), gallery_id))
            return "redirect:/self/galleries";
        System.out.println("owner done");
        Gallery s = galleryDao.getGalleryByID(gallery_id);
        Address a = addressDao.getAddressByID(s.getAddress_id());
        System.out.println("all done");
        m.addAttribute("gallery_id", gallery_id);
        m.addAttribute("galleries", galleryDao.getGalleriesByUserID(u.getUser_id()));

        m.addAttribute("gallery", s);
        m.addAttribute("address", a);
        m.addAttribute("phones", phoneDao.getPhoneByGalleryID(gallery_id));
        return "accountProfile";
    }

    @GetMapping("/account/{gallery_id}/add_phone")
    public String addPhone(Model m, Principal p, @PathVariable int gallery_id) {

        User u = userDao.findByUsername(p.getName());
        m.addAttribute("user", u);
        m.addAttribute("user_email", p.getName());
        m.addAttribute("gallery_id", gallery_id);
        if(!galleryDao.isOwner(u.getUser_id(), gallery_id))
            return "redirect:/self/galleries";

        m.addAttribute("gallery_id", gallery_id);
        m.addAttribute("galleries", galleryDao.getGalleriesByUserID(u.getUser_id()));
        m.addAttribute("phone_no", new GalleryPhone());
        return "accountPhone";
    }

    @PostMapping("/account/{gallery_id}/add_phone")
    public String addPhone(@RequestParam String phone_no, Principal p, @PathVariable int gallery_id) {

        User u = userDao.findByUsername(p.getName());
        if(!galleryDao.isOwner(u.getUser_id(), gallery_id))
            return "redirect:/self/galleries";
      
        int user_id = userDao.getUserIDByEmailID(p.getName());
        galleryDao.saveGalleryPhone(phone_no, gallery_id);
        return "redirect:/account/"+gallery_id+"/profile";
    }

    
    @RequestMapping("/account/{gallery_id}/inventory")
    public String viewInventory(Model m, Principal p, @PathVariable int gallery_id) {

        User u = userDao.findByUsername(p.getName());
        m.addAttribute("user", u);
        m.addAttribute("user_email", p.getName());
        if(!galleryDao.isOwner(u.getUser_id(), gallery_id))
            return "redirect:/self/galleries";

        m.addAttribute("gallery_id", gallery_id);
        m.addAttribute("galleries", galleryDao.getGalleriesByUserID(u.getUser_id()));
        m.addAttribute("paintings" , galleryDao.getInventoryByID(gallery_id));
        return "accountInventory";
    }

    @GetMapping("/account/{gallery_id}/inventory/add")
    public String addInventory(Model m, Principal p, @PathVariable int gallery_id) {

        User u = userDao.findByUsername(p.getName());

        if(!"gallery".equals(u.getRole()))
            return "redirect:/self/galleries";

        m.addAttribute("painting", new Paintings());
        m.addAttribute("user_email", p.getName());
        m.addAttribute("artist", new Artist());
        return "addInventory";
    }

    @PostMapping("/account/{gallery_id}/inventory/add")
    public String addInventory(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes, @RequestParam String name,@RequestParam int is_framed,@RequestParam double length,@RequestParam double breadth,@RequestParam double price,@RequestParam String first_name,@RequestParam String last_name,@RequestParam String email_id,@RequestParam String painting_style,@RequestParam int experience,@RequestParam String license, @PathVariable int gallery_id, Principal p) {

        User u = userDao.findByUsername(p.getName());
        if(!u.getRole().equals("gallery"))
            return "redirect:/self/galleries";
        String image_path=  "/images/"+file.getOriginalFilename();
        paintingDao.savePainting(name, is_framed, length, breadth, price,first_name, last_name, email_id, painting_style, experience, license, gallery_id, image_path);
        storageService.store(file);
		redirectAttributes.addFlashAttribute("message","You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/account/" + gallery_id + "/inventory";
    }
    @ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

    @RequestMapping("/account/{gallery_id}/orders")
    public String accountOrders(Model m, Principal p, @PathVariable int gallery_id){

        User u = userDao.findByUsername(p.getName());
        m.addAttribute("user_email", p.getName());
        m.addAttribute("user", u);
        if(!galleryDao.isOwner(u.getUser_id(), gallery_id))
            return "redirect:/self/galleries";

        m.addAttribute("gallery_id", gallery_id);
        //m.addAttribute("galleries", galleryDao.getGalleriesByUserID(u.getUser_id()));
        m.addAttribute("orders", orderDao.getOrdersByGalleryID(gallery_id));
        return "accountOrders";
    }

    @RequestMapping("/account/{gallery_id}/feedbacks")
    public String viewFeedBacks(Model m, Principal p, @PathVariable int gallery_id) {

        User u = userDao.findByUsername(p.getName());
        m.addAttribute("user", u);
        m.addAttribute("user_email", p.getName());
        if(!galleryDao.isOwner(u.getUser_id(), gallery_id))
            return "redirect:/self/galleries";

        m.addAttribute("gallery_id", gallery_id);
        m.addAttribute("galleries", galleryDao.getGalleriesByUserID(u.getUser_id()));
        m.addAttribute("reviews", orderDao.getReviewsForGalleryID(gallery_id));
        return "accountFeedbacks";
    }


    
    @RequestMapping("/account/{gallery_id}/order/{order_id}")
    public String viewGalleryOrder(Model m, Principal p, @PathVariable int gallery_id, @PathVariable int order_id){

        User u = userDao.findByUsername(p.getName());
        m.addAttribute("user_email", p.getName());
        m.addAttribute("user", u);
        if(!galleryDao.isOwner(u.getUser_id(), gallery_id))
            return "redirect:/self/galleries";

        m.addAttribute("gallery_id", gallery_id);
        m.addAttribute("galleries", galleryDao.getGalleriesByUserID(u.getUser_id()));
        m.addAttribute("order", orderDao.getOrderByID(order_id));
        return "viewGalleryOrder";
    }
}
