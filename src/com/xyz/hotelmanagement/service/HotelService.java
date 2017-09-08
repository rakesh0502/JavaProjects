/**
 * File: HotelManagement
 * Package: com.xyz.hotelmanagement.service
 * Description: Service Layer for Hotel Service
 * Version: 1.0
 * Author: Rakeshsingh Rana @author rr812950
 * 
 */
package com.xyz.hotelmanagement.service;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xyz.hotelmanagement.dao.HotelDAO;
import com.xyz.hotelmanagement.dto.Hotel;
import com.xyz.hotelmanagement.exception.HotelManagementException;
/**
 * @author rr812950
 *
 */
public class HotelService implements IHotelService {
	private Hotel hotel=null;
	
	public String addHotel(Hotel hotel)throws HotelManagementException
	{	
	
		
		boolean status=validate(hotel);				//calls validate() to check for correct input
		String hotelId=null;
		if(status)
		{
			HotelDAO hotelDAO=new HotelDAO();
			//hotel.setHotelId(hotelId);
			hotelId=hotelDAO.addHotel(hotel);
		}
		else
		{
			throw new HotelManagementException("Enter correct details for Adding new hotel");
		}
		return hotelId;
	}
		
	
	public boolean validate(Hotel hotel)throws HotelManagementException
	{																	//Validating details for hotel entry
		boolean status=false;
		Pattern patternEmail=Pattern.compile("^\\w+([\\.?|_?]?\\w*[@][a-z]+\\.([a-z]{3}|[a-z]{2}\\.[a-z]{2}))$");
		Matcher matcherEmail=patternEmail.matcher(hotel.getEmailId());
		Pattern patternPhoneNumber=Pattern.compile("\\d{10}");
		Matcher matcherPhoneNumber=patternPhoneNumber.matcher(hotel.getPhoneNumber()+"");
		
		if(hotel.getHotelName().length()<3)
		{
			status=false;
			throw new HotelManagementException("Hotel Name should be greater 2 alphabets");
		}
		else if(!matcherEmail.matches())
		{
			status=false;
			throw new HotelManagementException("Invalid email for hotel");
		}
		else if(!matcherPhoneNumber.matches())
		{
			status=false;
			throw new HotelManagementException("Invalid email for hotel");
		}
		else if(hotel.getCity().length()<3)
		{
			status=false;
			throw new HotelManagementException("Hotel City should be greater 2 alphabets");
		}
		else
		{
			status=true;
		}

		return status;
	}
	
	public List<Hotel> searchAllHotels() throws HotelManagementException
	{
		HotelDAO hotelDAO=new HotelDAO();					//calls searchAllHotels() of HotelDAO and gets the List
		List<Hotel> list=hotelDAO.searchAllHotels();
		//System.out.println("Service");
		return list;
	}
	public void updateHotelName(String hotelName,String oldHotelName)throws HotelManagementException
	{
		HotelDAO hotelDAO=new HotelDAO();	
		try{
			hotelDAO.updateHotelName(hotelName,oldHotelName);
		}
		catch(HotelManagementException hotelManagementException){
			throw new HotelManagementException(hotelManagementException.getMessage());
		}
	}
	public void addDetailsXml(String fileName)throws HotelManagementException
	{	
		
		try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(fileName);
            System.out.println("After gen file in Service");
            if (file.exists()) {
            	System.out.println("If file exits in Service");
                Document doc = db.parse(file);
                Element docEle = doc.getDocumentElement();
 
                // Print root element of the document
                //System.out.println("Root element of the document: "+ docEle.getNodeName());
 
                NodeList hotelList = docEle.getElementsByTagName("Hotel");
 
                // Print total student elements in document
                //System.out.println("Total hotels: " + hotelList.getLength());
 
                if (hotelList != null && hotelList.getLength() > 0) {
                    for (int i = 0; i < hotelList.getLength(); i++) {
 
                        Node node = hotelList.item(i);
 
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
 
                            System.out.println("=====================");
 
                            hotel=new Hotel();
                            Element e = (Element) node;
                            NodeList nodeList = e.getElementsByTagName("HotelId");
                            System.out.println("HotelId: "+ nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            hotel.setHotelId(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            
                            nodeList = e.getElementsByTagName("HotelName");
                            System.out.println("HotelName: "+ nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            hotel.setHotelName(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            
                            nodeList = e.getElementsByTagName("City");
                            System.out.println("City: "+ nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            hotel.setCity(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            
                            nodeList = e.getElementsByTagName("PhoneNumber");
                            System.out.println("PhoneNumber: "+ nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            hotel.setPhoneNumber(Long.parseLong(nodeList.item(0).getChildNodes().item(0).getNodeValue()));
                            
                            nodeList = e.getElementsByTagName("EmailId");
                            System.out.println("EmailId: "+ nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            hotel.setEmailId(nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            
                            nodeList = e.getElementsByTagName("DateOfRegistration");
                            System.out.println("DateOfRegistration: "+ nodeList.item(0).getChildNodes().item(0).getNodeValue());
                            String date=nodeList.item(0).getChildNodes().item(0).getNodeValue();
                            int year=Integer.parseInt(date.substring(0,4))-1900;
                            int month=Integer.parseInt(date.substring(5,7))-1;
                            int day=Integer.parseInt(date.substring(8,10));
                            hotel.setDateOfRegistration(new java.sql.Date(year,month,day));
                            
                            HotelDAO hotelDAO=new HotelDAO();
                			//hotel.setHotelId(hotelId);
                			hotelDAO.addHotelXml(hotel);
                        }
                    }
                } else {
                    System.exit(1);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
	}
	public void createXmlHotelDetails(String filename)throws HotelManagementException
	{
		ResultSet resultSet=null;
		FileOutputStream fileOutputStream=null;
		try{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document document=db.newDocument();
        Element hotelDetails = document.createElement("HotelDetails");
        document.appendChild(hotelDetails);
        
        HotelDAO hotelDAO=new HotelDAO();
        resultSet=hotelDAO.getListOfHotels();
        //System.out.println("resultset");
        /*if(resultSet.next())
        {
        	System.out.println("has data");
        }*/
        while(resultSet.next())
        {
        	Element hotel = document.createElement("Hotel");
        	
        	Element hotelId = document.createElement("HotelId");
        	hotelId.setTextContent(resultSet.getString(1));
        	Element hotelName = document.createElement("HotelName");
        	hotelName.setTextContent(resultSet.getString(2));
        	Element hotelCity = document.createElement("HotelCity");
        	hotelCity.setTextContent(resultSet.getString(3));
        	Element phoneNumber = document.createElement("PhoneNumber");
        	phoneNumber.setTextContent(resultSet.getString(4));
        	Element emailId = document.createElement("EmailId");
        	emailId.setTextContent(resultSet.getString(5));
        	Element dateOfReg = document.createElement("DateOfReg");
        	dateOfReg.setTextContent(resultSet.getDate(6)+"");
        	hotel.appendChild(hotelId);
        	hotel.appendChild(hotelName);
        	hotel.appendChild(hotelCity);
        	hotel.appendChild(phoneNumber);
        	hotel.appendChild(emailId);
        	hotel.appendChild(dateOfReg);
        	hotelDetails.appendChild(hotel);
        	
        }
        //System.out.println("After while");
        hotelDetails.normalize();
        TransformerFactory tranFactory = TransformerFactory.newInstance(); 
	    Transformer aTransformer = tranFactory.newTransformer(); 
	    //System.out.println("after transformer");
	    Source src = new DOMSource(document);
	    fileOutputStream=new FileOutputStream(filename);
	    //System.out.println("after fileoutputstream");
	    Result dest = new StreamResult(fileOutputStream); 
	    //System.out.println("After stream result");
	    aTransformer.transform(src,dest); 
	    //System.out.println("after transform");

		}
		catch(Exception exception)
		{
			throw new HotelManagementException(exception.getMessage()+" While creating Xml file");
		}
		finally
		{
			try{
				if(fileOutputStream!=null)
				{
					fileOutputStream.close();
				}
			}
			catch(IOException ioException)
			{
				throw new HotelManagementException(ioException.getMessage());
			}
		}
		
	}
}
