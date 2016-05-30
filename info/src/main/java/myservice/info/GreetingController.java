package myservice.info;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;   
import java.sql.ResultSet;
import java.sql.SQLException;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();
  
    //@Autowired
    ApplicationContext  ctx = new FileSystemXmlApplicationContext("/src/applicationContext.xml");
    JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");;
    @RequestMapping(value ="/register",method = RequestMethod.POST)
    public void greeting(HttpServletRequest request) {
    	    try{
    	    	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    	    	String line = null;
    	        StringBuilder sb = new StringBuilder();
    	        while((line = br.readLine())!=null){
    	            sb.append(line);
    	        }
    	        String reqBody = sb.toString();
    	        URLDecoder.decode(reqBody, "UTF-8");
    	        //System.out.println(reqBody);
    	        User user=(User)JSONObject.toBean(JSONObject.fromObject(reqBody),User.class);
    	        System.out.println(user.getusername());
    	        System.out.println(user.getpassword());
    	        System.out.println(user.getphonenum());
    	        //jdbcTemplate.execute("DROP TABLE users IF EXISTS");
    	        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users(" +
    	        		"username VARCHAR(255), password VARCHAR(255), phonenum VARCHAR(255))");
    	        jdbcTemplate.update("insert into users(username,password,phonenum) values(?,?,?)",   
    	                new Object[]{user.getusername(),user.getpassword(),user.getphonenum()});
    	        //jdbcTemplate.query("select * from users where username = ?",new Object[] { "aaaaaa" },new UserRowMapper());
    	        List<Map<String, Object>> rows = jdbcTemplate.queryForList("select * from users");
    	        for(int i=0;i<rows.size();i++){
    	        	Map  userMap=rows.get(i);
    	        	System.out.println(userMap.get("username"));
    	        	System.out.println(userMap.get("password"));
    	        	System.out.println(userMap.get("phonenum"));
    	        }
    		} catch(IOException e) {
    			e.printStackTrace();
    		}
        	System.out.println(request.getCharacterEncoding());
        	//String username = request.getParameter("NAME");
        	//System.out.println(username);
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



