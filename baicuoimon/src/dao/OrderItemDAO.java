package dao;

import java.sql.*;

public class OrderItemDAO {

    public void addItem(int orderId, int menuItemId, int quantity) throws Exception {

        String sql = "INSERT INTO order_items(order_id, menu_item_id, quantity, price) " +
                "SELECT ?, id, ?, price FROM menu_items WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, orderId);
        ps.setInt(2, quantity);
        ps.setInt(3, menuItemId);

        ps.executeUpdate();
    }

    public void showByOrder(int orderId) throws Exception {

        String sql = "SELECT oi.id, m.name, oi.quantity, oi.status " +
                "FROM order_items oi " +
                "JOIN menu_items m ON oi.menu_item_id = m.id " +
                "WHERE oi.order_id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- MÓN ĐÃ GỌI ---");

        while (rs.next()) {

            String status = rs.getString("status");

            String note = "";
            if (status.equals("SERVED")) {
                note = " (ĐÃ XONG - KHÔNG HỦY)";
            } else if (!status.equals("PENDING")) {
                note = " (ĐANG LÀM - KHÔNG HỦY)";
            }

            System.out.println(
                    rs.getInt("id") + " - " +
                            rs.getString("name") + " x" +
                            rs.getInt("quantity") + " - " +
                            status + note
            );
        }
    }

    public boolean cancelItem(int id) throws Exception {

        String checkSql = "SELECT status FROM order_items WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement checkPs = conn.prepareStatement(checkSql);
        checkPs.setInt(1, id);

        ResultSet rs = checkPs.executeQuery();

        if (!rs.next()) {
            System.out.println(" Không tìm thấy món!");
            return false;
        }

        String status = rs.getString("status");

        if (!status.equals("PENDING")) {
            System.out.println(" Không thể hủy! Món đã được chế biến hoặc hoàn tất!");
            return false;
        }

        String sql = "DELETE FROM order_items WHERE id = ?";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, id);

        ps.executeUpdate();

        return true;
    }

    public void showPendingItems() throws Exception {

        String sql = "SELECT oi.id, m.name, oi.quantity, oi.status " +
                "FROM order_items oi " +
                "JOIN menu_items m ON oi.menu_item_id = m.id " +
                "WHERE oi.status IN ('PENDING','COOKING','READY')";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- DANH SÁCH MÓN ---");

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " - " +
                            rs.getString("name") + " x" +
                            rs.getInt("quantity") + " - " +
                            rs.getString("status")
            );
        }
    }

    public void updateStatus(int id, String status) throws Exception {

        String checkSql = "SELECT status FROM order_items WHERE id = ?";
        Connection conn = DBConnection.getConnection();
        PreparedStatement checkPs = conn.prepareStatement(checkSql);
        checkPs.setInt(1, id);

        ResultSet rs = checkPs.executeQuery();

        if (!rs.next()) {
            System.out.println(" Không tìm thấy món!");
            return;
        }

        String currentStatus = rs.getString("status");

        if (currentStatus.equals("SERVED")) {
            System.out.println(" Món đã hoàn tất!");
            return;
        }

        if (currentStatus.equals("PENDING") && !status.equals("COOKING")) {
            System.out.println(" Phải chuyển PENDING -> COOKING");
            return;
        }

        if (currentStatus.equals("COOKING") && !status.equals("READY")) {
            System.out.println(" Phải chuyển COOKING -> READY");
            return;
        }

        if (currentStatus.equals("READY") && !status.equals("SERVED")) {
            System.out.println(" Phải chuyển READY -> SERVED");
            return;
        }

        String sql = "UPDATE order_items SET status = ? WHERE id = ?";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, status);
        ps.setInt(2, id);

        int rows = ps.executeUpdate();

        if (rows > 0) {
            System.out.println(" Cập nhật thành công!");
        } else {
            System.out.println(" Cập nhật thất bại!");
        }
    }

    public void printBill(int orderId) throws Exception {

        String sql = "SELECT m.name, oi.quantity, oi.price, (oi.quantity * oi.price) as total " +
                "FROM order_items oi " +
                "JOIN menu_items m ON oi.menu_item_id = m.id " +
                "WHERE oi.order_id = ? AND oi.status = 'SERVED'";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, orderId);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n========= HÓA ĐƠN =========");

        while (rs.next()) {
            System.out.println(
                    rs.getString("name") + " x" +
                            rs.getInt("quantity") + " | " +
                            rs.getDouble("price") + "đ | Thành tiền: " +
                            rs.getDouble("total") + "đ"
            );
        }
    }
}