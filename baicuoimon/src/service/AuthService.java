package service;
import dao.UserDAO;
import model.User;

public class AuthService {

    private UserDAO userDAO = new UserDAO();

    public void register(String username, String password) throws Exception {

        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hash(password));
        user.setRole("CUSTOMER");

        userDAO.register(user);

        System.out.println("Đăng ký thành công!");

    }

    public void createDefaultAdmin() throws Exception {
        User existing = userDAO.findByUsername("admin");

        if (existing == null) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(PasswordUtil.hash("123"));
            admin.setRole("MANAGER");

            userDAO.register(admin);

            System.out.println(" Đã tạo tài khoản ADMIN mặc định (admin/123)");
        }
    }

    public void createChef(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordUtil.hash(password));
        user.setRole("CHEF");

        userDAO.register(user);

        System.out.println(" Tạo CHEF thành công!");
    }

    public void showChefList() throws Exception {
        userDAO.getUsersByRole("CHEF");
    }

    public void banUser(int userId) throws Exception {
        userDAO.banUser(userId);
        System.out.println(" User đã bị khóa!");
    }

    public void showAllUsers() throws Exception {
        userDAO.getAllUsers();
    }

    public User login(String username, String password) throws Exception {

        String hashed = PasswordUtil.hash(password);

        User user = userDAO.login(username, hashed);

        if (user == null) {
            throw new Exception("Sai tài khoản hoặc mật khẩu!");
        }

        if (user.getStatus().equals("BANNED")) {
            throw new Exception("Tài khoản bị khóa!");
        }

        return user;
    }
}