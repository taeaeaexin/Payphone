package app.PayPhone3.dao;

import app.PayPhone3.data.DBManager;
import app.PayPhone3.dto.AmountDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AmountDAO {
    public List<AmountDTO> getAllAmounts() {
        List<AmountDTO> amounts = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM amount";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                AmountDTO amount = new AmountDTO();
                amount.setAmountId(rs.getInt("amountid"));
                amount.setAmount(rs.getInt("amount"));
                amount.setPrice(rs.getInt("price"));
                amounts.add(amount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return amounts;
    }

    public AmountDTO getAmountById(int amountId) {
        AmountDTO amount = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM amount WHERE amountid = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, amountId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                amount = new AmountDTO();
                amount.setAmountId(rs.getInt("amountid"));
                amount.setAmount(rs.getInt("amount"));
                amount.setPrice(rs.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return amount;
    }
}