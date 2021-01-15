package com.ArtOnline.ArtOnline.repository;

import com.ArtOnline.ArtOnline.model.Address;
import com.ArtOnline.ArtOnline.model.UserAddress;
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
public class AddressDao {

    @Autowired
    JdbcTemplate jt;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jt = jdbcTemplate;
    }

    public int saveAddress(String plot_no, String street, String city, String state) {
        String query = "insert into address(plot_no, street, city,  state) values (?,?,?,?)";
        jt.update(query, plot_no, street, city, state);

        String sql = "select address_id from address where plot_no=? and street=? and city=? and state=?";
        return jt.queryForObject(sql, new Object[]{plot_no, street, city, state}, Integer.class);
    }
    
    public void saveAddressOfUser(int address_id, String address_type, int user_id){
        String query = "insert into addressOfUser() values (?,?,?)";
        jt.update(query, address_type,user_id,address_id);
    }

    public List<UserAddress> getAddressesByUserID(int user_id){
        String query = "select * from addressOfUser as au, address as a where a.address_id=au.address_id and au.user_id="+user_id;
        List<Map<String,Object>> rs = jt.queryForList(query);
        List<UserAddress> res = new ArrayList<UserAddress>();
        for ( Map<String,Object> r:rs) {
            UserAddress u = new UserAddress();
            u.setAddress_type((String) r.get("address_type"));
            Address a = new Address();
            a.setAddress_id((int) r.get("address_id"));
            a.setPlot_no((String) r.get("plot_no"));
            a.setStreet((String) r.get("street"));
            a.setCity((String) r.get("city"));
            a.setState((String) r.get("state"));
            u.setAddress(a);
            res.add(u);
        }
        return res;
    }

    public Address getAddressByID(int address_id){
        String query = "select * from address where address_id="+address_id;
        return jt.queryForObject(query, new RowMapper<Address>() {
            @Override
            public Address mapRow(ResultSet r, int i) throws SQLException {
                Address a = new Address();
                a.setAddress_id(r.getInt("address_id"));
                a.setPlot_no(r.getString("plot_no"));
                a.setStreet(r.getString("street"));
                a.setCity(r.getString("city"));
                a.setState(r.getString("state"));
                return a;
            }
        });
    }
}
