package dao;

import model.Category;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {


    public void add(Category category) throws Exception {
        String sql = "INSERT INTO category(name) VALUES (?)";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, category.getName());
        ps.executeUpdate();
    }


    public List<Category> getAll() throws Exception {
        List<Category> list = new ArrayList<>();

        String sql = "SELECT * FROM category";
        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Category c = new Category(
                    rs.getInt("id"),
                    rs.getString("name")
            );
            list.add(c);
        }

        return list;
    }

    public void update(int id, String name) throws Exception {
        String sql = "UPDATE category SET name = ? WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, name);
        ps.setInt(2, id);

        ps.executeUpdate();
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM category WHERE id = ?";

        Connection conn = DBConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setInt(1, id);
        ps.executeUpdate();
    }
}