package myservice.info.Controller;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.util.concurrent.atomic.AtomicLong;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import myservice.info.Model.User;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;   
import java.sql.ResultSet;
import java.sql.SQLException;

import myservice.info.DAO.impl.*;
import myservice.info.DAO.AccountDAO;

@RestController
public class GreetingController {
    private final AtomicLong counter = new AtomicLong();
    private AccountDAO adi = new AccountDaoImpl();
    //@Autowired
    ApplicationContext  ctx = new FileSystemXmlApplicationContext("/src/applicationContext.xml");
    JdbcTemplate jdbcTemplate = (JdbcTemplate) ctx.getBean("jdbcTemplate");;
    @RequestMapping(value ="/register",method = RequestMethod.POST)
    public void register(HttpServletRequest request, HttpServletResponse response) {
    	String data;
    	    try{
    	    	BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
    	    	String line = null;
    	        StringBuilder sb = new StringBuilder();
    	        while((line = br.readLine())!=null){
    	            sb.append(line);
    	        }
    	        String reqBody = sb.toString();
    	        URLDecoder.decode(reqBody, "UTF-8");
    	        User user=(User)JSONObject.toBean(JSONObject.fromObject(reqBody),User.class);
    	        List<Map<String, Object>>rows=adi.getAcoountByUserName(user.getusername());
    	        if(rows.isEmpty()) {
    	        	//jdbcTemplate.update("insert into users(username,password,phonenum) values(?,?,?)",   new Object[]{user.getusername(),user.getpassword(),user.getphonenum()});
    	        	adi.setAcoountByUser(user);
    	        	data = "success";
    	        } else {
    	        	data = "fail";
    	        }
    	        response.setCharacterEncoding("UTF-8");
    	        response.setHeader("content-type", "text/html;charset=UTF-8");
    	        PrintWriter out = response.getWriter();
    	        out.write(data);
    		} catch(IOException e) {
    			e.printStackTrace();
    		}
        	System.out.println(request.getCharacterEncoding());
        	//String username = request.getParameter("NAME");
        	//System.out.println(username);
    }
    @RequestMapping(value ="/login",method = RequestMethod.POST)
    public void login(HttpServletRequest request, HttpServletResponse response) {
    	String data;
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
	        List<Map<String, Object>>rows=adi.getAcoountByUserName(user.getusername());
	        if(rows.get(0).get("password").equals(user.getpassword())) {
	        	data = "success";
	        } else {
	        	data = "fail";
	        }
	        response.setCharacterEncoding("UTF-8");
	        response.setHeader("content-type", "text/html;charset=UTF-8");
	        PrintWriter out = response.getWriter();
	        out.write(data);
	        System.out.println("login");
    	}catch(IOException e) {
			e.printStackTrace();
		}
    	
    }
}



