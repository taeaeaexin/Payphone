package payphone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class UserService {
    public static void registerUser() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("아이디: ");
        String userid = scanner.next();
        System.out.print("비밀번호: ");
        String password = scanner.next();
        System.out.print("이름: ");
        String name = scanner.next();

        String sql = "INSERT INTO users (userid, password, name) VALUES (?, ?, ?)";

        try (Connection con = DBManager.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, userid);
            pstmt.setString(2, password);
            pstmt.setString(3, name);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("회원가입이 완료되었습니다");
            } else {
                System.out.println("회원가입에 실패했습니다");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}