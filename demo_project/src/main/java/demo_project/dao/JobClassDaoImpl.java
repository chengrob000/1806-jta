package demo_project.dao;

import static demo_project.CloseStreams.close;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import demo_project.Connections;
import demo_project.beans.JobClass;
import demo_project.beans.Npc;

public class JobClassDaoImpl implements JobClassDao{
	public List<JobClass> getAllJobs(){
		Statement stmt = null; // Simple SQL query to be executed
		ResultSet rs = null; // Object that holds query results
		List<JobClass> jcs = new ArrayList<>();
		
		try(Connection conn = Connections.getConnection())
		{
			String sql = "SELECT * FROM npc";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			
			while(rs.next()) {
				JobClass jc = new JobClass(rs.getInt(1), rs.getString(2));
				jcs.add(jc);
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		finally {
			close(stmt);
			close(rs);
		}
		return jcs;
	}
}
