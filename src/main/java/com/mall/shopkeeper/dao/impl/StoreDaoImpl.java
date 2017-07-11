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

import com.mall.shopkeeper.dao.StoreDao;
import com.mall.shopkeeper.dao.model.Store;

public class StoreDaoImpl extends JdbcDaoSupport implements StoreDao {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public void create(final Store store) {
        final Date now = new Date();
        String sql = "insert into `store`(`id`, `name`, `managerId`, `address`, `tel`, `fax`, `ct`, `ut`) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?)";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, store.getId());
                ps.setString(2, store.getName());
                ps.setString(3, store.getManagerId());
                ps.setString(4, store.getAddress());
                ps.setString(5, store.getTel());
                ps.setString(6, store.getFax());
                ps.setDate(7, new java.sql.Date(now.getTime()));
                ps.setDate(8, new java.sql.Date(now.getTime()));
            }
        });
    }

    @Override
    public void update(final Store store) {
        final Date now = new Date();
        String id = store.getId();
        String sql = "update `store` set `name` = ?, `managerId` = ?, `address` = ?, `tel` = ?, `fax` = ?, `ut` = ? "
                + "where `id` = " + "'" + id + "'";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, store.getName());
                ps.setString(2, store.getManagerId());
                ps.setString(3, store.getAddress());
                ps.setString(4, store.getTel());
                ps.setString(5, store.getFax());
                ps.setDate(6, new java.sql.Date(now.getTime()));
            }
        });
    }

    @Override
    public void remove(String id) {
        String sql = "delete from `store` where `id` = " + "'" + id + "'";
        super.getJdbcTemplate().execute(sql);
    }

    @Override
    public List<Store> getStores(String id, String name, String tel) {
        StringBuilder sb = new StringBuilder();
        sb.append("select `id`, `name`, `managerId`, `address`, `tel`, `fax`, `ct`, `ut` from `store` ");
        if(StringUtils.isNotBlank(id) || StringUtils.isNotBlank(name) || StringUtils.isNotBlank(tel)) {
            sb.append(" where ");
            if(StringUtils.isNotBlank(id)) {
                sb.append(" `id` = " + "'" + id + "'");
                if(StringUtils.isNotBlank(name)){
                    sb.append(" and `name` =" + "'" + name + "'");
                }
                if(StringUtils.isNotBlank(tel)) {
                    sb.append(" and `tel` =" + "'" + tel + "'");
                }
            }else if(StringUtils.isNotBlank(name)){
                sb.append(" `name` =" + "'" + name + "'");
                if(StringUtils.isNotBlank(tel)) {
                    sb.append(" and `tel` =" + "'" + tel + "'");
                }
            }else if(StringUtils.isNotBlank(tel)) {
                sb.append("`tel` =" + "'" + tel + "'");
            }
        }
        String sql = sb.toString();
        return super.getJdbcTemplate().query(sql, new RowMapper<Store>(){
            @Override
            public Store mapRow(ResultSet rs, int num) throws SQLException {
                Store store = new Store();
                store.setId(rs.getString("id"));
                store.setName(rs.getString("name"));
                store.setManagerId(rs.getString("managerId"));
                store.setAddress(rs.getString("address"));
                store.setTel(rs.getString("tel"));
                store.setFax(rs.getString("fax"));
                store.setCt(rs.getDate("ct"));
                store.setUt(rs.getDate("ut"));
                return store;
            }
        });
    }

    @Override
    public List<String> getStoreIds() {
        String sql = "select `id` from `store`";
        return super.getJdbcTemplate().query(sql, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet rs, int num) throws SQLException {
                return rs.getString("id");
            }
        });
    }

}
