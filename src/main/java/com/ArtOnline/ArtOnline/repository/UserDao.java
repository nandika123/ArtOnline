package com.ArtOnline.ArtOnline.repository;

import com.ArtOnline.ArtOnline.model.Orders;
import com.ArtOnline.ArtOnline.model.Paintings;
import com.ArtOnline.ArtOnline.model.Gallery;
import com.ArtOnline.ArtOnline.model.User;


import org.hibernate.boot.model.relational.Database;
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
import java.util.Set;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
@Transactional
@Repository
public class UserDao {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jt = jdbcTemplate;
    }

   
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat tf = new SimpleDateFormat("HH:mm:ss");

    public boolean userExists(String email_id) {
        String query = "select count(*) from user where email_id=?";
        int cnt = jt.queryForObject(query, Integer.class, email_id);
        if(cnt == 0) return false;
        else return true;
    }

    public List<String> getPhoneByID(int user_id){
        String query = "select phone_no from userPhone where user_id="+user_id;
        List<String> res = new ArrayList<String>();
        List<Map<String,Object>> rs = jt.queryForList(query);
        for( Map<String,Object> r:rs ) {
            String s = (String) r.get("phone_no");
            res.add(s);
        }
        return res;
    }

    public User findByUsername(String email_id) {
        String query = "select * from user where email_id='"+email_id+"'";
        return jt.queryForObject(query, new RowMapper<User>() {

            public User mapRow(ResultSet row, int i) throws SQLException {
                User u = new User();
                u.setUser_id(row.getInt("user_id"));
                u.setEmail_id(row.getString("email_id"));
                u.setFirst_name(row.getString("first_name"));
                u.setLast_name(row.getString("last_name"));
                u.setEmail_id(row.getString("email_id"));
                u.setPassword(row.getString("password"));
                u.setRole(row.getString("role"));
                return u;
            }
        });
    }

    public void save(String f_name,String l_name,String email_id,String password, String role) {
        String query = "insert into user(first_name, last_name, email_id, password,role) values (?,?,?,?,?)";
        jt.update(query, f_name, l_name, email_id, password, role);
        
    }

    public void saveUserPhone(String phone_no, Integer user_id) {
        String query = "insert into userPhone values (?,?)";
        jt.update(query, phone_no, user_id);
    }

    

    public void saveUserReview(String comment, BigDecimal rating,  Integer painting_id) {
        String query = "insert into reviews(comment, rating, painting_id) values (?,?,?)";
        jt.update(query, comment, rating, painting_id);
        query= "update paintingsOrdered set painting_status= 'delivered and reviewed' where painting_id=?";
        jt.update(query, painting_id);
    }

    public int getUserIDByEmailID(String email_id) {
        String query = "select user_id from user where email_id=?";
        return jt.queryForObject(query, Integer.class, email_id);
    }

    public List<Orders> getOrdersByUserID(int user_id){
        String sql= "select * from paintingsordered where not painting_status='pending' and user_id=?";
        Set<Integer>l = new HashSet<Integer>(); 
        List<Map<String,Object>> rs = jt.queryForList(sql, user_id);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("order_id");
            l.add(s);
        }
        List<Orders> o= new ArrayList<Orders> ();
        for(Integer id:l)
        {
            int order_id= id;
            String query = "select * from orders where order_id="+order_id;
            Orders p=  jt.queryForObject(query, new RowMapper<Orders>() {
                @Override
                public Orders mapRow(ResultSet row, int i) throws SQLException {
                    Orders g = new Orders();
                    g.setOrder_id(row.getInt("order_id"));
                    g.setBill_amount(row.getBigDecimal("bill_amount"));
                    g.setOrder_date(row.getString("order_date"));
                    g.setOrder_time(row.getString("order_time"));
                    return g;
                }
            });
            o.add(p);
        }
        return o;
    }

    public void changeRoleToGallery(int user_id){
        String query = "update user set role='gallery' where user_id="+user_id;
        jt.update(query);
    }

    public List<Gallery> getGalleriesOfUser(int user_id) {
        String query = "select s.gallery_name, s.gallery_id from gallery s,owner o where s.gallery_id=o.gallery_id and o.user_id="+user_id;
        List<Gallery> res = new ArrayList<Gallery>();
        List<Map<String,Object>> rs = jt.queryForList(query);
        for ( Map<String, Object> r:rs) {
            Gallery s = new Gallery();
            s.setGallery_name((String) r.get("gallery_name"));
            s.setGallery_id((int) r.get("gallery_id"));
            res.add(s);
        }
        return res;
    }
    //returns cart total 
    public BigDecimal getCart(String email){
        String sql= "select user_id from user where email_id=?";
        int user_id= jt.queryForObject(sql, new Object[]{email}, Integer.class);
        sql= "select * from paintingsordered where user_id="+user_id+" and painting_status='pending'";
        List<Integer> l= new ArrayList<Integer>();
        List<Map<String,Object>> rs = jt.queryForList(sql);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        BigDecimal ans= BigDecimal.ZERO;
        for(int i=0;i<l.size();i++)
        {
            sql= "select price from paintings where painting_id=?";
            BigDecimal p= jt.queryForObject(sql, new Object[]{l.get(i)}, BigDecimal.class);
            ans= ans.add(p);
        }
        return ans;
    }
    //returns cart items
    public List<Paintings> getOrderItems(String email){
        String sql= "select user_id from user where email_id=?";
        int user_id= jt.queryForObject(sql, new Object[]{email}, Integer.class);
        
        List<Integer> l= new ArrayList<Integer>() ;
        String query = "select * from paintingsOrdered where user_id=? and painting_status='pending'";
        List<Map<String,Object>> rs = jt.queryForList(query, user_id);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        List<Paintings> res = new ArrayList<Paintings>();
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
            res.add(p);
        }
        return res;
    }
    public List<User> listAllUsers() {
        String query = "select * from User where not role='admin'";
        return jt.query(query, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet row, int i) throws SQLException {
                User g = new User();
                g.setUser_id(row.getInt("User_id"));
                g.setFirst_name(row.getString("first_name"));
                g.setLast_name(row.getString("last_name"));
                g.setEmail_id(row.getString("email_id"));
                g.setRole(row.getString("role"));
                return g;
            }
        });
    }
    
    
}
