package Model.DTO;

import java.io.Serializable;

public class OrderDetail implements Serializable {

    private int orderID;
    private String productID;
    private int quantity;

    public OrderDetail() {
    }

    public OrderDetail(int orderID, String productID) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = 0;
    }

    public OrderDetail(String productID) {
        this.productID = productID;
        this.quantity = 0;
    }

    public OrderDetail(String productID, int quantity) {
        this.productID = productID;
        this.quantity = quantity;
    }

    public OrderDetail(int orderID, String productID, int quantity) {
        this.orderID = orderID;
        this.productID = productID;
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderID=" + orderID + ", productID=" + productID + ", quantity=" + quantity + '}';
    }
}
