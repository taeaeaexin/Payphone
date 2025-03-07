package app.PayPhone.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import app.PayPhone.common.DBManager;
import app.PayPhone.dto.Phone;

public class PhoneDao {
	public int insertPhone(Phone phone) {
		int ret = -1;
		String sql = "insert into phone values (?, ?, ?, ?);";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, phone.getId());
			pstmt.setString(2, phone.getModel());
			pstmt.setString(3, phone.getCompany());
			pstmt.setInt(4, phone.getPrice());
			ret = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		return ret;
	}

	public int updatePhone(Phone phone) {
		int ret = -1;		
		String sql = "update phone set model = ?, company = ?, price = ? where id = ?; ";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, phone.getModel());
			pstmt.setString(2, phone.getCompany());
			pstmt.setInt(3, phone.getPrice());
			pstmt.setInt(4, phone.getId());
			ret = pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		return ret;
	}
	
	public int deletePhone(int id) {
		int ret = -1;
		String sql = "delete from phone where id = ?; ";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			ret = pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		return ret;
	}
	
	public List<Phone> listPhone(){
		List<Phone> list = new ArrayList<>();
		String sql = "select * from phone; ";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Phone phone = new Phone();
				phone.setId(rs.getInt("id"));
				phone.setModel(rs.getString("model"));
				phone.setCompany(rs.getString("company"));
				phone.setPrice(rs.getInt("price"));
				list.add(phone);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		return list;
	}
	
	public List<Phone> listPhone(String searchWord){
		List<Phone> list = new ArrayList<>();
		String sql = "select * from phone where model like ?; "; // % 사용 X
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, "%" + searchWord + "%");
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Phone phone = new Phone();
				phone.setId(rs.getInt("id"));
				phone.setModel(rs.getString("model"));
				phone.setCompany(rs.getString("company"));
				phone.setPrice(rs.getInt("price"));
				list.add(phone);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		return list;
	}
	
	public Phone detailPhone(int id) {
		Phone phone = null;
		String sql = "select * from phone where id = ?; ";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DBManager.getConnection();			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				phone = new Phone();
				phone.setId(rs.getInt("id"));
				phone.setModel(rs.getString("model"));
				phone.setCompany(rs.getString("company"));
				phone.setPrice(rs.getInt("price"));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.releaseConnection(pstmt, con);
		}
		
		return phone;
	}
}




















