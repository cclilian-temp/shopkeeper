package com.mall.shopkeeper.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.support.TransactionTemplate;

import com.mall.shopkeeper.dao.OrderDao;
import com.mall.shopkeeper.dao.model.Order;
import com.mall.shopkeeper.dao.model.Production;

public class OrderDaoImpl extends JdbcDaoSupport implements OrderDao {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public void create(final Order order) {
        final Date now = new Date();
        String sql = "insert into `order`(`id`, `storeName`, `clientName`, `store`, `client`, `price`, `productions`, `groups`, `status`, `message`, `ct`, `ut`) "
                + " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, order.getId());
                ps.setString(2, order.getStoreName());
                ps.setString(3, order.getClientName());
                ps.setString(4, order.getStore());
                ps.setString(5, order.getClient());
                ps.setString(6, order.getPrice());
                ps.setString(7, order.getProductions());
                ps.setString(8, order.getGroups());
                ps.setString(9, order.getStatus());
                ps.setString(10, order.getMessage());
                ps.setDate(11, new java.sql.Date(now.getTime()));
                ps.setDate(12, new java.sql.Date(now.getTime()));
            }
        });
    }

    @Override
    public List<Order> getOrders(String id, String storeName, String clientName, Date frDate, Date toDate) {
        StringBuilder sb = new StringBuilder("select `id`, `storeName`, `clientName`, `store`, `client`, `price`, "
                + "`productions`, `groups`, `status`, `message`, `ct`, `ut` from `order`");
        
        if((null != id) || (null != storeName) || (null != clientName) || (null != frDate) || (null != toDate)){
            sb.append(" where");
            if(null != id){
                sb.append(" and `id` =" + "'" + id + "'");
            }
            if(null != storeName){
                sb.append(" and `storeName` =" + "'" + storeName + "'");
            }
            if(null != clientName){
                sb.append(" and `clientName` =" + "'" + clientName + "'");
            }
            if(null != frDate){
                sb.append(" and `frDate` >=" + "'" + frDate + "'");
            }
            if(null != toDate){
                sb.append(" and `toDate` <=" + "'" + toDate + "'");
            }
        }
        String sql = sb.toString();
        if(sql.contains("where")){
            sql = sql.replaceAll("where and", "where");
        }
        return super.getJdbcTemplate().query(sql, new RowMapper<Order>(){
            @Override
            public Order mapRow(ResultSet rs, int num) throws SQLException {
                Order order = new Order();
                order.setId(rs.getString("id"));
                order.setStoreName(rs.getString("storeName"));
                order.setClientName(rs.getString("clientName"));
                order.setStore(rs.getString("store"));
                order.setClient(rs.getString("client"));
                order.setPrice(rs.getString("price"));
                order.setProductions(rs.getString("productions"));
                order.setGroups(rs.getString("groups"));
                order.setStatus(rs.getString("status"));
                order.setMessage(rs.getString("message"));
                order.setUt(rs.getDate("ut"));
                order.setCt(rs.getDate("ct"));
                return order;
            }});
    }

    @Override
    public void update(final Order order) {
        final Date now = new Date();
        String id = order.getId();
        String sql = "update `order` set `storeName` = ?, `clientName` = ?, `store` = ?, `client` = ?, `price` = ?, `productions` = ?,"
                + "`groups` = ?, `status` = ?, `message` = ?, `ut` = ? where `id`=" + "'" + id + "'";
        
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, order.getStoreName());
                ps.setString(2, order.getClientName());
                ps.setString(3, order.getStore());
                ps.setString(4, order.getClient());
                ps.setString(5, order.getPrice());
                ps.setString(6, order.getProductions());
                ps.setString(7, order.getGroups());
                ps.setString(8, order.getStatus());
                ps.setString(9, order.getMessage());
                ps.setDate(10, new java.sql.Date(now.getTime()));
            }
        });
    }

}
