package com.ArtOnline.ArtOnline.controller;

import com.ArtOnline.ArtOnline.model.*;
import com.ArtOnline.ArtOnline.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class AdminController {

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

    @RequestMapping("/admin")
    public String adminHome(Model m, Principal p){
        return "adminHome";
    }

    @GetMapping("/adminPaintings")
    public String adminPaintings(Model m, Principal p){
        m.addAttribute("paintings", paintingDao.listAllPaintings());
        return "adminPaintings";
    }
    @RequestMapping("/admin/removePainting/{painting_id}")
    public String removePainting(Model m, Principal p, @PathVariable int painting_id)
    {
        paintingDao.removePainting(painting_id);
        return "redirect:/adminPaintings";
    }
    @GetMapping("/adminUsers")
    public String adminUsers(Model m, Principal p){
        m.addAttribute("users", userDao.listAllUsers());
        return "adminUsers";
    }
    @GetMapping("/adminGalleries")
    public String adminGalleries(Model m, Principal p){
        m.addAttribute("galleries", galleryDao.getAllGalleries());
        return "adminGalleries";
    }
    @RequestMapping("/admin/removeGallery/{gallery_id}")
    public String removeGallery(Model m, Principal p, @PathVariable int gallery_id)
    {
        galleryDao.removeGallery(gallery_id);
        return "redirect:/adminGalleries";
    }
   
}