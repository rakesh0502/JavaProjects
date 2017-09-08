/**
 * 
 */
package com.xyz.hotelmanagement.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import com.xyz.hotelmanagement.dto.Hotel;
import com.xyz.hotelmanagement.exception.HotelManagementException;

/**
 * @author rr812950
 *
 */
public class HotelDAO {
	private List<Hotel> hotelList=new ArrayList<Hotel>();
	//private static int count=0;
	private DbConnection dbConnection=null;
	private Connection connection=null;
	private Hotel hotel;
	
	//static Logger log=Logger.getLogger(HotelDAO.class.getName());
	public HotelDAO() {
		// TODO Auto-generated constructor stub
		//DOMConfigurator.configure("logJ.xml");
	}
	
	public String addHotel(Hotel hotel)throws HotelManagementException
	{
		/*String hotelId=++count+hotel.getHotelName();		//assigning a unique HotelId
		hotel.setHotelId(hotelId);*/
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		String hotelId=null;
		try
		{
		
		dbConnection=DbConnection.getDbConnection();
		connection=dbConnection.getConnection();
		preparedStatement=connection.prepareStatement("SELECT seq_id_812950.NEXTVAL FROM dual");
		preparedStatement.setMaxRows(1);
		resultSet=preparedStatement.executeQuery();
		resultSet.next();
		int hotel_id=resultSet.getInt(1);
		hotelId=hotel_id+hotel.getHotelName();
		hotel.setHotelId(hotelId);
		preparedStatement=connection.prepareStatement("INSERT INTO hotel_812950 VALUES(?,?,?,?,?,?)");
		preparedStatement.setString(1,hotel.getHotelId());
		preparedStatement.setString(2,hotel.getHotelName());
		preparedStatement.setString(3,hotel.getCity());
		preparedStatement.setString(4,hotel.getPhoneNumber()+"");
		preparedStatement.setString(5,hotel.getEmailId());
		preparedStatement.setDate(6,hotel.getDateOfRegistration());
		System.out.println(hotel.getDateOfRegistration());
		int noOfRecords=preparedStatement.executeUpdate();
		if(noOfRecords!=1)
		{
			System.out.println("Table not added");
		}
	}
	catch(ClassNotFoundException classNotFoundException)
	{
		throw new HotelManagementException(classNotFoundException.getMessage());
	}
	catch(SQLException sqlException) 
	{
		throw new HotelManagementException(sqlException.getMessage());
	}
	catch(IOException ioException)
	{
		throw new HotelManagementException(ioException.getMessage());	
	}
	finally
	{
		try
		{
			if(preparedStatement!=null)
			{
				preparedStatement.close();
			}
		}
		catch(SQLException sqlException)
		{
			throw new HotelManagementException(sqlException.getMessage());
		}
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch(SQLException sqlException)
		{
			throw new HotelManagementException(sqlException.getMessage());
		}
	
		/*String hotelId=++count+hotel.getHotelName();		//assigning a unique HotelId
		hotel.setHotelId(hotelId);
		//hotelList.add(hotel);
		Statement statement=null;
		try
		{
		
		dbConnection=DbConnection.getDbConnection();
		connection=dbConnection.getConnection();
		statement=connection.createStatement();
		//int number=statement.executeUpdate("INSERT INTO hotel_812950 VALUES('vsdvvsd','rggttw','rggegff',1234567,'xfere@gmail.com')");
		int number=statement.executeUpdate("INSERT INTO hotel_812950 VALUES('"+hotel.getHotelId()+"','"+hotel.getHotelName()+"','"+hotel.getCity()+"','"+hotel.getPhoneNumber()+""+"','"+hotel.getEmailId()+"')");
			if(number!=1)
			{
				System.out.println("Table not added");
			}
		}
		catch(ClassNotFoundException classNotFoundException)
		{
			throw new HotelManagementException(classNotFoundException.getMessage());
		}
		catch(SQLException sqlException) 
		{
			throw new HotelManagementException(sqlException.getMessage());
		}
		catch(IOException ioException)
		{
			throw new HotelManagementException(ioException.getMessage());	
		}
		finally
		{
			try
			{
				if(statement!=null)
				{
					statement.close();
				}
			}
			catch(SQLException sqlException)
			{
				throw new HotelManagementException(sqlException.getMessage());
			}
			try
			{
				if(connection!=null)
				{
					connection.close();
				}
			}
			catch(SQLException sqlException)
			{
				throw new HotelManagementException(sqlException.getMessage());
			}
			*/
			
		}
	return hotelId;
		
	}
	public List<Hotel> searchAllHotels()throws HotelManagementException
	{
		//System.out.println("Dao");
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			try{
				dbConnection=DbConnection.getDbConnection();
				System.out.println("db ok");
				connection=dbConnection.getConnection();
				System.out.println("conn ok");
			}
			catch(SQLException sqlException)
			{
				//PropertyConfigurator.configure("log4J.properties");
				
				/*log.info("Sql Exception while connecting"+sqlException.getMessage());*/
				throw new HotelManagementException("Error while creating connection");
			}
			catch(IOException ioException)
			{
				throw new HotelManagementException("Error while creating connection");
			}
			catch(ClassNotFoundException classNotFoundException)
			{
				throw new HotelManagementException("Error while creating connection");
			}
			preparedStatement=connection.prepareStatement("SELECT hotel_id,hotel_name,hotel_city,phone_number,email_id,date_of_registration FROM hotel_812950");
			resultSet=preparedStatement.executeQuery();
			if(resultSet!=null)
			{	while(resultSet.next())
				{	
					hotel=new Hotel();
					hotel.setHotelId(resultSet.getString(1));
					hotel.setHotelName(resultSet.getString(2));
					hotel.setCity(resultSet.getString(3));
					hotel.setPhoneNumber(resultSet.getLong(4));
					hotel.setEmailId(resultSet.getString(5));
					hotel.setDateOfRegistration(resultSet.getDate(6));
					hotelList.add(hotel);
				}
			}
		}
		catch(SQLException sqlException)
		{
			throw new HotelManagementException("Error while retriving list");
		}
		finally
		{
			try{
			if(preparedStatement!=null)
				preparedStatement.close();
			if(resultSet!=null)
				resultSet.close();
			}
			catch(SQLException sqlException2)
			{
				throw new HotelManagementException("Error while returning");
			}
		}
		return hotelList;								//returning List to searchAllHotels() of HotelService
	}
	public void updateHotelName(String hotelName,String oldHotelName)throws HotelManagementException
	{
		PreparedStatement preparedStatement=null;
		try{
		try{
			dbConnection=DbConnection.getDbConnection();
			System.out.println("db ok");
			connection=dbConnection.getConnection();
			System.out.println("conn ok");
		}
		catch(IOException ioException)
		{
			throw new HotelManagementException("Error while creating connection");
		}
		catch(ClassNotFoundException classNotFoundException)
		{
			throw new HotelManagementException("Error while creating connection");
		}
		preparedStatement=connection.prepareStatement("UPDATE hotel_812950 SET hotel_name=? WHERE hotel_name=?");
		preparedStatement.setString(1,hotelName);
		preparedStatement.setString(2,oldHotelName);
		int no_of_updates=preparedStatement.executeUpdate();
		if(no_of_updates!=1)
		{
			System.out.println("Update Unsucessful");
		}
		else
		{
			System.out.println("Update Sucessful");
		}
		}
		catch(SQLException sqlException)
		{
			throw new HotelManagementException("Error while retriving list");
		}
		finally
		{
			try{
				
				if(preparedStatement!=null)
				{
					preparedStatement.close();
				}
			}
			catch(SQLException sqlException2)
			{
				throw new HotelManagementException("Cannot Close Resource");
			}
		}
	}
	
	public void addHotelXml(Hotel hotel)throws HotelManagementException
	{
		
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		
		try
		{
		
		dbConnection=DbConnection.getDbConnection();
		connection=dbConnection.getConnection();
		
		preparedStatement=connection.prepareStatement("INSERT INTO hotel_812950 VALUES(?,?,?,?,?,?)");
		preparedStatement.setString(1,hotel.getHotelId());
		preparedStatement.setString(2,hotel.getHotelName());
		preparedStatement.setString(3,hotel.getCity());
		preparedStatement.setString(4,hotel.getPhoneNumber()+"");
		preparedStatement.setString(5,hotel.getEmailId());
		preparedStatement.setDate(6,hotel.getDateOfRegistration());
		System.out.println(hotel.getDateOfRegistration());
		int noOfRecords=preparedStatement.executeUpdate();
		if(noOfRecords!=1)
		{
			System.out.println("Table not added");
		}
	}
	catch(ClassNotFoundException classNotFoundException)
	{
		throw new HotelManagementException(classNotFoundException.getMessage());
	}
	catch(SQLException sqlException) 
	{
		throw new HotelManagementException(sqlException.getMessage());
	}
	catch(IOException ioException)
	{
		throw new HotelManagementException(ioException.getMessage());	
	}
	finally
	{
		try
		{
			if(preparedStatement!=null)
			{
				preparedStatement.close();
			}
		}
		catch(SQLException sqlException)
		{
			throw new HotelManagementException(sqlException.getMessage());
		}
		try
		{
			if(connection!=null)
			{
				connection.close();
			}
		}
		catch(SQLException sqlException)
		{
			throw new HotelManagementException(sqlException.getMessage());
		}
	}
	}
	
	public ResultSet getListOfHotels()throws HotelManagementException
	{
		
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			try{
				dbConnection=DbConnection.getDbConnection();
				System.out.println("db ok");
				connection=dbConnection.getConnection();
				System.out.println("conn ok");
			}
			catch(SQLException sqlException)
			{
			
				throw new HotelManagementException("Error while creating connection");
			}
			catch(IOException ioException)
			{
				throw new HotelManagementException("Error while creating connection");
			}
			catch(ClassNotFoundException classNotFoundException)
			{
				throw new HotelManagementException("Error while creating connection");
			}
			preparedStatement=connection.prepareStatement("SELECT hotel_id,hotel_name,hotel_city,phone_number,email_id,date_of_registration FROM hotel_812950");
			resultSet=preparedStatement.executeQuery();
			System.out.println("after query");
			/*if(resultSet.next()){System.out.println("has recordddssssss");}
			else{System.out.println("no record fetched");}*/
			
		}
		catch(SQLException sqlException)
		{
			throw new HotelManagementException("Error while retriving list");
		}
		/*finally
		{
			try{
			if(preparedStatement!=null)
				preparedStatement.close();
			
			}
			catch(SQLException sqlException2)
			{
				throw new HotelManagementException("Error while returning");
			}
		}*/
		return resultSet;
	}
	
}
