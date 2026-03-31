package dao;

import model.MenuItem;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    public void add(MenuItem item) throws Exception {
        String sql = "INSERT INTO menu_items(name, price, type, stock, category_id) VALUES (?, ?, ?, ?, ?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, item.getName());
        ps.setDouble(2, item.getPrice());
        ps.setString(3, item.getType());
        ps.setObject(4, item.getStock());
        ps.setInt(5, item.getCategoryId());

        ps.executeUpdate();
    }

    public List<MenuItem> getAll() throws Exception {
        List<MenuItem> list = new ArrayList<>();

        String sql = "SELECT * FROM menu_items";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new MenuItem(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("type"),
                    (Integer) rs.getObject("stock"),
                    rs.getString("status"),
                    rs.getInt("category_id")
            ));
        }

        return list;
    }


    public List<String> getAllWithCategory() throws Exception {
        List<String> list = new ArrayList<>();

        String sql = "SELECT m.*, c.name AS category_name " +
                "FROM menu_items m " +
                "JOIN category c ON m.category_id = c.id";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String row = String.format(
                    "%d - %s - %.0fđ - Danh mục: %s",
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getString("category_name")
            );

            list.add(row);
        }

        return list;
    }

    public void update(int id, String name, double price) throws Exception {
        String sql = "UPDATE menu_items SET name = ?, price = ? WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.setDouble(2, price);
        ps.setInt(3, id);

        ps.executeUpdate();
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM menu_items WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();
    }
}