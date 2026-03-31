package ui;

import model.User;
import service.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();

        try {
            authService.createDefaultAdmin();
        } catch (Exception e) {
            System.out.println("Lỗi tạo admin: " + e.getMessage());
        }

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Đăng ký");
            System.out.println("2. Đăng nhập");
            System.out.println("0. Thoát");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Username: ");
                        String user = sc.nextLine();

                        System.out.print("Password: ");
                        String pass = sc.nextLine();

                        authService.register(user, pass);
                        break;

                    case 2:
                        System.out.print("Username: ");
                        String u = sc.nextLine();

                        System.out.print("Password: ");
                        String p = sc.nextLine();

                        User loginUser = authService.login(u, p);

                        if (loginUser.getRole().equals("CUSTOMER")) {

                            System.out.println(" Xin chào khách hàng");

                            CustomerService customerService = new CustomerService(loginUser);

                            if (!customerService.hasTable()) {
                                boolean success = customerService.chooseTable(sc);

                                if (!success) {
                                    System.out.println(" Không có bàn! Tạm biệt.");
                                    break;
                                }
                            }

                            while (true) {
                                System.out.println("\n===== MENU KHÁCH HÀNG =====");
                                System.out.println("1. Xem menu");
                                System.out.println("2. Gọi món");
                                System.out.println("3. Xem món đã gọi");
                                System.out.println("4. Hủy món");
                                System.out.println("5. Thanh toán");
                                System.out.println("0. Thoát");

                                int c = sc.nextInt();
                                sc.nextLine();

                                try {
                                    switch (c) {
                                        case 1:
                                            customerService.showMenu();
                                            break;

                                        case 2:
                                            customerService.orderFood(sc);
                                            break;

                                        case 3:
                                            customerService.viewOrder();
                                            break;

                                        case 4:
                                            customerService.cancelItem(sc);
                                            break;

                                        case 5:
                                            customerService.checkout();
                                            break;

                                        case 0:
                                            System.out.println(" Hẹn gặp lại!");
                                            break;
                                    }

                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }

                                if (c == 0) break;
                            }
                        }

                        else if (loginUser.getRole().equals("CHEF")) {

                            System.out.println(" Xin chào đầu bếp");

                            ChefService chefService = new ChefService();
                            chefService.run(sc);
                        }

                        else if (loginUser.getRole().equals("MANAGER")) {

                            System.out.println(" Xin chào quản lý");

                            CategoryService categoryService = new CategoryService();
                            TableService tableService = new TableService();
                            MenuItemService menuService = new MenuItemService();

                            while (true) {
                                System.out.println("\n===== MANAGER MENU =====");
                                System.out.println("1. Quản lý CHEF");
                                System.out.println("2. Quản lý USER");
                                System.out.println("3. Quản lý CATEGORY");
                                System.out.println("4. Quản lý BÀN");
                                System.out.println("5. Quản lý MENU");
                                System.out.println("0. Đăng xuất");

                                int c = sc.nextInt();
                                sc.nextLine();

                                switch (c) {

                                    case 1:
                                        while (true) {
                                            System.out.println("\n===== QUẢN LÝ CHEF =====");
                                            System.out.println("1. Tạo CHEF");
                                            System.out.println("2. Xem danh sách CHEF");
                                            System.out.println("0. Quay lại");

                                            int ch = sc.nextInt();
                                            sc.nextLine();

                                            if (ch == 1) {
                                                System.out.print("Username: ");
                                                String newUser = sc.nextLine();

                                                System.out.print("Password: ");
                                                String newPass = sc.nextLine();

                                                authService.createChef(newUser, newPass);

                                            } else if (ch == 2) {
                                                authService.showChefList();
                                            }

                                            if (ch == 0) break;
                                        }
                                        break;

                                    case 2:
                                        while (true) {
                                            System.out.println("\n===== QUẢN LÝ USER =====");
                                            System.out.println("1. Xem tất cả");
                                            System.out.println("2. Ban USER");
                                            System.out.println("0. Quay lại");

                                            int cu = sc.nextInt();
                                            sc.nextLine();

                                            if (cu == 1) {
                                                authService.showAllUsers();

                                            } else if (cu == 2) {
                                                System.out.print("User ID: ");
                                                int uid = sc.nextInt();
                                                sc.nextLine();

                                                authService.banUser(uid);
                                            }

                                            if (cu == 0) break;
                                        }
                                        break;

                                    case 3:
                                        while (true) {
                                            System.out.println("\n===== CATEGORY =====");
                                            System.out.println("1. Thêm");
                                            System.out.println("2. Xem tất cả");
                                            System.out.println("0. Quay lại");

                                            int cc = sc.nextInt();
                                            sc.nextLine();

                                            if (cc == 1) {
                                                System.out.print("Tên: ");
                                                categoryService.addCategory(sc.nextLine());
                                            } else if (cc == 2) {
                                                categoryService.showAll();
                                            }

                                            if (cc == 0) break;
                                        }
                                        break;

                                    case 4:
                                        while (true) {
                                            System.out.println("\n===== BÀN =====");
                                            System.out.println("1. Thêm");
                                            System.out.println("2. Xem");
                                            System.out.println("0. Quay lại");

                                            int tb = sc.nextInt();
                                            sc.nextLine();

                                            if (tb == 1) {
                                                System.out.print("Số bàn: ");
                                                String number = sc.nextLine();

                                                System.out.print("Sức chứa: ");
                                                int cap = sc.nextInt();

                                                tableService.addTable(number, cap);

                                            } else if (tb == 2) {
                                                tableService.showAll();
                                            }

                                            if (tb == 0) break;
                                        }
                                        break;

                                    case 5:
                                        while (true) {
                                            System.out.println("\n===== MENU =====");
                                            System.out.println("1. Thêm");
                                            System.out.println("2. Xem");
                                            System.out.println("0. Quay lại");

                                            int m = sc.nextInt();
                                            sc.nextLine();

                                            if (m == 1) {
                                                System.out.print("Tên: ");
                                                String name = sc.nextLine();

                                                System.out.print("Giá: ");
                                                double price = sc.nextDouble();
                                                sc.nextLine();

                                                categoryService.showAll();
                                                System.out.print("Category ID: ");
                                                int cid = sc.nextInt();

                                                menuService.addItem(name, price, "FOOD", null, cid);

                                            } else if (m == 2) {
                                                menuService.showAll();
                                            }

                                            if (m == 0) break;
                                        }
                                        break;

                                    case 0:
                                        break;
                                }

                                if (c == 0) break;
                            }
                        }

                        break;

                    case 0:
                        System.exit(0);
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}