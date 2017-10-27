package edu.unl.cse.sql;

import java.sql.*;

import edu.unl.cse.model.Member;

public class AlbumAdder {

	private String  albumTitle;
	private String  bandName;
//	private String  albumYear;
//	private String  albumNumber;
	private Integer  albumYear;
	private Integer  albumNumber;
	
	//Create bandID to insert
	
	private String BandID;
	
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
		Connection conn = DatabaseInfo.getConnection();
		PreparedStatement ps;
		ResultSet rs;
		// get the bandName from Bands 
		String bandNameQuery = "SELECT a.BandID FROM (Albums as a JOIN Bands as b ON a.BandID = b.BandID) WHERE bandName like ?;";
		try
		{
			ps = conn.prepareStatement(bandNameQuery);
			ps.setString(1, this.getBandName());
			rs = ps.executeQuery();
			
			if (rs.next())
			{
				//delete duplicates BandName if exist?
				
				this.setBandID(rs.getString("a.BandID"));
//				String removeMembers = "DELETE FROM Bands where BaneName = ?";
				rs.close();
				ps.close();
				
//				ps = conn.prepareStatement(removeMembers);
//				ps.setString(1, this.getBandName());
//				ps.executeUpdate();
//				ps.close();
			}
			else
			{
				//Insert bandName if there isn't duplicate?
				rs.close();
				ps.close();
				
				//Insert
				String insertBand = "insert into Bands (BandName) values (?)";
				ps = conn.prepareStatement(insertBand);
				ps.setString(1, this.getBandName());
				ps.executeUpdate();
				
				String bandIDQuery = "SELECT BandID FROM Bands  WHERE bandName = ?;";
				ps = conn.prepareStatement(bandIDQuery);
				ps.setString(1, this.getBandName());
				rs = ps.executeQuery();
				if(rs.next()) {
					this.setBandID(rs.getString("BandID"));
				}
			

				ps.close();
				
				
//				ps = conn.prepareStatement(bandNameQuery);
//				ps.setString(1, this.getBandName());
//				rs = ps.executeQuery();
//				
//				rs.next();
//				this.setBandName(rs.getString("BandName")) ;
				
			}
			
			//Insert the album number, title, year, and bandID
						
			
//			String bandIDQuery = "SELECT BandID from Bands WHERE BandName like (?);";
//			
//			try {
//				//get the BandID from BandName
//				ps = conn.prepareStatement(bandIDQuery);
//				ps.setString(1, this.getBandName());
//				rs = ps.executeQuery();
//				
//				this.setBandID(rs.getString("BandID"));
//				
//				ps.close();
//				
//			} catch (SQLException e)
//			{
//				System.out.println("SQLException: ");
//				e.printStackTrace();
//				throw new RuntimeException(e);
//			}
			String insertAlbum = "INSERT INTO Albums (AlbumTitle,AlbumYear, BandID, AlbumNumber) VALUES (?,?,?,?)";

			
			PreparedStatement albumPS = conn.prepareStatement(insertAlbum);
			
			albumPS.setString(1, getAlbumTitle());
			albumPS.setString(2, getAlbumYear().toString());
			albumPS.setString(3, this.getBandID());
			albumPS.setString(4,getAlbumNumber().toString());
			albumPS.executeUpdate();
			
			albumPS.close();
			conn.close();
		
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return true;
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
		if(albumYear != null) {
			try{
				this.albumYear = Integer.parseInt(albumYear);
			}catch (Exception e){
				e.printStackTrace();
			}
		}else {
			this.albumYear = -9999;
		}
		
	}

	public Integer getAlbumNumber() {
		return albumNumber;
	}

	public void setAlbumNumber(String albumNumber) {
		if(albumNumber != null) {
			try{
				this.albumNumber = Integer.parseInt(albumNumber);
			}catch (Exception e){
				e.printStackTrace();
			}
		}else {
			this.albumNumber = -9999;
		}
		
	}

	public String getBandID() {
		return BandID;
	}

	public void setBandID(String bandID) {
		this.BandID = bandID;
	}


}
