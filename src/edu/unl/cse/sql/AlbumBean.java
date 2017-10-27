package edu.unl.cse.sql;

import java.sql.Connection;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import edu.unl.cse.model.Album;
import edu.unl.cse.model.Band;
import edu.unl.cse.model.Member;

public class AlbumBean {

	public Album getDetailedAlbum(String albumTitle)
	{
		/*
		 * Query the database and get the album with the 
		 * specified ID, create an Album object with all
		 * details specified
		 */
		//TODO: implement this method

		Connection conn = DatabaseInfo.getConnection();

		Album a;
		String query = "SELECT AlbumTitle, b.BandName, AlbumYear, AlbumNumber, AlbumID FROM (Albums AS a JOIN Bands AS b ON a.BandID = b.BandID) WHERE AlbumTitle = ?";
		try{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, albumTitle);
			ResultSet rs = ps.executeQuery();
		
			BandBean b = new BandBean();
			a = new Album();
			if(rs.next()) {
				a.setTitle(rs.getString("AlbumTitle"));
				a.setYear(Integer.parseInt(rs.getString("AlbumYear")));
				a.setBand(b.getBand(rs.getString("b.BandName")));
				a.setAlbumNumber(Integer.parseInt(rs.getString("AlbumNumber")));
				a.setAlbumId(Integer.parseInt(rs.getString("AlbumId")));

			}
			
			rs.next();
			conn.close();


		}catch (SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		return a;
	}

	public List<Album> getAlbums() {
		//TODO: implement this method
		ArrayList<Album> albumList = new ArrayList<Album>();
		Connection conn = DatabaseInfo.getConnection();
		
		Album a;
		BandBean b = new BandBean();
		
		String query = "SELECT AlbumTitle, b.BandName, AlbumYear, AlbumNumber, AlbumID FROM (Albums AS a JOIN Bands AS b ON a.BandID = b.BandID)";
		
		try{
			PreparedStatement ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			
			ArrayList<String> songs = new ArrayList<String>();
			while(rs.next()) {
				a = new Album(rs.getString("AlbumTitle"), Integer.parseInt(rs.getString("AlbumYear")), b.getBand(rs.getString("b.BandName")), Integer.parseInt(rs.getString("AlbumNumber")),Integer.parseInt(rs.getString("AlbumID")) );
				int albumID = Integer.parseInt(rs.getString("AlbumID"));
				String query2 = "SELECT SongTitle FROM (Songs AS s JOIN AlbumSongs AS a ON s.SongID = a.SongID) WHERE a.AlbumID = ?";
				ps = conn.prepareStatement(query2);
				ps.setInt(1, albumID);
				ResultSet rs2 = ps.executeQuery();
				while(rs2.next()) {
					songs.add(rs2.getString("SongTitle"));
				}
				a.setSongTitles(songs);
				albumList.add(a);
				
			}
			rs.close();
			
		}catch (SQLException e){
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return albumList;
	}

	/**
	 * Before you test your Java App, you can
	 * test your method implementations using this main:
	 * @param args
	 */
	public static void main(String args[]) {

		System.out.println("Albums: ");
		AlbumBean ab = new AlbumBean();
		for(Album a : ab.getAlbums()) {
			System.out.println("\t"+a.getTitle()+" (id = "+a.getAlbumId()+")");
			
		}

		Album da = null; 
		try {
			da = ab.getAlbums().iterator().next();
		} catch (Exception e) {
			System.out.println("No albums returned in the list");
		}
		if(da != null) {
			System.out.println(da.getTitle()+" details: ");
			for(String trackTitle : da.getSongTitles()) {
				System.out.println("\t"+trackTitle);
			}
		}
	}
}
