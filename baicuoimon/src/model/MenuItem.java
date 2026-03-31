package model;

public class MenuItem {
    private int id;
    private String name;
    private double price;
    private String type;
    private Integer stock;
    private String status;
    private int categoryId;

    public MenuItem() {}

    public MenuItem(int id, String name, double price, String type, Integer stock, String status, int categoryId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.type = type;
        this.stock = stock;
        this.status = status;
        this.categoryId = categoryId;
    }

    public MenuItem(String name, double price, String type, Integer stock, int categoryId) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getType() { return type; }
    public Integer getStock() { return stock; }
    public String getStatus() { return status; }
    public int getCategoryId() { return categoryId; }
}