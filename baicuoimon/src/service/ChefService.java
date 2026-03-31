package service;

import dao.OrderItemDAO;

import java.util.Scanner;

public class ChefService {

    private OrderItemDAO itemDAO = new OrderItemDAO();

    public void run(Scanner sc) throws Exception {

        while (true) {
            System.out.println("\n===== CHEF MENU =====");
            System.out.println("1. Xem món cần làm");
            System.out.println("2. Cập nhật trạng thái");
            System.out.println("0. Đăng xuất");

            int c = sc.nextInt();
            sc.nextLine();

            if (c == 1) {
                itemDAO.showPendingItems();

            } else if (c == 2) {
                System.out.print("ID món: ");
                int id = sc.nextInt();
                sc.nextLine();

                System.out.println("1. COOKING");
                System.out.println("2. READY");
                System.out.println("3. SERVED");

                int choice = sc.nextInt();
                sc.nextLine();

                String status = "";

                if (choice == 1) {
                    status = "COOKING";
                } else if (choice == 2) {
                    status = "READY";
                } else if (choice == 3) {
                    status = "SERVED";
                } else {
                    System.out.println(" Lựa chọn không hợp lệ!");
                    continue;
                }

                itemDAO.updateStatus(id, status);
            }

            if (c == 0) break;
        }
    }
}