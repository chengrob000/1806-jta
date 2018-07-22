package com.revature.dao;

import static com.revature.utils.CloseStreams.close;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.revature.bean.Notificaiton;
import com.revature.utils.Connections;
import com.revature.utils.StringManip;

public class NotificationDao {

	public void eventAddNotification(Integer eventId, Integer userId, String message) {
		CallableStatement stmt = null;
		ResultSet rs = null;

		String sql = "{CALL EVENT_ADD_NOTIFICATION(?, ?, ?)}";

		try (Connection conn = Connections.getConnection()) {

			stmt = conn.prepareCall(sql);

			stmt.setInt(1, eventId);
			stmt.setInt(2, userId);
			stmt.setString(3, message);

			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}

	}

	public void employeeAddNotification(Integer targetId, Integer sourceId, String message) {
		CallableStatement stmt = null;
		ResultSet rs = null;

		String sql = "{CALL EMPLOYEE_ADD_NOTIFICATION(?, ?, ?)}";

		try (Connection conn = Connections.getConnection()) {

			stmt = conn.prepareCall(sql);

			stmt.setInt(1, targetId);
			stmt.setInt(2, sourceId);
			stmt.setString(3, message);

			stmt.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}

	}

	public List<Notificaiton> selectUserNotifications(Integer userId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM notification WHERE nt_emp_target = ?";

		List<Notificaiton> ls = new ArrayList<Notificaiton>();

		try (Connection conn = Connections.getConnection()) {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userId);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Notificaiton a = new Notificaiton(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), StringManip.getLocalDateTime(rs.getString(7)));
				ls.add(a);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}

		return ls;
	}

	public List<Notificaiton> selectEventNotifications(Integer eventId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM notification WHERE nt_event = ?";

		List<Notificaiton> ls = new ArrayList<Notificaiton>();

		try (Connection conn = Connections.getConnection()) {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, eventId);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Notificaiton a = new Notificaiton(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), StringManip.getLocalDateTime(rs.getString(7)));
				ls.add(a);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}

		return ls;
	}
	
	public List<Notificaiton> selectUnreadEventUserNotifications(Integer eventId, Integer userId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT * FROM notification WHERE nt_event = ? AND nt_emp_target = ? AND nt_read = 0";

		List<Notificaiton> ls = new ArrayList<Notificaiton>();

		try (Connection conn = Connections.getConnection()) {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, eventId);
			stmt.setInt(2, userId);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Notificaiton a = new Notificaiton(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4),
						rs.getInt(5), rs.getInt(6), StringManip.getLocalDateTime(rs.getString(7)));
				ls.add(a);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}

		return ls;
	}
	
	public Map<Integer, Integer> selectUserEventUpdatedMap(Integer userId) {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "SELECT event.ev_id, HAS_NOTIF(?, ev_id) FROM event";

		Map<Integer, Integer> map = new HashMap<Integer, Integer>();

		try (Connection conn = Connections.getConnection()) {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, userId);
			
			rs = stmt.executeQuery();

			while (rs.next()) {
				map.put(rs.getInt(1), rs.getInt(2));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(stmt);
			close(rs);
		}

		return map;
	}

	public void notificationMarkAsRead(Integer ntId) {
		PreparedStatement ps = null;

		String sql = "UPDATE notification SET nt_read = 1 WHERE nt_id = ?";

		try (Connection conn = Connections.getConnection()) {
			ps = conn.prepareStatement(sql);

			ps.setInt(1, ntId);
			
			ps.execute();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(ps);
		}
		
	}
}
