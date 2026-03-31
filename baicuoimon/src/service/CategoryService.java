package service;

import dao.CategoryDAO;
import model.Category;
import java.util.List;

public class CategoryService {

    private CategoryDAO dao = new CategoryDAO();

    public void addCategory(String name) throws Exception {
        if (name.isEmpty()) {
            throw new Exception("Tên category không được để trống!");
        }

        dao.add(new Category(name));
        System.out.println(" Thêm category thành công!");
    }

    public void showAll() throws Exception {
        List<Category> list = dao.getAll();

        System.out.println("\n--- DANH SÁCH CATEGORY ---");
        for (Category c : list) {
            System.out.println(c.getId() + " - " + c.getName());
        }
    }

    public void updateCategory(int id, String name) throws Exception {
        dao.update(id, name);
        System.out.println(" Cập nhật thành công!");
    }

    public void deleteCategory(int id) throws Exception {
        dao.delete(id);
        System.out.println(" Xóa thành công!");
    }
}