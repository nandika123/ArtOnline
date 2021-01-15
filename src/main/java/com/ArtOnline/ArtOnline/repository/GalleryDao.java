package com.ArtOnline.ArtOnline.repository;


import com.ArtOnline.ArtOnline.model.Paintings;
import com.ArtOnline.ArtOnline.model.Gallery;
import com.ArtOnline.ArtOnline.model.GalleryInventory;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Transactional
@Repository
public class GalleryDao {

    @Autowired
    JdbcTemplate jt;
    @Autowired
    private PaintingDao paintingDao;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jt = jdbcTemplate;
    }

    public int save(String gallery_name,String email_id,String license, Integer address_id) {
        String query = "insert into gallery(gallery_name, email_id, license, address_id) values (?,?,?,?)";
        jt.update(query, gallery_name, email_id, license, address_id);

        String sql = "select gallery_id from gallery where gallery_name=? and email_id=? and license=? and address_id=?";
        return jt.queryForObject(sql, new Object[]{gallery_name, email_id, license, address_id}, Integer.class);
    }

    public void saveGalleryPhone(String phone_no, Integer gallery_id) {
        String query = "insert into galleryPhone values (?,?)";
        jt.update(query, phone_no, gallery_id);
    }

    


    public void addOwner(int user_id, int gallery_id){
        String query = "insert into owner(user_id,gallery_id) values (?,?)";
        jt.update(query, user_id, gallery_id);
    }

    public List<Gallery> getAllGalleries() {
        String query = "select * from gallery";
        return jt.query(query, new RowMapper<Gallery>() {
            @Override
            public Gallery mapRow(ResultSet r, int i) throws SQLException {
                Gallery g = new Gallery();
                g.setGallery_name(r.getString("gallery_name"));
                g.setGallery_id(r.getInt("gallery_id"));
                g.setEmail_id(r.getString("email_id"));
                return g;
            }
        });
    }

    public List<Gallery> getGalleriesWithNameLike(String name) {
        String query = "select * from gallery where gallery_name like '%"+name+"%'";
        return jt.query(query, new RowMapper<Gallery>() {
            @Override
            public Gallery mapRow(ResultSet r, int i) throws SQLException {
                Gallery s = new Gallery();
                s.setGallery_name(r.getString("gallery_name"));
                s.setGallery_id(r.getInt("gallery_id"));
                return s;
            }
        });
    }

    
    public Gallery getGalleryByID(int gallery_id) {
        String query = "select * from gallery where gallery_id="+gallery_id;
        return jt.queryForObject(query, new RowMapper<Gallery>() {
            @Override
            public Gallery mapRow(ResultSet r, int i) throws SQLException {
                Gallery s = new Gallery();
                s.setGallery_name(r.getString("gallery_name"));
                s.setGallery_id(r.getInt("gallery_id"));
                s.setEmail_id(r.getString("email_id"));
                s.setLicense(r.getString("license"));
                s.setAddress_id(r.getInt("address_id"));
                return s;
            }
        });
    }

    public List<Paintings> getInventoryByID(int gallery_id) {
        List<Paintings> res = new ArrayList<Paintings>();
        String query = "select * from galleryInventory where gallery_id=? and status='active'";
        List<Integer> l= new ArrayList<Integer>();
        List<Map<String,Object>> rs = jt.queryForList(query, gallery_id);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
            System.out.println("gives painting ids: ");
            System.out.print(s);
        }
        for(int i=0;i<l.size();i++)
        {
            int id= l.get(i);
            query= "select * from paintings where painting_id="+id;
            Paintings p=  jt.queryForObject(query, new RowMapper<Paintings>() {
                @Override
                public Paintings mapRow(ResultSet row, int i) throws SQLException {
                    Paintings g = new Paintings();
                    g.setPainting_id(row.getInt("painting_id"));
                    g.setName(row.getString("name"));
                    g.setPrice(row.getDouble("price"));
                    g.setIs_framed(row.getInt("is_framed"));
                    g.setLength(row.getFloat("length"));
                    g.setBreadth(row.getFloat("breadth"));
                    g.setImage_path(row.getString("image_path"));
                    return g;
                }
            });
            System.out.print(p.getName());
            System.out.println();
            res.add(p);
        }
        return res;
    }


    public boolean isOwner(int user_id, int gallery_id) {
        String query = "select count(*) from owner where gallery_id=? and user_id=?";
        int cnt = jt.queryForObject(query, new Object[]{gallery_id, user_id}, Integer.class);
        if(cnt == 0) return false;
        return true;
    }

    

    
   
    public List<Gallery> getGalleriesByUserID(int user_id){
        String query = "select * from gallery where gallery_id in (select gallery_id from owner where user_id="+user_id+")";
        return jt.query(query, new RowMapper<Gallery>() {
            @Override
            public Gallery mapRow(ResultSet r, int i) throws SQLException {
                Gallery s = new Gallery();
                s.setGallery_id(r.getInt("gallery_id"));
                s.setGallery_name((r.getString("gallery_name")));
                return s;
            }
        });
    }

   public void removeGallery(int gallery_id){
       String sql= "delete from owner where gallery_id=?";
       jt.update(sql, gallery_id);
       sql= "delete from galleryphone where gallery_id=?";
       jt.update(sql, gallery_id);
       sql= "select * from galleryinventory where gallery_id=?";
       List<Integer> l= new ArrayList<Integer> ();
       List<Map<String,Object>> rs = jt.queryForList(sql, gallery_id);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        for(int i=0;i<l.size();i++)
        {
            paintingDao.removePainting(l.get(i));
        }
        sql= "select address_id from gallery where gallery_id=?";
        int address_id= jt.queryForObject(sql, new Object[]{gallery_id}, Integer.class);
        sql= "delete from gallery where gallery_id=?";
        jt.update(sql, gallery_id);
        sql= "delete from address where address_id=?";
        jt.update(sql,address_id);

      

   }

}
