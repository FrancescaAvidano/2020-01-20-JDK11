package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Coppia;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getRuoli(){
		String sql = "SELECT DISTINCT role FROM authorship ORDER BY role";
		List<String> ruoli = new LinkedList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				ruoli.add(res.getString("role"));
				} 
			conn.close();
			return ruoli;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Coppia> getCoppia(String ruolo){
		String sql = "SELECT a1.artist_id AS artista1, a2.artist_id AS artista2, COUNT(DISTINCT(eo1.exhibition_id)) AS peso " + 
				"FROM artists a1, artists a2, authorship au1, authorship au2, " + 
				"	 exhibition_objects eo1, exhibition_objects eo2 " + 
				"WHERE au1.role = ? AND au2.role = ? " + 
				" 	AND a1.artist_id = au1.artist_id AND a2.artist_id = au2.artist_id " + 
				" 	AND a1.artist_id > a2.artist_id " + 
				"	AND au1.object_id = eo1.object_id " + 
				"	AND au2.object_id = eo2.object_id " + 
				"	AND eo1.exhibition_id = eo2.exhibition_id " + 
				"GROUP BY a1.artist_id, a2.artist_id ORDER BY peso desc;";
		List<Coppia> coppie = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, ruolo);
			st.setString(2, ruolo);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				coppie.add(new Coppia(res.getInt("artista1"), res.getInt("artista2"), res.getInt("peso")));
				} 
			conn.close();
			return coppie;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
