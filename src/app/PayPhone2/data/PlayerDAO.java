package app.PayPhone2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerDAO {
    public void createPlayer(Player player) {
        String sql = "INSERT INTO player (name, health, money, stamina) VALUES (?, ?, ?, ?)";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, player.getName());
            pstmt.setInt(2, player.getHealth());
            pstmt.setInt(3, player.getMoney());
            pstmt.setInt(4, player.getStamina());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(pstmt, con);
        }
    }

    public Player getPlayer(String name) {
        String sql = "SELECT * FROM player WHERE name = ?";
        Player player = null;

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                player = new Player();
                player.setName(rs.getString("name"));
                player.setHealth(rs.getInt("health"));
                player.setMoney(rs.getInt("money"));
                player.setStamina(rs.getInt("stamina"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return player;
    }
}