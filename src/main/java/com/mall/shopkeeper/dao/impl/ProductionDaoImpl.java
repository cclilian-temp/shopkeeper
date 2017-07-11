package com.mall.shopkeeper.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.support.TransactionTemplate;

import com.mall.shopkeeper.dao.ProductionDao;
import com.mall.shopkeeper.dao.model.Production;

public class ProductionDaoImpl extends JdbcDaoSupport implements ProductionDao {

    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public List<Production> getProductions(String model, String type) {
        StringBuilder sb = new StringBuilder();
        sb.append("select `id`, `model`, `type`, `price`, `lowestDiscount`, `specialDiscount`, `ct`, `ut` from `production`");
        if(StringUtils.isNotBlank(model) || StringUtils.isNotBlank(type)) {
            sb.append(" where ");
        }
        if(StringUtils.isNotBlank(model)){
            sb.append(" `model` = " + "'" + model + "'");
            if(StringUtils.isNotBlank(type)) {
                sb.append(" and type = " + "'" + type + "'");
            }
        } else if(StringUtils.isNotBlank(type)) {
            sb.append(" `type` = " + "'" + type + "'");
        }
        String sql = sb.toString();
        logger.debug(sql);
        return super.getJdbcTemplate().query(sql, new RowMapper<Production>(){
            @Override
            public Production mapRow(ResultSet rs, int num) throws SQLException {
                Production production = new Production();
                production.setId(rs.getString("id"));
                production.setModel(rs.getString("model"));
                production.setType(rs.getString("type"));
                production.setPrice(rs.getDouble("price"));
                production.setLowestDiscount(rs.getDouble("lowestDiscount"));
                production.setSpecialDiscount(rs.getDouble("specialDiscount"));
                production.setCt(rs.getDate("ct"));
                production.setUt(rs.getDate("ut"));
                return production;
            }});
    }

    @Override
    public void create(final Production production) throws Exception {
        final Date now = new Date();
        String sql = "insert into `production`(`id`, `model`, `type`, `price`, `lowestDiscount`, `specialDiscount`,"
                + " `ct`, `ut`) values(?, ?, ?, ?, ?, ?, ?, ?)";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, production.getId());
                ps.setString(2, production.getModel());
                ps.setString(3, production.getType());
                ps.setDouble(4, production.getPrice());
                ps.setDouble(5, production.getLowestDiscount());
                ps.setDouble(6, production.getSpecialDiscount());
                ps.setDate(7, new java.sql.Date(now.getTime()));
                ps.setDate(8, new java.sql.Date(now.getTime()));
            }
        });
    }
    
    @Override
    public void update(final Production production) throws Exception {
        final Date now = new Date();
        String model = production.getModel();
        String sql = "update `production` set `type` = ?, `price` = ?, `lowestDiscount` = ?, `specialDiscount` = ?,"
                + " ut = ? where model = " + "'" + model + "'";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, production.getType());
                ps.setDouble(2, production.getPrice());
                ps.setDouble(3, production.getLowestDiscount());
                ps.setDouble(4, production.getSpecialDiscount());
                ps.setDate(5, new java.sql.Date(now.getTime()));
            } 
        });
    }
    
    @Override
    public void delete(String model) throws Exception {
        String sql = "delete from `production` where `model` =" + "'" + model + "'";
        super.getJdbcTemplate().execute(sql);
    }

    @Override
    public List<String> getModels() {
        String sql = "select `model` from `production`";
        return super.getJdbcTemplate().query(sql, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet rs, int num) throws SQLException {      
                return rs.getString("model");
            }});
    }

    @Override
    public List<Production> getProductions(List<String> models) {
        StringBuilder sb = new StringBuilder("select `id`, `model`, `type`, `price`, `lowestDiscount`, `specialDiscount`, `ct`, `ut` from `production`");
        if(null != models && models.size() > 0) {
            sb.append("where `model` in (");
            for (String model : models){
                sb.append("'" + model + "',");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") ");
        }
        String sql = sb.toString();
        logger.debug(sql);
        return super.getJdbcTemplate().query(sql, new RowMapper<Production>(){
            @Override
            public Production mapRow(ResultSet rs, int num) throws SQLException {
                Production production = new Production();
                production.setId(rs.getString("id"));
                production.setModel(rs.getString("model"));
                production.setType(rs.getString("type"));
                production.setPrice(rs.getDouble("price"));
                production.setLowestDiscount(rs.getDouble("lowestDiscount"));
                production.setSpecialDiscount(rs.getDouble("specialDiscount"));
                production.setCt(rs.getDate("ct"));
                production.setUt(rs.getDate("ut"));
                return production;
            }});
    }
}
