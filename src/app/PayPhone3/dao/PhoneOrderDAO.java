package app.PayPhone3.dao;

import app.PayPhone3.data.DBManager;
import app.PayPhone3.dto.PhoneOrderDTO;
import java.sql.*;

public class PhoneOrderDAO {
    public void insertOrder(PhoneOrderDTO order) {
        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            String query = "INSERT INTO phone_order (companyid, storageid, planid, total_price) VALUES (?, ?, ?)";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, order.getCompanyId());
            pstmt.setInt(2, order.getStorageId());
            pstmt.setInt(3, order.getPlanId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
    }
}