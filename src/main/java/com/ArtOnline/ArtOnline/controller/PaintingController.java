package com.ArtOnline.ArtOnline.controller;

import com.ArtOnline.ArtOnline.model.Paintings;
import com.ArtOnline.ArtOnline.repository.PaintingDao;
import com.ArtOnline.ArtOnline.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class PaintingController {

    @Autowired
    private PaintingDao paintingDao;

    @Autowired
    private UserDao userDao;

    @GetMapping(value = "/paintings")
    public String viewPaintings(@RequestParam(name = "name", required = false) String name, Model m, Principal p){
        System.out.println(name);
        if( name == null){
            m.addAttribute("paintings", paintingDao.listAllPaintings());
        }
        else {
            m.addAttribute("paintings", paintingDao.listPaintingsWithNameLike(name));
        }
       m.addAttribute("user_email", p.getName());
        return "paintings";
    }

    @GetMapping(value = "/artists")
    public String viewArtists( Model m, Principal p){
        m.addAttribute("user_email", p.getName());
        m.addAttribute("artists", paintingDao.listAllArtists());
        return "artists";
    }

    @GetMapping(value = "/framed_paintings")
    public String viewFramedPaintings(@RequestParam(name = "name", required = false) String name, Model m, Principal p){
        System.out.println(name);
        if( name == null ){
            m.addAttribute("paintings", paintingDao.listAllFramedPaintings());
        }
        else {
            m.addAttribute("paintings", paintingDao.listFramedPaintingsWithNameLike(name));
        }        
        m.addAttribute("user_email", p.getName());
        return "framedpaintings";
    }

    @RequestMapping(value = "/painting/{painting_id}")
    public String viewPaintingDetail(Model m, Principal p, @PathVariable int painting_id){
        Paintings d = paintingDao.getPaintingByID(painting_id);
        m.addAttribute("painting", d);
        m.addAttribute("user_email", p.getName());
        m.addAttribute( "artist", paintingDao.getArtistByPaintingID( painting_id));
        return "paintingDetail";
    }
    
}
