package app.PayPhone3.dao;

import app.PayPhone3.data.DBManager;
import app.PayPhone3.dto.PlanDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlanDAO {
    public List<PlanDTO> getAllPlans() {
        List<PlanDTO> plans = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM plan";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                PlanDTO plan = new PlanDTO();
                plan.setPlanId(rs.getInt("planid"));
                plan.setSpeedId(rs.getInt("speedid"));
                plan.setAmountId(rs.getInt("amountid"));
                plan.setPrice(rs.getInt("price"));
                plans.add(plan);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return plans;
    }

    public PlanDTO getPlanById(int planId) {
        PlanDTO plan = null;
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM plan WHERE planid = ?";
            pstmt = con.prepareStatement(query);
            pstmt.setInt(1, planId);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                plan = new PlanDTO();
                plan.setPlanId(rs.getInt("planid"));
                plan.setSpeedId(rs.getInt("speedid"));
                plan.setAmountId(rs.getInt("amountid"));
                plan.setPrice(rs.getInt("price"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return plan;
    }
}