/**
 * 
 */
package com.xyz.hotelmanagement.service;
import java.util.List;

import com.xyz.hotelmanagement.dto.Hotel;
import com.xyz.hotelmanagement.exception.HotelManagementException;
/**
 * @author rr812950
 *
 */
public interface IHotelService {
	
	public String addHotel(Hotel hotel)throws HotelManagementException;
	public List<Hotel> searchAllHotels()throws HotelManagementException;
	public void updateHotelName(String hotelName,String oldHotelName)throws HotelManagementException;
	public void addDetailsXml(String fileName)throws HotelManagementException;
	public void createXmlHotelDetails(String filename)throws HotelManagementException;
}
