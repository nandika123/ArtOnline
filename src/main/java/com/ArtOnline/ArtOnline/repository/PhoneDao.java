package com.ArtOnline.ArtOnline.repository;

import com.ArtOnline.ArtOnline.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Transactional
@Repository
public class PhoneDao {
    @Autowired
    JdbcTemplate jt;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jt = jdbcTemplate;
    }

    public List<UserPhone> getPhoneByUserID(int user_id){
        String query = "select * from userPhone where userPhone.user_id="+user_id;
        List<Map<String,Object>> rs = jt.queryForList(query);
        List<UserPhone> res = new ArrayList<UserPhone>();
        for ( Map<String,Object> r:rs) {
            UserPhone u = new UserPhone();
            u.setPhone_no((String) r.get("phone_no"));
            u.setUser_id((int)r.get("user_id"));
            res.add(u);
        }
        return res;
    }

    public List<GalleryPhone> getPhoneByGalleryID(int gallery_id){
        String query = "select * from galleryPhone where galleryPhone.gallery_id="+gallery_id;
        List<Map<String,Object>> rs = jt.queryForList(query);
        List<GalleryPhone> res = new ArrayList<GalleryPhone>();
        for ( Map<String,Object> r:rs) {
            GalleryPhone u = new GalleryPhone();
            u.setPhone_no((String) r.get("phone_no"));
            u.setGallery_id((int)r.get("gallery_id"));
            res.add(u);
        }
        return res;
    }
}
