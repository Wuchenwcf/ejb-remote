package org.jboss.as.quickstarts.ejb.remote.database;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
public class DatabaseContrler {
	
	
	Connection con=null;
	public DatabaseContrler()
	{

		test();
	}
	public Connection getConnection()
	{
		try 
		{
			final Hashtable<String, String> jndiProperties = new Hashtable<>();
			jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		    final Context context = new InitialContext(jndiProperties);
			DataSource myDb=(DataSource)context.lookup("java:/jboss/jdbc/mysql");
		    return myDb.getConnection();
			//statement=connection.createStatement();
			
		}
		catch (SQLException e)
		{
			
			e.printStackTrace();
			// TODO: handle exception
		}
		catch (Exception e) 
		{
		
			e.printStackTrace();
			// TODO: handle exception
		}
		return null;
	}
    public String test(){
    	Connection conn=null;
    	String s="";
    	try 
		{
			final Hashtable<String, String> jndiProperties = new Hashtable<>();
			jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
		    final Context context = new InitialContext(jndiProperties);
			DataSource myDb=(DataSource)context.lookup("java:/jboss/jdbc/mysql");
		    conn=myDb.getConnection();
			//statement=connection.createStatement();
			
		}
		catch (SQLException e)
		{
			s=s+e.toString();
			e.printStackTrace();
			// TODO: handle exception
		}
		catch (Exception e) 
		{
			s=s+e.toString();
			e.printStackTrace();
			// TODO: handle exception
		}
    	
    	return s;
    }
 
	
	public String select() throws SQLException

	{
		
		String s="连接失败";

		String sql="select * from students";
		Statement statement=con.createStatement();
		ResultSet rs=statement.executeQuery(sql);
		
		while(rs.next())
		{
			//s=s+rs.getString("id")+rs.getString("stuname")+rs.getInt("times")+rs.getString("lasttime");
		}
		
	
	
		return s;
	}

	public String statelessRegist(String name,String passworld)

	{
		Connection con=this.getConnection();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String data=   df.format(new java.util.Date());
		String sql="INSERT into mw2017.`user`(`user`.`name`, `user`.`password`,`user`.`times`,`user`.`lasttime`) VALUES('"+name+"','"+passworld+"',0,'"+data+"');";
		try {
			Statement s=con.createStatement();
			s.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.toString()+sql;
			
			
		}
		
		return "注册成功!";
		
	
	}

	public String statelessLoad(String name,String password) throws SQLException
	{
		String s="";
		Connection con=this.getConnection();
		Statement st=con.createStatement();
		String sql="select * from user";
		ResultSet rs=st.executeQuery(sql);
		boolean yes=false;
		while(rs.next())
		{
			s+=rs.getString("name")+" ";
			s+=rs.getString("password")+"\n";
			if(rs.getString("name").equals(name)&&rs.getString("password").equals(password))
			{
				yes=true;
				break;
			}
			 
		}
		if(yes)
		{
			int times=rs.getInt("times")+1;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=   df.format(new java.util.Date());
			sql="UPDATE `user` set times="+times+",lasttime='"+data+"' WHERE `name`='a';";
			st.execute(sql);
			return "登录成功";
		}
		else
			
		return "登录失败！";
	}
	
	public String statefullLoad(String name,String password) throws SQLException
	{
		String s="";
		Connection con=this.getConnection();
		Statement st=con.createStatement();
		String sql="select * from user";
		ResultSet rs=st.executeQuery(sql);
		boolean yes=false;
		try{
		while(rs.next())
		{
			s+=rs.getString("name")+" ";
			s+=rs.getString("password")+"\n";
			if(rs.getString("name").equals(name)&&rs.getString("password").equals(password))
			{
				yes=true;
				break;
			}
			 
		}
		}
		catch(Exception e)
		{
			return e.toString()+s;
		}
		
		if(yes)
		{
			int times=rs.getInt("times")+1;
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String data=   df.format(new java.util.Date());
			String lasttime=rs.getString("lasttime");
			sql="UPDATE `user` set times="+times+",lasttime='"+data+"' WHERE `name`='a';";
			st.execute(sql);
			
			return "本次为第"+times+"次登录!"+"  上次登录的实践为"+lasttime;
		}
		else
			
		return "登录失败！";
	}

}
