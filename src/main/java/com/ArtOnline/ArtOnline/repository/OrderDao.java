package com.ArtOnline.ArtOnline.repository;

import com.ArtOnline.ArtOnline.model.PaintingsOrdered;
import com.ArtOnline.ArtOnline.model.Orders;
import com.ArtOnline.ArtOnline.model.Paintings;
import com.ArtOnline.ArtOnline.model.Reviews;

import org.hibernate.id.IntegralDataTypeHolder;
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
public class OrderDao {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    UserDao userDao;
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat tf = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jt = jdbcTemplate;
    }

    public Orders getOrderByID(int order_id){
        String query = "select * from orders where order_id="+order_id;
        Orders p = jt.queryForObject(query, new RowMapper<Orders>() {
            @Override
            public Orders mapRow(ResultSet r, int i) throws SQLException {
                Orders o = new Orders();
                o.setOrder_id(r.getInt("order_id"));
                o.setOrder_date(df.format(r.getDate("order_date")));
                o.setOrder_time(tf.format(r.getTime("order_time")));
                o.setBill_amount(r.getBigDecimal("bill_amount"));
                return o;
            }
        });
        return p;
    }

    public List<PaintingsOrdered> getOrderInfo(int order_id){
        String sql= "select * from paintingsOrdered where order_id="+order_id;
        return jt.query(sql, new RowMapper<PaintingsOrdered>() {
            @Override
            public PaintingsOrdered mapRow(ResultSet row, int i) throws SQLException {
                PaintingsOrdered r = new PaintingsOrdered();
                r.setPainting_status(row.getString("painting_status"));
                r.setPainting_id(row.getInt("painting_id"));
                
                return r;
            }
        });
    }

    

    public List<Reviews> getReviewsForGalleryID(int gallery_id){
        String sql= "select * from galleryinventory where gallery_id=? and status='sold'";
        List<Integer> l= new ArrayList<Integer> ();
        List<Map<String,Object>> rs = jt.queryForList(sql, gallery_id);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        List<Reviews> res = new ArrayList<Reviews> ();
        for(int i=0;i<l.size();i++)
        {
            int painting_id= l.get(i);
            sql= "select count(review_id) from reviews where painting_id=?";
            int cnt= jt.queryForObject(sql, new Object[]{painting_id}, Integer.class);
            if(cnt==0)
              continue;
            String query = "select * from reviews where painting_id="+painting_id;
            Reviews p=  jt.queryForObject(query, new RowMapper<Reviews>() {
                @Override
                public Reviews mapRow(ResultSet row, int i) throws SQLException {
                    Reviews r = new Reviews();
                    r.setComment(row.getString("comment"));
                    r.setRating(row.getBigDecimal("rating"));
                    r.setPainting_id(row.getInt("painting_id"));
                    return r;
                }
            });
            res.add(p);
        }
        return res;
    }

    public List<PaintingsOrdered> getOrdersByGalleryID(int gallery_id){
        String  query= "select * from galleryInventory where status='sold' and gallery_id="+ gallery_id;
        List<Integer> l= new ArrayList<Integer> ();
        List<Map<String,Object>> rs = jt.queryForList(query);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        List<PaintingsOrdered> o= new ArrayList<PaintingsOrdered> ();
        for(Integer id:l)
        {
            String sql= "select count(order_id) from paintingsordered where painting_id=?";
            int cnt= jt.queryForObject(sql, new Object[]{id}, Integer.class);
            if(cnt==0)
              continue;
             query = "select * from PaintingsOrdered where painting_id="+id;
            PaintingsOrdered p=  jt.queryForObject(query, new RowMapper<PaintingsOrdered>() {
                @Override
                public PaintingsOrdered mapRow(ResultSet row, int i) throws SQLException {
                    PaintingsOrdered g = new PaintingsOrdered();
                    g.setOrder_id(row.getInt("order_id"));
                    g.setPainting_id(row.getInt("painting_id"));
                    g.setUser_id(row.getInt("user_id"));
                    g.setPainting_status(row.getString("painting_status"));
                    return g;
                }
            });
            o.add(p);
        }
        return o;
    }

    public Integer save(String order_date, String order_time, BigDecimal bill_amount){

        Date now = new Date();
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String mysqlDateString = formatter.format(now);

        String query = "insert into orders(order_date, order_time, bill_amount) values (?,?,?)";
        jt.update(query, mysqlDateString, order_time, bill_amount);

        query= "select order_id from orders where order_time=? and order_date=? and bill_amount=? ";
        Integer order_id= jt.queryForObject(query, new Object[]{order_time, mysqlDateString, bill_amount}, Integer.class);
        return order_id;
    }

    

    
    public void removeOrder(int order_id){
        String query = "delete from orders where order_id="+order_id;
        jt.update(query);
    }

    public void changeOrderStatus(int painting_id, String status){

        String query = "update paintingsordered set painting_status=? where painting_id="+painting_id;
        jt.update(query, status);
       
        if(status=="delivered")
        {
            query= "update galleryInventory set status='sold' where painting_id=?";
            jt.update(query, painting_id);
        }
        else if(status=="cancelled")
        {
            query= "update galleryInventory set status='active' where painting_id=?";
            jt.update(query, painting_id);
            query= "delete from paintingsOrdered where painting_id=?";
            jt.update(query, painting_id);
        }
    }


    
    public void cartAdd(int painting_id, String name){
        String sql= "select user_id from user where email_id=?";
        int user_id= jt.queryForObject(sql,new Object[]{name}, Integer.class);
        
        sql= "insert into paintingsOrdered(user_id,painting_id,painting_status) values(?,?,?)";
        jt.update(sql, user_id, painting_id,"pending");
    }

    public void placeOrder(String email){

        BigDecimal bill_amount= userDao.getCart(email);

        Date now = new Date();
        int order_id= save(df.format(now),tf.format(now), bill_amount);

        String sql= "select user_id from user where email_id=?";
        int user_id= jt.queryForObject(sql, new Object[]{email}, Integer.class);

        List<Integer> l= new ArrayList<Integer>();
        sql= "select * from paintingsordered where user_id=? and painting_status='pending'";
        List<Map<String,Object>> rs = jt.queryForList(sql, user_id);
        for( Map<String,Object> r:rs ) {
            Integer s = (Integer) r.get("painting_id");
            l.add(s);
        }
        for(int i=0;i<l.size();i++){
            int id= l.get(i);
            sql= "update paintingsordered set order_id=? where painting_id=? and user_id=?";
            jt.update(sql, order_id, id, user_id);
            sql= "update paintingsordered set painting_status=? where painting_id=? and user_id=?";
            jt.update(sql,"ordered",id,user_id);
            sql= "delete from paintingsordered where painting_id=? and not user_id=?";
            jt.update(sql, id, user_id);
            sql= "update galleryinventory set status='sold' where painting_id=?";
            jt.update(sql, id);
        }  
    }

    public void removePaintingFromCart(int painting_id, int user_id){
        String sql= "delete from paintingsordered where painting_id=?";
        jt.update(sql, painting_id);
    }
}
