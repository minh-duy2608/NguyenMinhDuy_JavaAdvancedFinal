package dao;

import model.User;
import java.sql.*;

public class UserDAO {

    public void register(User user) throws Exception {
        String sql = "INSERT INTO users(username, password, role) VALUES (?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, user.getUsername());
        ps.setString(2, user.getPassword());
        ps.setString(3, user.getRole());

        ps.executeUpdate();
    }

    public User findByUsername(String username) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("status")
            );
        }
        return null;
    }

    public void updateRole(int userId, String role) throws Exception {
        String sql = "UPDATE users SET role = ? WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, role);
        ps.setInt(2, userId);

        ps.executeUpdate();
    }

    public void banUser(int userId) throws Exception {
        String sql = "UPDATE users SET status = 'BANNED' WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, userId);
        ps.executeUpdate();
    }

    public void getUsersByRole(String role) throws Exception {
        String sql = "SELECT * FROM users WHERE role = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, role);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- DANH SÁCH " + role + " ---");

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " - " +
                            rs.getString("username") + " - " +
                            rs.getString("status")
            );
        }
    }

    public void getAllUsers() throws Exception {
        String sql = "SELECT * FROM users";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        System.out.println("\n--- DANH SÁCH USER ---");

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " - " +
                            rs.getString("username") + " - " +
                            rs.getString("role") + " - " +
                            rs.getString("status")
            );
        }
    }

    public User login(String username, String password) throws Exception {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, username);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role"),
                    rs.getString("status")
            );
        }
        return null;
    }
}