package com.ArtOnline.ArtOnline.controller;

import com.ArtOnline.ArtOnline.model.Gallery;
import com.ArtOnline.ArtOnline.repository.AddressDao;
import com.ArtOnline.ArtOnline.repository.GalleryDao;
import com.ArtOnline.ArtOnline.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class GalleryController {

    @Autowired
    private GalleryDao galleryDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private AddressDao addressDao;

    @RequestMapping(value = "/galleries")
    public String viewGalleries(@RequestParam(name = "name", required = false) String name, Model m, Principal p){
        if(name==null){
            m.addAttribute("galleries", galleryDao.getAllGalleries());
        }
        else{
            m.addAttribute("galleries", galleryDao.getGalleriesWithNameLike(name));
        }
        if(p != null) m.addAttribute("user_email", p.getName());
        return "galleries";
    }

    @RequestMapping(value = "/gallery/{gallery_id}")
    public String viewGalleryDetail(Model m, @PathVariable int gallery_id, Principal p){
        Gallery s = galleryDao.getGalleryByID(gallery_id);
        m.addAttribute("gallery", s);
        m.addAttribute("address", addressDao.getAddressByID(s.getAddress_id()));
        m.addAttribute("user_email", p.getName());
        return "galleryDetail";
    }

    @RequestMapping(value = "/gallery/{gallery_id}/inventory")
    public String viewGalleryInventory(Model m, Principal p, @PathVariable int gallery_id){
        m.addAttribute("paintings", galleryDao.getInventoryByID(gallery_id));
        m.addAttribute("user_email", p.getName());
        m.addAttribute("user", userDao.findByUsername(p.getName()));
        return "galleryInventory";
    }
}
