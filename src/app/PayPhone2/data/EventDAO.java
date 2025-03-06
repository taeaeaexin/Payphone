package app.PayPhone2.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String sql = "SELECT * FROM event";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBManager.getConnection();
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("eventId"));
                event.setTitle(rs.getString("title"));
                event.setText(rs.getString("text"));
                event.setOption1(rs.getString("option1"));
                event.setOption2(rs.getString("option2"));
                event.setResult1_h(rs.getInt("result1_h"));
                event.setResult1_m(rs.getInt("result1_m"));
                event.setResult1_s(rs.getInt("result1_s"));
                event.setResult2_h(rs.getInt("result2_h"));
                event.setResult2_m(rs.getInt("result2_m"));
                event.setResult2_s(rs.getInt("result2_s"));
                event.setResult1_text(rs.getString("result1_text"));
                event.setResult2_text(rs.getString("result2_text"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBManager.releaseConnection(rs, pstmt, con);
        }

        return events;
    }
}