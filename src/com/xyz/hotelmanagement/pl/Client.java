/**
 * File: HotelManagement
 * Package: com.igate.hotelmanagement.pl
 * Description: Presentation Layer for User interaction
 * Version: 1.0
 * Author: Rakeshsingh Rana @author rr812950
 * 
 */
package com.xyz.hotelmanagement.pl;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import com.xyz.hotelmanagement.dto.Hotel;
import com.xyz.hotelmanagement.exception.HotelManagementException;
import com.xyz.hotelmanagement.service.HotelService;
import com.xyz.hotelmanagement.service.IHotelService;
/**
 * Class: Client for static methods in Presentation Layer
 * @author rr812950
 *
 */
public class Client {

	/**
	 * @Description: method for adding hotel to the List and it calls addHotel() of HotelService
	 * @param none
	 * @throws HotelManagementException
	 * 
	 * @author rr812950
	 */
	public static void addHotel()
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the details of hotels to be added ");
		String hotelName;
		String city;
		long phoneNumber;
		String emailId;
		java.sql.Date dateOfRegistration;
		System.out.println("Enter hotel name(greater than 2 letters)");
		hotelName=scanner.next();
		System.out.println("Enter city(greater than 2 letters)");
		city=scanner.next();
		System.out.println("Enter phone number(10 digits)");
		phoneNumber=scanner.nextLong();
		System.out.println("Enter email id");
		emailId=scanner.next();
	
		//System.out.println("Enter date (yyyy,mm,dd)");
		java.util.Date dateOfReg=new java.util.Date();
		dateOfRegistration=new java.sql.Date(dateOfReg.getYear(),dateOfReg.getMonth(),dateOfReg.getDate());
		IHotelService hotelService=new HotelService();
		Hotel hotel=new Hotel();
		hotel.setHotelName(hotelName);
		hotel.setCity(city);
		hotel.setPhoneNumber(phoneNumber);
		hotel.setEmailId(emailId);
		hotel.setDateOfRegistration(dateOfRegistration);
		String hotelId=null;
		try
		{
			hotelId=hotelService.addHotel(hotel);			//calls addhotel() of HotelService
			System.out.println("Hotel added to the List with Id: "+hotelId);
		}
		catch(HotelManagementException hotelManagementException)			//block to catch the exception due to invalid input
		{
			System.out.println(hotelManagementException.getMessage());		//Printing message for invalid entry
		}
	}
	
	/**
	 * @Description: method for searching hotels from the List and it calls searchAllHotels() of HotelService
	 * @param none
	 * 
	 * @author rr812950
	 */
	public static void searchAllHotels()
	{
		IHotelService hotelService=new HotelService();
		try{
		List<Hotel> list=hotelService.searchAllHotels();		//calls searchAllHotels() of HotelService
		if(list.isEmpty())
		{
			System.out.println("No records found");
		}
		else
		{
		System.out.println("List of Hotels :\n");
		Iterator<Hotel> iterator1=list.iterator();			//Using Iterator for an Object
	
			while(iterator1.hasNext())
			{
				Hotel hotel=iterator1.next();					//checking for next element in the List
				System.out.println("Hotel Id: "+hotel.getHotelId());
				System.out.println("Hotel Name: "+hotel.getHotelName());
				System.out.println("Hotel City: "+hotel.getCity());
				System.out.println("Hotel Phone No: "+hotel.getPhoneNumber());
				System.out.println("Hotel Mail Id: "+hotel.getEmailId());
				System.out.println("Hotel's Registration date: "+hotel.getDateOfRegistration());
				System.out.println("\n");
				
			}
		}
		}
		catch(HotelManagementException hotelManagementException)
		{
			System.out.println(hotelManagementException.getMessage());
		}
		
	}
	public static void updateHotelName()
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("Procedure to change hotel name");
		System.out.println("Enter New Hotel Name: ");
		String hotelName=scanner.next();
		System.out.println("Enter Hotel Name to be changed: ");
		String oldHotelName=scanner.next();
		IHotelService hotelService=new HotelService();
		try{
			hotelService.updateHotelName(hotelName, oldHotelName);
		}
		catch(HotelManagementException hotelManagementException)
		{
			System.out.println(hotelManagementException.getMessage());
		}
	}
	
	public static void addHotelsXml()
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the xml filename");
		String fileXml=scanner.next();
		IHotelService hotelService=new HotelService();
		
		try{
			//System.out.println("inclient try");
			hotelService.addDetailsXml(fileXml);
		}
		catch (HotelManagementException hotelManagementException) {
			
			System.out.println(hotelManagementException.getMessage());
		}
	}
	
	public static void createXml()
	{
		Scanner scanner=new Scanner(System.in);
		System.out.println("Enter the xml filename");
		String fileXml=scanner.next();
		IHotelService hotelService=new HotelService();
		try{
			hotelService.createXmlHotelDetails(fileXml);
			System.out.println("Xml File created");
		}
		catch(HotelManagementException hotelManagementException)
		{	
			System.out.println(hotelManagementException.getMessage());
		}
	}
	
	/**
	 * @Description: Main Method for the Application with Switch-case for adding Hotels, searching all hotels and exiting the program
	 * @param args
	 * 
	 * @author rr812950
	 */
	
	public static void main(String[] args) {	//main() begins
		
		Scanner scanner=new Scanner(System.in);
		
		int choice;
		while(true)			//while begins
		{
			System.out.println("Enter choice\n1. to add Hotel\n2. to search hotels\n3. to Change Hotel name\n4. to add Hotels from Xml file\n5. to create an Xml file of Hotel\n6. to Exit");
			choice=scanner.nextInt();
			switch(choice)		//switch-case begins
			{
			case 1:
				{
					addHotel();
					System.out.println();
					break;
				}
			
			case 2:
				{
					searchAllHotels();
					System.out.println();
					break;
				}
			case 3:
				{	
					System.out.println();
					updateHotelName();
					break;
				}
			case 4:
				{
					System.out.println();
					addHotelsXml();
					break;
				}
			case 5:
				{
					System.out.println();
					createXml();
					break;
				}
			case 6:
				{
					System.out.println("Exit Successful");
					System.exit(0);
				}
			default :
					System.out.println("Wrong choice");
			}		//Switch-case Ends
		}		//while ends
		
	}			//main() ends

}		//Client class ends
		