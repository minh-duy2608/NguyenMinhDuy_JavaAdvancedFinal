package model;

public class Table {
    private int id;
    private String tableName;
    private int capacity;
    private String status;

    public Table() {}

    public Table(int id, String tableName, int capacity, String status) {
        this.id = id;
        this.tableName = tableName;
        this.capacity = capacity;
        this.status = status;
    }

    public Table(String tableName, int capacity) {
        this.tableName = tableName;
        this.capacity = capacity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTableName() { return tableName; }
    public void setTableName(String tableName) { this.tableName = tableName; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}