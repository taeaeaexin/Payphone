package app.PayPhone3.dao;

import app.PayPhone3.data.DBManager;
import app.PayPhone3.dto.CompanyDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {
    public List<CompanyDTO> getAllCompanies() {
        List<CompanyDTO> companies = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM company";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CompanyDTO company = new CompanyDTO();
                company.setCompanyId(rs.getInt("companyid"));
                company.setName(rs.getString("company"));
                company.setPrice(rs.getInt("price"));
                companies.add(company);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return companies;
    }
}