package Model.DTO;

import java.sql.Date;

/**
 *
 * @author mndkh
 */
public class Order {

    private int orderID;
    private String accountID;
    private Date orderDate;
    private Date shippedDate;
    private double freight;
    private String shipAddress;
    private String status;

    // Default constructor
    public Order() {
    }

    // Full constructor
    public Order(int orderID, String accountID, Date orderDate, Date shippedDate, double freight, String shipAddress, String status) {
        this.orderID = orderID;
        this.accountID = accountID;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        this.freight = freight;
        this.shipAddress = shipAddress;
        this.status = status;
    }

    // Full constructor without orderID
    public Order(String accountID, Date orderDate, Date shippedDate, double freight, String shipAddress, String status) {
        this.accountID = accountID;
        this.orderDate = orderDate;
        this.shippedDate = shippedDate;
        this.freight = freight;
        this.shipAddress = shipAddress;
        this.status = status;
    }

    // Only accountID and shipAddress
    public Order(String accountID, String shipAddress) {
        this.accountID = accountID;
        this.shipAddress = shipAddress;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
        this.shippedDate = shippedDate;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public String getShipAddress() {
        return shipAddress;
    }

    public void setShipAddress(String shipAddress) {
        this.shipAddress = shipAddress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" + "orderID=" + orderID + ", accountID=" + accountID + ", orderDate=" + orderDate + ", shippedDate=" + shippedDate + ", freight=" + freight + ", shipAddress=" + shipAddress + ", status=" + status + '}';
    }
}
