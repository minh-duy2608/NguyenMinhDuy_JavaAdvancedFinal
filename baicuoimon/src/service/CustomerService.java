package service;

import model.User;
import dao.MenuItemDAO;
import dao.TableDAO;
import dao.OrderDAO;
import dao.OrderItemDAO;

import java.util.Scanner;

public class CustomerService {

    private User user;
    private MenuItemDAO menuDAO = new MenuItemDAO();
    private TableDAO tableDAO = new TableDAO();
    private OrderDAO orderDAO = new OrderDAO();
    private OrderItemDAO itemDAO = new OrderItemDAO();

    private int currentTableId = -1;
    private int currentOrderId = -1;

    public CustomerService(User user) throws Exception {
        this.user = user;

        int existingOrder = orderDAO.getActiveOrderByUser(user.getId());

        if (existingOrder != -1) {
            currentOrderId = existingOrder;
            currentTableId = orderDAO.getTableIdByOrder(existingOrder);

            System.out.println(" Bạn đã có bàn trước đó!");
        }
    }

    public void showMenu() throws Exception {
        System.out.println("\n--- MENU ---");
        menuDAO.getAllWithCategory().forEach(System.out::println);
    }

    public boolean chooseTable(Scanner sc) throws Exception {

        if (currentTableId != -1) {
            System.out.println(" Bạn đã có bàn rồi!");
            return true;
        }

        if (!tableDAO.hasAvailableTable()) {
            System.out.println(" Hiện tại đã hết bàn!");
            System.out.println(" Vui lòng quay lại sau!");
            return false;
        }

        while (true) {

            tableDAO.showAll();

            System.out.print("Chọn bàn (ID): ");
            int tableId = sc.nextInt();
            sc.nextLine();

            String status = tableDAO.getStatus(tableId);

            if (status == null) {
                System.out.println(" Bàn không tồn tại!");
                continue;
            }

            if (status.equals("OCCUPIED")) {
                System.out.println(" Bàn này đã được đặt rồi!");
                continue;
            }

            tableDAO.updateStatus(tableId, "OCCUPIED");

            currentTableId = tableId;

            currentOrderId = orderDAO.createOrder(user.getId(), currentTableId);

            System.out.println(" Chọn bàn thành công!");
            return true;
        }
    }

    public boolean hasTable() {
        return currentTableId != -1;
    }

    public void orderFood(Scanner sc) throws Exception {

        if (currentOrderId == -1) {
            System.out.println(" Bạn chưa chọn bàn!");
            return;
        }

        showMenu();

        System.out.print("Nhập ID món: ");
        int itemId = sc.nextInt();

        System.out.print("Số lượng: ");
        int qty = sc.nextInt();
        sc.nextLine();

        itemDAO.addItem(currentOrderId, itemId, qty);

        System.out.println(" Gọi món thành công!");
    }

    public void viewOrder() throws Exception {

        if (currentOrderId == -1) {
            System.out.println(" Chưa có order!");
            return;
        }

        itemDAO.showByOrder(currentOrderId);
    }

    public void cancelItem(Scanner sc) throws Exception {

        if (currentOrderId == -1) {
            System.out.println(" Chưa có order!");
            return;
        }

        System.out.print("Nhập ID order_item: ");
        int id = sc.nextInt();
        sc.nextLine();

        boolean success = itemDAO.cancelItem(id);

        if (success) {
            System.out.println(" Hủy món thành công!");
        } else {
            System.out.println(" Không thể hủy! (món đã được chế biến)");
        }
    }

    public void checkout() throws Exception {

        if (currentOrderId == -1) {
            System.out.println(" Không có đơn để thanh toán!");
            return;
        }

        itemDAO.printBill(currentOrderId);

        double total = orderDAO.getTotalAmount(currentOrderId);

        System.out.println("--------------------------");
        System.out.println(" TỔNG TIỀN: " + total + "đ");
        System.out.println("==========================");

        orderDAO.finishOrder(currentOrderId);

        tableDAO.updateStatus(currentTableId, "EMPTY");

        currentOrderId = -1;
        currentTableId = -1;

        System.out.println(" Thanh toán thành công!");
    }
}