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

import com.mall.shopkeeper.dao.TypeDao;
import com.mall.shopkeeper.dao.model.Type;

public class TypeDaoImpl extends JdbcDaoSupport implements TypeDao {

protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public List<String> getTypeIds() {
        String sql = "select `id` from `type`";
        return super.getJdbcTemplate().query(sql, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet rs, int num) throws SQLException {
                return rs.getString("id");
            }     
        });
    }
    
    @Override
    public List<Type> getTypes(String name) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("select `id` , `name` , `ct`, `ut` from `type`");
        if(StringUtils.isNotBlank(name)) {
            sb.append(" where name = " + "'" + name + "'");
        }
    	String sql = sb.toString();
        return super.getJdbcTemplate().query(sql, new RowMapper<Type>(){
            @Override
            public Type mapRow(ResultSet rs, int num) throws SQLException {
            	Type type = new Type();
            	type.setId(rs.getString("id"));
            	type.setName(rs.getString("name"));
            	type.setCt(rs.getDate("ct"));
            	type.setUt(rs.getDate("ut"));
                return type;
            }
        });
    }

    @Override
    public void create(final Type type) {
    	final Date now = new Date();
        String sql = "insert into `type`(`id`, `name`, `ct`, `ut`) "
                + "values(?, ?, ?, ?)";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, type.getId());
                ps.setString(2, type.getName());
                ps.setDate(3, new java.sql.Date(now.getTime()));
                ps.setDate(4, new java.sql.Date(now.getTime()));
            }
        });
    }

    @Override
    public void remove(String id) {
        String sql = "delete from `type` where id = " + "'" + id + "'";
        super.getJdbcTemplate().execute(sql);
    }

	@Override
	public void update(final Type type) {
		final Date now = new Date();
        String id = type.getId();
        String sql = "update `type` set `name` = ?, `ut` = ? "
                + "where `id` = " + "'" + id + "'";
        super.getJdbcTemplate().update(sql, new PreparedStatementSetter(){
            @Override
            public void setValues(PreparedStatement ps) throws SQLException {
                ps.setString(1, type.getName());
                ps.setDate(2, new java.sql.Date(now.getTime()));
            }
        });
	}

	@Override
	public List<String> getTypeNames() {
		String sql = "select `name` from `type`";
        return super.getJdbcTemplate().query(sql, new RowMapper<String>(){
            @Override
            public String mapRow(ResultSet rs, int num) throws SQLException {
                return rs.getString("name");
            }     
        });
	}

}
