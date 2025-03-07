package app.PayPhone3.dao;

import app.PayPhone3.data.DBManager;
import app.PayPhone3.dto.StorageDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StorageDAO {
    public List<StorageDTO> getAllStorages() {
        List<StorageDTO> storages = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            String query = "SELECT * FROM storage";
            pstmt = con.prepareStatement(query);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                StorageDTO storage = new StorageDTO();
                storage.setStorageId(rs.getInt("storageid"));
                storage.setGb(rs.getInt("gb"));
                storage.setPrice(rs.getInt("price"));
                storages.add(storage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }
        return storages;
    }
}