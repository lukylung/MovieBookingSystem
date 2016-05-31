package myservice.info.DAO.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import myservice.info.DAO.AccountDAO;
import myservice.info.Model.*;

@Repository
public class AccountDaoImpl implements AccountDAO {
	ApplicationContext  ctx = new FileSystemXmlApplicationContext("/src/applicationContext.xml");
    JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");
    List<Map<String, Object>> rows;
	@Override
	public List<Map<String, Object>> getAcoountByUserName(String username) {
		jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(" +
        		"username VARCHAR(255), password VARCHAR(255), phonenum VARCHAR(255))");
		rows=jdbcTemplate.query("select * from users where username=?",new Object[]{username},new UserRowMapper());
        return  rows;
	}
	@Override
	public void setAcoountByUser(User user) {
		jdbcTemplate.update("insert into users(username,password,phonenum) values(?,?,?)",   
                new Object[]{user.getusername(),user.getpassword(),user.getphonenum()});
	}
	
	public class UserRowMapper implements RowMapper {
    	public Object mapRow(ResultSet rs, int value) throws SQLException { 
    	User user = new User (); 
    	user.setusername(rs.getString("username"));
    	user.setpassword(rs.getString("password"));
    	user.setphonenum(rs.getString("phonenum"));
    	return user; 
    	}
    }
}