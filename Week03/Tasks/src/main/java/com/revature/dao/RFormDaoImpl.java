package com.revature.dao;

import static com.revature.util.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.revature.beans.Employee;
import com.revature.beans.RForm;
import com.revature.util.Connections;

public class RFormDaoImpl {
	public Boolean insertRFormViaSp(RForm rform) {
		CallableStatement stmt = null; 
		
		try(Connection conn = Connections.getConnection()){

			stmt = conn.prepareCall("{call insertNewRForm(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			
			stmt.setInt(1, rform.getEmpid());
			stmt.setDate(2, rform.getrFormDate());
			stmt.setString(3, rform.getPlace());
			stmt.setString(4, rform.getInfo());
			stmt.setInt(5,rform.getPropReim());
			stmt.setString(6,rform.getJustification());
			stmt.setInt(7,rform.getTimeMissed());
			stmt.setInt(8,rform.getGradeFormat());
			stmt.setInt(9,rform.getCutoffGrade());
			stmt.setInt(10,rform.getEventTypeId());
			stmt.setDouble(11,rform.getEventCost());
			stmt.setInt(12, rform.getSupid());
			stmt.setString(13, rform.getEventName());
			stmt.setInt(14, rform.getDepid());
			stmt.setInt(15, rform.getFinalperc());

			
			stmt.execute(); //Returns amount rows effected;
			return true;
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(stmt);
		}		
		return false;
	}
	
	public Boolean approveRForm(int applvl, int rformid) {
		PreparedStatement ps = null;
		String sql = "UPDATE RForm SET app_lvl = ? WHERE rform_id = ?";
		
		try(Connection conn = Connections.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, applvl);
			ps.setInt(2, rformid);
			ps.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
		
		return false;
	}
	
	public Boolean submitGrade(int grade, int rformid) {
		PreparedStatement ps = null;
		String sql = "UPDATE RForm SET grade = ? WHERE rform_id = ?";
		
		try(Connection conn = Connections.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setInt(1, grade);
			ps.setInt(2, rformid);
			ps.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
		
		return false;
	}
	
	public Boolean setFileKey(int rformid, String key) {
		PreparedStatement ps = null;
		String sql = "UPDATE RForm SET filekey = ? WHERE rform_id = ?";
		
		try(Connection conn = Connections.getConnection()){
			ps = conn.prepareStatement(sql);
			ps.setString(1, key);
			ps.setInt(2, rformid);
			ps.executeUpdate();
			return true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			close(ps);
		}
		
		return false;
	}
}