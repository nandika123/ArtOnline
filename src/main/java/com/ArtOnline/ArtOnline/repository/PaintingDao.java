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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Transactional
@Repository
public class PaintingDao {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jt = jdbcTemplate;
    }

   

    public List<Paintings> listAllPaintings() {
        String sql= "select * from galleryinventory where status='active'";
        List<Integer> l= new ArrayList<Integer> ();
        List<Map<String,Object>> rs = jt.queryForList(sql);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        List<Paintings> res= new ArrayList<Paintings> ();
        for(int i=0;i<l.size();i++)
        {
            sql= "select * from paintings where painting_id="+l.get(i);
            Paintings p=  jt.queryForObject(sql, new RowMapper<Paintings>() {
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
            res.add(p);
        }
      return res;
    }

    public List<Paintings> listPaintingsWithNameLike(String name){
        String sql= "select * from paintings where name like '%"+name+"%' and painting_id in (select painting_id from galleryinventory where status='active')";
        return jt.query(sql, new RowMapper<Paintings>() {
            @Override
            public Paintings mapRow(ResultSet row, int i) throws SQLException {
                Paintings u = new Paintings();
                u.setPainting_id(row.getInt("painting_id"));
                u.setName(row.getString("name"));
                u.setIs_framed(row.getInt("is_framed"));
                u.setArtist_id(row.getInt("artist_id"));
            
                u.setLength(row.getFloat("length"));
                u.setBreadth(row.getFloat("breadth"));
                u.setPrice(row.getDouble("price"));
                u.setImage_path(row.getString("image_path"));
                return u;
            }
        });
    }

    
    public List<Paintings> listFramedPaintingsWithNameLike(String name){
        String sql= "select * from galleryinventory where status='active'";
        List<Integer> l= new ArrayList<Integer> ();
        List<Map<String,Object>> rs = jt.queryForList(sql);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        List<Paintings> res= new ArrayList<Paintings> ();
        for(int i=0;i<l.size();i++)
        {
            sql= "select * from paintings where painting_id="+l.get(i)+" and is_framed=1 and name like '%"+name+"%'";
            Paintings p=  jt.queryForObject(sql, new RowMapper<Paintings>() {
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
            res.add(p);
        }
      return res;
    }

    public List<Paintings> getPaintingsWithArtist(String name){
        String query = "select * from paintings where artist_name like '%"+name+"%'";
        return jt.query(query, new RowMapper<Paintings>() {
            @Override
            public Paintings mapRow(ResultSet row, int i) throws SQLException {
                Paintings u = new Paintings();
                u.setPainting_id(row.getInt("painting_id"));
                u.setName(row.getString("name"));
                u.setIs_framed(row.getInt("is_framed"));
                u.setArtist_id(row.getInt("artist_id"));
            
                u.setLength(row.getFloat("length"));
                u.setBreadth(row.getFloat("breadth"));
                u.setPrice(row.getDouble("price"));
                u.setImage_path(row.getString("image_path"));
                return u;
            }
        });
    }

    public List<Paintings> listAllFramedPaintings() {
        String sql= "select * from galleryinventory where status='active'";
        List<Integer> l= new ArrayList<Integer> ();
        List<Map<String,Object>> rs = jt.queryForList(sql);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        List<Paintings> res= new ArrayList<Paintings> ();
        for(int i=0;i<l.size();i++)
        {
            sql= "select * from paintings where painting_id="+l.get(i)+" and is_framed=1";
            Paintings p=  jt.queryForObject(sql, new RowMapper<Paintings>() {
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
            res.add(p);
        }
      return res;
    }

    public List<Artist> listAllArtists() {
        String query = "select * from artist";
        return jt.query(query, new RowMapper<Artist>() {
            @Override
            public Artist mapRow(ResultSet row, int i) throws SQLException {
                Artist g = new Artist();
                g.setArtist_id(row.getInt("artist_id"));
                g.setFirst_name(row.getString("first_name"));
                g.setLast_name(row.getString("last_name"));
                g.setEmail_id(row.getString("email_id"));
                g.setPainting_style(row.getString("painting_style"));
                g.setExperience(row.getInt("experience"));
                g.setLicense(row.getString("license"));
                return g;
            }
        });
    }

    public Paintings getPaintingByID(int painting_id) {
        String query = "select * from paintings where painting_id="+painting_id;
        return jt.queryForObject(query, new RowMapper<Paintings>() {
            @Override
            public Paintings mapRow(ResultSet row, int i) throws SQLException {
                Paintings u = new Paintings();
                u.setPainting_id(row.getInt("Painting_id"));
                u.setPrice(row.getDouble("price"));
                u.setName(row.getString("name"));
                u.setIs_framed(row.getInt("is_framed"));
                u.setImage_path(row.getString("image_path"));
                return u;
            }
        });
    }

    public Artist getArtistByPaintingID(int painting_id){
        String query= "select artist_id from paintings where painting_id=?";
        Integer id= jt.queryForObject(query, new Object[]{painting_id}, Integer.class);
        query= "select * from artist where artist_id="+id;
        return jt.queryForObject(query, new RowMapper<Artist>() {
            @Override
            public Artist mapRow(ResultSet row, int i) throws SQLException {
                Artist u = new Artist();
                u.setArtist_id(row.getInt("artist_id"));
                u.setFirst_name(row.getString("first_name"));
                u.setLast_name(row.getString("last_name"));
                return u;
            }
        });
    }

    public Paintings getFramedPaintingByID(int painting_id) {
        String query = "select * from paintings where is_framed='True' and painting_id="+painting_id;
        return jt.queryForObject(query, new RowMapper<Paintings>() {
            @Override
            public Paintings mapRow(ResultSet row, int i) throws SQLException {
                Paintings g = new Paintings();
                g.setPainting_id(row.getInt("painting_id"));
                g.setName(row.getString("name"));
                return g;
            }
        });
    }

    public void savePainting(String name, int is_framed, double length, double breadth, double price, String first_name, String last_name, String email_id, String painting_style, int experience, String license, int gallery_id, String image_path){
        //d.getName(), d.getIs_framed(), d.getLength(), d.getBreadth(),d.getPrice(), 
        //a.getFirst_name(), a.getLast_name(),a.getEmail_id(), a.getPainting_style(), a.getExperience(), a.getLicense())
        String sql ="select count(*) from artist where first_name=? and last_name=? and email_id=? and painting_style=? and experience=? and license=?";
        int cnt = jt.queryForObject(sql, Integer.class, first_name, last_name, email_id, painting_style, experience, license);
        System.out.println("cnt: ");
        System.out.print(cnt);
        if(cnt == 0) {
            sql= "insert into artist(first_name, last_name, email_id, painting_style, experience, license ) values( ?,?,?,?,?,?)";
            jt.update(sql,first_name, last_name, email_id, painting_style, experience, license );
        }
        sql= "select artist_id from artist where first_name=? and last_name=? and email_id=? and painting_style=? and experience=? and license=?";
        int id= jt.queryForObject(sql, new Object[]{first_name, last_name, email_id, painting_style, experience, license}, Integer.class);
        String artist_name= first_name+last_name;
        String query = "insert into paintings(name, is_framed,artist_id, length, breadth, price,image_path) values (?,?,?,?,?,?,?)";
        jt.update(query, name, is_framed,id, length, breadth, price, image_path);
        query= "select painting_id from paintings where name=? and is_framed=? and artist_id=? and length=? and breadth=? and price=?";
        int pid= jt.queryForObject(query, new Object[]{name,is_framed,id, length, breadth, price}, Integer.class);
        sql= "insert into galleryInventory(painting_id, gallery_id, status) values (?,?,?)";
        jt.update(sql,pid, gallery_id, "active");
    }

    public void removePainting(int painting_id){
        String sql= "delete from galleryInventory where painting_id=?";
        jt.update(sql, painting_id);
        sql= "delete from paintings where painting_id=?";
        jt.update(sql, painting_id);
    }
    

}