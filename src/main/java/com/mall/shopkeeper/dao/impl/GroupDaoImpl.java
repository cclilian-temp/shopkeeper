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

import com.mall.shopkeeper.dao.GroupDao;
import com.mall.shopkeeper.dao.model.Group;

public class GroupDaoImpl extends JdbcDaoSupport implements GroupDao {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public void create(final Group group) {
        final Date now = new Date();
        String sql = "insert into `group` (`id`, `model`, `price`, `ct`, `ut`)"
                + " values (?, ?, ?, ?,?)";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, group.getId());
                ps.setString(2, group.getModel());
                ps.setDouble(3, group.getPrice());
                ps.setDate(4, new java.sql.Date(now.getTime()));
                ps.setDate(5, new java.sql.Date(now.getTime()));
            }
        });
    }

    @Override
    public void update(final Group group) {
        final Date now = new Date();
        final String model = group.getModel();
        String sql = "update `group` set `price` = ?, `ut` = ? "
                + " where model = " + "'" + model + "'";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setDouble(1, group.getPrice());
                ps.setDate(2, new java.sql.Date(now.getTime()));
            }
        });
    }

    @Override
    public List<Group> getGroups(String model) {
        StringBuilder sb = new StringBuilder("select `id`, `model`, `price`, `ct`, `ut` from `group`");
        if(StringUtils.isNotBlank(model)) {
            sb.append(" where model = " + "'" + model + "'");
        }
        String sql = sb.toString();
        logger.debug(sql);
        return super.getJdbcTemplate().query(sql, new RowMapper<Group>(){
            @Override
            public Group mapRow(ResultSet rs, int num) throws SQLException {
                Group group = new Group();
                group.setId(rs.getString("id"));
                group.setModel(rs.getString("model"));
                group.setPrice(rs.getDouble("price"));
                group.setUt(rs.getDate("ut"));
                group.setCt(rs.getDate("ct"));
                return group;
            }
        });
    }

    @Override
    public void remove(String model) {
        String sql = "delete from `group` where `model` = " + "'" + model + "'";
        super.getJdbcTemplate().execute(sql);
    }

    @Override
    public List<String> getModels() {
        String sql = "select `model` from `group`";
        return super.getJdbcTemplate().query(sql, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet rs, int num) throws SQLException {      
                return rs.getString("model");
            }});
    }

    @Override
    public List<Group> getGroups(List<String> models) {
        StringBuilder sb = new StringBuilder("select `id`, `model`, `price` from `group`");
        if(null != models && models.size() > 0) {
            sb.append("where `model` in (");
            for (String model : models){
                sb.append("'" + model + "',");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(") ");
        }
        return super.getJdbcTemplate().query(sb.toString(), new RowMapper<Group>(){
            @Override
            public Group mapRow(ResultSet rs, int num) throws SQLException {
                Group group = new Group();
                group.setId(rs.getString("id"));
                group.setModel(rs.getString("model"));
                group.setPrice(rs.getDouble("price"));
                return group;
            }
        });
    }

}
