package edu.unl.cse.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import edu.unl.cse.model.Band;
import edu.unl.cse.model.Member;


public class BandBean {

	public Band getBand(String name)
	{
		String query = "select BandID from Bands where BandName like ?";
		Connection conn = DatabaseInfo.getConnection();
		int bandID = 0;
		
		try
		{
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			bandID = rs.getInt("BandID");
			conn.close();
			
			
		}
		catch (SQLException e)
		{
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
			
		}
		return this.getBand(bandID);
	}
	
	public Band getBand(int bandId) {

		Band b = new Band();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (InstantiationException e) {
			System.out.println("InstantiationException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			System.out.println("IllegalAccessException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		

		String query = "SELECT BandName FROM Bands where BandID = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, bandId);
			rs = ps.executeQuery();
			if(rs.next()) {
				b.setBandId(bandId);
				b.setName(rs.getString("BandName"));
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		query = "SELECT MusicianFirstName, MusicianLastName, MusicianCountry FROM BandMusicians bm JOIN Musicians m ON bm.MusicianID = m.MusicianID WHERE bm.BandID = ?";
		
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, bandId);
			rs = ps.executeQuery();
			while(rs.next()) {
				b.getMembers().add( new Member(rs.getString("MusicianFirstName"), rs.getString("MusicianLastName"), rs.getString("MusicianCountry") ) );
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}

		try {
			if(rs != null && !rs.isClosed())
				rs.close();
			if(ps != null && !ps.isClosed())
				ps.close();
			if(conn != null && !conn.isClosed())
				conn.close();
		} catch (SQLException e) {
			System.out.println("SQLException: ");
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return b;
	}
}
