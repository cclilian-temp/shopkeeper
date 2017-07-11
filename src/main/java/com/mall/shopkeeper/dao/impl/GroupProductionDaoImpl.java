package com.mall.shopkeeper.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.transaction.support.TransactionTemplate;

import com.mall.shopkeeper.dao.GroupProductionDao;
import com.mall.shopkeeper.dao.model.mapping.GroupProduction;

public class GroupProductionDaoImpl extends JdbcDaoSupport  implements GroupProductionDao {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    protected TransactionTemplate transactionTemplate;

    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }
    
    @Override
    public List<GroupProduction> getProductionsByGroup(String groupModel) {
        String sql = "select `id`, `groupModel`, `productionModel`, `num` from `groupProduction`"
                + " where `groupModel` = " + "'" + groupModel + "'";     
        return super.getJdbcTemplate().query(sql, new RowMapper<GroupProduction>(){
            @Override
            public GroupProduction mapRow(ResultSet rs, int num) throws SQLException {
                GroupProduction groupProduction = new GroupProduction();
                groupProduction.setId(rs.getString("id"));
                groupProduction.setGroupModel(rs.getString("groupModel"));
                groupProduction.setProductionModel(rs.getString("productionModel"));
                groupProduction.setNum(rs.getInt("num"));
                return groupProduction;
            }
        });
    }
    
    @Override
    public List<GroupProduction> getProductionsByProduction(String productionModel) {
        String sql = "select `id`, `groupModel`, `productionModel`, `num` from `groupProduction`"
                + " where `productionModel` = " + "'" + productionModel + "'";     
        return super.getJdbcTemplate().query(sql, new RowMapper<GroupProduction>(){
            @Override
            public GroupProduction mapRow(ResultSet rs, int num) throws SQLException {
                GroupProduction groupProduction = new GroupProduction();
                groupProduction.setId(rs.getString("id"));
                groupProduction.setGroupModel(rs.getString("groupModel"));
                groupProduction.setProductionModel(rs.getString("productionModel"));
                groupProduction.setNum(rs.getInt("num"));
                return groupProduction;
            }
        });
    }

    @Override
    public void insert(final List<GroupProduction> groupProductions) {
        String sql = "insert into `groupProduction` (`id`,`groupModel`,`productionModel`,`num`) values(?,?,?,?)";
        super.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter(){
            @Override
            public int getBatchSize() {
                return groupProductions.size();
            }
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, groupProductions.get(i).getId());
                ps.setString(2, groupProductions.get(i).getGroupModel());
                ps.setString(3, groupProductions.get(i).getProductionModel());
                ps.setInt(4, groupProductions.get(i).getNum());
            }});
        
    }

    @Override
    public void remove(String groupModel, String productionModel) {
        String sql = "delete from `groupProduction` where `groupModel` =" + "'" + groupModel + "'" + " and `productionModel` =" + "'" + productionModel + "'";
        super.getJdbcTemplate().execute(sql);
    }

    @Override
    public void remove(String groupModel) {
        String sql = "delete from `groupProduction` where `groupModel` =" + "'" + groupModel + "'";
        super.getJdbcTemplate().execute(sql);
    }

}
