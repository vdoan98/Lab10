package edu.unl.cse.sql;

import java.sql.*;

public class AlbumAdder {

	private String  albumTitle;
	private String  bandName;
//	private String  albumYear;
//	private String  albumNumber;
	private Integer  albumYear;
	private Integer  albumNumber;
	
	public AlbumAdder(String albumTitle, String bandName, String albumYear, String albumNumber) {
		this.setAlbumTitle(albumTitle);
		this.setBandName(bandName);  
		this.setAlbumYear(albumYear); 
		this.setAlbumNumber(albumNumber);
	}

	/**
	 * Adds the album (and band if necessary) to the database and returns true if the insert was
	 * successful, false otherwise.
	 * @return
	 */
	public boolean AddAlbumToDatabase() {
		//TODO: implement this method
		return false;
	}
	
	
	
	
	public boolean removeAlbumFromDatabase()
	{
		return false;
	}

	public String getAlbumTitle() {
		return albumTitle;
	}

	public void setAlbumTitle(String albumTitle) {
		this.albumTitle = albumTitle;
	}

	public String getBandName() {
		return bandName;
	}

	public void setBandName(String bandName) {
		this.bandName = bandName;
	}

	public Integer getAlbumYear() {
		return albumYear;
	}

	public void setAlbumYear(String albumYear) {
		try{
			this.albumYear = Integer.parseInt(albumYear);
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public Integer getAlbumNumber() {
		return albumNumber;
	}

	public void setAlbumNumber(String albumNumber) {
		try{
			this.albumNumber = Integer.parseInt(albumNumber);
		}catch (Exception e){
			e.printStackTrace();
		}
	}


}
