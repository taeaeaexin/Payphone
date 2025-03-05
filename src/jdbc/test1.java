package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

//Statement 사용
public class test1 {
    public static void main(String[] args) throws Exception {
        //MySQL에 접근하기 위해 필요한3가지
        String url = "jdbc:mysql://localhost:3306/madang";
        String user = "root";
        String pwd = "root";

        //Driver 테스트 (Drivermanager에 자동 등록)
//        Class.forName("com.mysql.cj.jdbc.Driver");

        //JDBC 드라이버 객체를 DriverManager에 등록 단계 필요 <- 버전 올라가면서 자동 처리

        //Connection (DB 연결)
        Connection con = DriverManager.getConnection(url, user, pwd);

        //Statement (sql 전달 객체)
        Statement stmt = con.createStatement();

        //ResultSet (select query의 결과)
        ResultSet rs = null;

        //insert
        //dup 확인
//        {
//            String insertSql = "insert into customer values (6, '손흥민', '영국 토트넘', '010-6666-6666'); ";
//            int ret = stmt.executeUpdate(insertSql);
//            System.out.println(ret);
//        }

        //update
//        {
//            String updateSql = "update customer set address = '대한민국 서울' where custid = 6; ";
//            int ret = stmt.executeUpdate(updateSql);
//            System.out.println(ret);
//        }

        //delete
//        {
//            String deleteSql = "delete from customer where custid = 6; ";
//            int ret = stmt.executeUpdate(deleteSql);
//            System.out.println(ret);
//        }

        //select list (복수 건)
//        {
//            String selectSql = "select * from customer; ";
//            String selectSql = "select custid, name, address, phone from customer; ";
//            rs = stmt.executeQuery(selectSql);
//            while(rs.next()) { //row 이동
//                //해당 row에서 필요한 column 획득 <- rs.getInt(), rs.getString() (괄호안에 인덱스(숫자)도 사용 가능하지만, 일반적으로 컬럼명 사용)
//                System.out.println(rs.getInt("custid") + " | " + rs.getString("name") + " | " + rs.getString("address") + " | " + rs.getString("phone"));
//            }
//        }

        //select one (단수 건)
//        {
//            String selectSql = "select * from customer where custid = 4 ; ";
////            String selectSql = "select custid, name, address, phone from customer where custid = 4; ";
////            String selectSql = "select custid, name cuse_name, address cust_address, phone cust_phone from customer where custid = 4; "; //alias
//            rs = stmt.executeQuery(selectSql);
//            while(rs.next()) { //1건에 대해 있고 없고
//                //해당 row에서 필요한 column 획득 <- rs.getInt(), rs.getString() (괄호안에 인덱스(숫자)도 사용 가능하지만, 일반적으로 컬럼명 사용)
//                System.out.println(rs.getInt("custid") + " | " + rs.getString("name") + " | " + rs.getString("address") + " | " + rs.getString("phone"));
////                System.out.println(rs.getInt("custid") + " | " + rs.getString("name") + " | " + rs.getString("address") + " | " + rs.getString("phone"));
//            }
//        }

        if(rs==null) rs.close();
        stmt.close();
        con.close();
    }
}