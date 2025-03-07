package app.PayPhone3.dao;

import app.PayPhone3.data.DBManager;
import app.PayPhone3.dto.SpeedDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SpeedDAO {
    public static List<SpeedDTO> getAllSpeeds() {
        List<SpeedDTO> speeds = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM speed";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                SpeedDTO speed = new SpeedDTO();
                speed.setSpeedId(rs.getInt("speedid"));
                speed.setSpeed(rs.getInt("speed"));
                speed.setPrice(rs.getInt("price"));
                speeds.add(speed);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return speeds;
    }

    public SpeedDTO getSpeedById(int speedId) {
        SpeedDTO speed = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM speed WHERE speedid = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, speedId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                speed = new SpeedDTO();
                speed.setSpeedId(rs.getInt("speedid"));
                speed.setSpeed(rs.getInt("speed"));
                speed.setPrice(rs.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return speed;
    }
}