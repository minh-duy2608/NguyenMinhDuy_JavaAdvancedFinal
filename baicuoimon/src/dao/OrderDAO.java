package dao;

import java.sql.*;

public class OrderDAO {

    public int createOrder(int userId, int tableId) throws Exception {

        String sql = "INSERT INTO orders(user_id, table_id) VALUES (?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        ps.setInt(1, userId);
        ps.setInt(2, tableId);

        int rows = ps.executeUpdate();


        if (rows == 0) {
            throw new Exception("Không tạo được order!");
        }

        ResultSet rs = ps.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1); // ID order vừa tạo
        }


        throw new Exception("Không lấy được ID order!");
    }

    public int getTableIdByOrder(int orderId) throws Exception {

        String sql = "SELECT table_id FROM orders WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("table_id");
        }

        return -1;
    }

    public int getActiveOrderByUser(int userId) throws Exception {

        String sql = "SELECT id, table_id FROM orders WHERE user_id = ? AND status = 'PENDING'";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, userId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }

        return -1;
    }

    public double getTotalAmount(int orderId) throws Exception {

        String sql = "SELECT SUM(quantity * price) as total FROM order_items WHERE order_id = ? AND status = 'SERVED'";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getDouble("total");
        }

        return 0;
    }

    public void finishOrder(int orderId) throws Exception {

        String sql = "UPDATE orders SET status = 'DONE' WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, orderId);
        ps.executeUpdate();
    }


}