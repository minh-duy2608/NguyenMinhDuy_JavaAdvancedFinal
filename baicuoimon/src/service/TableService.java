package service;

import dao.TableDAO;
import model.Table;
import java.util.List;

public class TableService {

    private TableDAO dao = new TableDAO();

    public void addTable(String name, int capacity) throws Exception {
        if (name.isEmpty() || capacity <= 0) {
            throw new Exception("Dữ liệu không hợp lệ!");
        }

        dao.add(new Table(name, capacity));
        System.out.println(" Thêm bàn thành công");
    }

    public void showAll() throws Exception {
        List<Table> list = dao.getAll();

        System.out.println("\n--- DANH SÁCH BÀN ---");
        for (Table t : list) {
            System.out.println(
                    t.getId() + " - " +
                            t.getTableName() + " - " +
                            t.getCapacity() + " người - " +
                            t.getStatus()
            );
        }
    }

    public void updateTable(int id, String name, int capacity) throws Exception {
        dao.update(id, name, capacity);
        System.out.println(" Cập nhật thành công");
    }

    public void deleteTable(int id) throws Exception {
        dao.delete(id);
        System.out.println(" Xóa thành công");
    }
}