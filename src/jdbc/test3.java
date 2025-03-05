package jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Statement 사용
//CRUD를 메소드화 - url, user, pwd를 static
//메소드 내에서 Connection, Statement, ResultSet 객체화
//메소드 내에서 예외 처리

//Statement -> PreparedStatement 전환
//하드코딩된 value -> 메소드의 Parameter
//DTO -> select에 적용
public class test3 {
    //MySQL에 접근하기 위해 필요한3가지
    static String url = "jdbc:mysql://localhost:3306/madang";
    static String user = "root";
    static String pwd = "root";

    public static void main(String[] args) {
//        insertCustomer(6, "손흥민", "영국 토트넘", "010-6666-6666");
//        updateCustomer(6, "대한민국 서울");
//        deleteCustomer(6);
//        List<CustomerDTO> list = listCustomer(); //없으면 list는 null이 아니라 empty
//        for (CustomerDTO dto : list) {
//            System.out.println(dto);
//        }
        CustomerDTO dto = detailCustomer(2); //없으면 null
        System.out.println(dto);
    }

    static void insertCustomer(int custId, String name, String address, String phone){
        Connection con = null;
        PreparedStatement pstmt = null;
        String insertSql = "insert into customer values (?, ?, ?, ?); "; //value에 해당하는 부분을 ?로 대체

        try{
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(insertSql);
            pstmt.setInt(1, custId);
            pstmt.setString(2, name);
            pstmt.setString(3, address);
            pstmt.setString(4, phone);

            int ret = pstmt.executeUpdate();
            System.out.println(ret);

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                pstmt.close();
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    static void updateCustomer(int custId, String address){
        Connection con = null;
        PreparedStatement pstmt = null;

        String updateSql = "update customer set address = ? where custid = ?; ";

        try{
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(updateSql);
            pstmt.setString(1, address);
            pstmt.setInt(2, custId);

            int ret = pstmt.executeUpdate(updateSql);
            System.out.println(ret);

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                pstmt.close();
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    static void deleteCustomer(int custId){
        Connection con = null;
        PreparedStatement pstmt = null;

        String deleteSql = "delete from customer where custid = ?; ";

        try{
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(deleteSql);
            pstmt.setInt(1, custId);

            int ret = pstmt.executeUpdate();
            System.out.println(ret);

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                pstmt.close();
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    static List<CustomerDTO> listCustomer(){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<CustomerDTO> list = new ArrayList<>();

        String selectSql = "select custid, name, address, phone from customer; ";

        try{
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(selectSql);

            rs = pstmt.executeQuery(selectSql);
            while(rs.next()) {
                //각 row를 CustomerDTO 객체로 만들고 ArrayList에 담는다
                CustomerDTO dto = new CustomerDTO();
                dto.setCustId(rs.getInt("custid"));
                dto.setName(rs.getString("name"));
                dto.setAddress(rs.getString("address"));
                dto.setPhone(rs.getString("phone"));

                list.add(dto);
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                pstmt.close();
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return list;
    }

    static CustomerDTO detailCustomer(int custId){
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        CustomerDTO dto = null;
        String selectSql = "select custid, name, address, phone from customer where custid = ?; ";

        try{
            con = DriverManager.getConnection(url, user, pwd);
            pstmt = con.prepareStatement(selectSql);
            pstmt.setInt(1, custId);

            rs = pstmt.executeQuery();
            if(rs.next()) {
                dto = new CustomerDTO();
                dto.setCustId(rs.getInt("custid"));
                dto.setName(rs.getString("name"));
                dto.setAddress(rs.getString("address"));
                dto.setPhone(rs.getString("phone"));
            }

        }catch(SQLException e){
            e.printStackTrace();
        }finally {
            try{
                rs.close();
                pstmt.close();
                con.close();
            }catch(SQLException e){
                e.printStackTrace();
            }
        }
        return dto;
    }
}