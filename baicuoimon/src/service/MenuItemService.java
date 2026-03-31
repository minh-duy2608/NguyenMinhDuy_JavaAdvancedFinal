package service;

import dao.MenuItemDAO;
import model.MenuItem;
import java.util.List;

public class MenuItemService {

    private MenuItemDAO dao = new MenuItemDAO();

    public void addItem(String name, double price, String type, Integer stock, int categoryId) throws Exception {
        if (name.isEmpty() || price <= 0) {
            throw new Exception("Dữ liệu không hợp lệ!");
        }

        dao.add(new MenuItem(name, price, type, stock, categoryId));
        System.out.println(" Thêm món thành công");
    }

    public void showAll() throws Exception {
        List<String> list = dao.getAllWithCategory();

        System.out.println("\n--- MENU ---");
        for (String s : list) {
            System.out.println(s);
        }
    }

    public void updateItem(int id, String name, double price) throws Exception {
        dao.update(id, name, price);
        System.out.println(" Cập nhật thành công");
    }

    public void deleteItem(int id) throws Exception {
        dao.delete(id);
        System.out.println(" Xóa thành công");
    }
}