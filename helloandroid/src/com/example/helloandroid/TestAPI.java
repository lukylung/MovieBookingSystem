package com.example.helloandroid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import model.Movie;

public class TestAPI {
	
	
    public static List<Movie> movieList = new ArrayList<>();

    
	 /**  
     * 程序中访问 http数据接口  
     */  
    public static String getURLContent(String urlStr) {             
        /** 网络的url地址 */      
     URL url = null;            
        /** http连接 */  
     HttpURLConnection httpConn = null;          
         /**//** 输入流 */ 
     BufferedReader in = null; 
     StringBuffer sb = new StringBuffer(); 
     try{   
      url = new URL(urlStr);   
      in = new BufferedReader( new InputStreamReader(url.openStream(),"UTF-8") ); 
      String str = null;  
      while((str = in.readLine()) != null) {  
       sb.append( str );   
             }   
         } catch (Exception ex) { 
           
         } finally{  
          try{           
           if(in!=null) {
            in.close();   
                 }   
             }catch(IOException ex) {    
             }   
         }   
         String result =sb.toString();   
         //System.out.println(result);   
         return result;  
         }  



    
    
    public static void analyzeMovieDataFromAPI (String dataString) {
    	String tempSSet[] = dataString.split("movies\":");
    	String tempS = tempSSet[1];
    	//去头去尾
    	tempS = tempS.substring(2, tempS.length()-4);
    	tempSSet = tempS.split("\\},\\{");
    	for (int i = 0; i < tempSSet.length; i++) {
    		Movie tempMovie = new Movie();
    		//每一部电影的所有数据
    		String currentMovieString = tempSSet[i];
    		currentMovieString = currentMovieString.substring(1, currentMovieString.length());
    		//System.out.println(currentMovieString);
    		//每一部电影的每一行数据的集合
    		String currentMovieStringSet[] = currentMovieString.split(",\"");
    		for (int j = 0; j < currentMovieStringSet.length; j++) {
    			String currentDataSet[] = currentMovieStringSet[j].split("\":");
    			//System.out.println(currentDataSet[0]);
    			//System.out.println(currentDataSet[1]);
    			if (currentDataSet[0].equals("img")) {
    				tempMovie.img = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("ver")) {
    				tempMovie.ver = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("nm")) {
    				tempMovie.nm = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("scm")) {
    				tempMovie.scm = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("src")) {
    				tempMovie.src = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("dir")) {
    				tempMovie.dir = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("star")) {
    				tempMovie.star = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("cat")) {
    				tempMovie.cat = currentDataSet[1].substring(1, currentDataSet[1].length()-1);
    			}
    			else if (currentDataSet[0].equals("id")) {
    				tempMovie.id = Integer.parseInt(currentDataSet[1]);
    			}
    		}

    		movieList.add(tempMovie);
    	}
    	
    }


    /*public static void main(String args[]) {
    	
    	analyzeMovieDataFromAPI(getURLContent("http://m.maoyan.com/movie/list.json?type=hot&offset=0&limit=1000"));
    	for (int i = 0; i < movieList.size(); i++) {
    		System.out.println(movieList.get(i).nm + "         " + movieList.get(i).dir + "         " + movieList.get(i).star);
    		System.out.println();
    	}
    }*/
}
