package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.beans.Npc;
import com.revature.util.Connections;

public class NpcDaoImpl implements NpcDao{

	public void insertNpc(Npc npc) {
		// TODO Auto-generated method stub
		
	}

	public Npc selectNpcById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * Try-With-Resources will close any streams you create within the
	 * parenthesis' of the try blockm once the try-catch-finally has
	 * finished.
	 */
	public List<Npc> selectAllNpc() {
		
		Statement stmt = null; // Simple SQL query to be executed
		ResultSet rs = null; //Object that holds query results
		List<Npc> npcs = new ArrayList<>();
		
		try(Connection conn = Connections.getConnection()){
			String sql = "SELECT * FROM npc";
			
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
						
			

			while(rs.next()){
				Npc npc = new Npc(
						rs.getInt(1),
						rs.getString("npc_name"),
						rs.getInt(3),
						rs.getInt(4),
						rs.getInt(5)
						);
				npcs.add(npc);
			}
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
			close(rs);
		}
		
		return npcs;
	}

	public Integer deleteNpcById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer updateNpcById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}