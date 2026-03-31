package dao;

import model.Table;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    public void add(Table t) throws Exception {
        String sql = "INSERT INTO tables(table_name, capacity) VALUES (?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, t.getTableName());
        ps.setInt(2, t.getCapacity());

        ps.executeUpdate();
    }

    public List<Table> getAll() throws Exception {
        List<Table> list = new ArrayList<>();

        String sql = "SELECT * FROM tables";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new Table(
                    rs.getInt("id"),
                    rs.getString("table_name"),
                    rs.getInt("capacity"),
                    rs.getString("status")
            ));
        }

        return list;
    }

    public void showAll() throws Exception {

        String sql = "SELECT * FROM tables";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- DANH SÁCH BÀN ---");

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " - " +
                            rs.getString("table_name") + " - Sức chứa: " +
                            rs.getInt("capacity") + " - " +
                            rs.getString("status")
            );
        }
    }

    public void updateStatus(int id, String status) throws Exception {

        String sql = "UPDATE tables SET status = ? WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, status);
        ps.setInt(2, id);

        ps.executeUpdate();
    }

    public void update(int id, String name, int capacity) throws Exception {

        String sql = "UPDATE tables SET table_name = ?, capacity = ? WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.setInt(2, capacity);
        ps.setInt(3, id);

        ps.executeUpdate();

        System.out.println(" Sửa bàn thành công!");
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM tables WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public boolean hasAvailableTable() throws Exception {

        String sql = "SELECT COUNT(*) as total FROM tables WHERE status = 'EMPTY'";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("total") > 0;
        }

        return false;
    }

    public String getStatus(int id) throws Exception {
        String sql = "SELECT status FROM tables WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getString("status");
        }

        return null;
    }

    public int createAndReturnId(String name, int capacity) throws Exception {
        String sql = "INSERT INTO tables(table_name, capacity) VALUES (?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setString(1, name);
        ps.setInt(2, capacity);

        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) {
            return rs.getInt(1);
        }

        throw new Exception("Không tạo được bàn!");
    }
}