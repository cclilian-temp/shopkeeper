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

import com.mall.shopkeeper.dao.TypeDao;

public class TypeDaoImpl extends JdbcDaoSupport implements TypeDao {

protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public List<String> getTypes() {
        String sql = "select `name` from `type`";
        return super.getJdbcTemplate().query(sql, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet rs, int num) throws SQLException {
                return rs.getString("name");
            }     
        });
    }

    @Override
    public void create(final String name) {
        final Date now = new Date();
        String sql = "insert into `type` (`name`, `ct`, `ut`) values(?, ?, ?)";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, name);
                ps.setDate(2, new java.sql.Date(now.getTime()));
                ps.setDate(3, new java.sql.Date(now.getTime()));
            }
        });
    }

    @Override
    public void remove(String name) {
        String sql = "delete from `type` where name = " + "'" + name + "'";
        super.getJdbcTemplate().execute(sql);
    }

}
