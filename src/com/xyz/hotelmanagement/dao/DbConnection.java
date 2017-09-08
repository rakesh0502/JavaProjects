/**
 * 
 */
package com.xyz.hotelmanagement.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.*;




/**
 * @author rr812950
 *
 */
public class DbConnection {
	private static DbConnection dbConnection=null;
	private DbConnection()throws ClassNotFoundException
	{
		Class.forName("oracle.jdbc.driver.OracleDriver");
	}
	public static DbConnection getDbConnection()throws ClassNotFoundException
	{
		if(dbConnection==null)
		{
			dbConnection=new DbConnection();
		}
		return dbConnection;
	}
	public Connection getConnection()throws SQLException,IOException
	{
		Connection connection=null;
		
		FileInputStream fileInputStream=null;
		try
		{
			fileInputStream=new FileInputStream("jdbc.properties");
			Properties properties=new Properties();
			properties.load(fileInputStream);
			String user=properties.getProperty("Username");
			String password=properties.getProperty("Password");
			String url=properties.getProperty("url");
			//url+=url;
			String driver=properties.getProperty("driver");
			connection=DriverManager.getConnection(url,user,password);
		}
		catch (IOException ioException) {
			System.out.println(ioException.getMessage());
			// TODO: handle exception
		}
		finally{
			if(fileInputStream!=null)
			{
				fileInputStream.close();
			}
		}
		return connection;
	}
	/*public static void main(String[] arg)
	{
		DbConnection dbConnection=null;
		try{
			dbConnection=DbConnection.getDbConnection();
			Connection connection=dbConnection.getConnection();
			System.out.println("Connection Established");
		}
		catch(ClassNotFoundException classNotFoundException)
		{
			System.out.println(classNotFoundException.getMessage());
		}
		catch(SQLException sqlException) 
		{
			System.out.println(sqlException.getMessage());
		}
		catch(IOException ioException)
		{
			System.out.println(ioException.getMessage());	
		}
	}*/

}
