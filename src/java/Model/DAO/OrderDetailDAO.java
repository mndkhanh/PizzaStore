package Model.DAO;

import DB.Context.DBContext;
import Model.DTO.Order;
import Model.DTO.OrderDetail;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author mndkh
 */
public class OrderDetailDAO {

    private Connection cnn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;

    private void closeResources() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (ps != null) {
            ps.close();
        }
        if (cnn != null) {
            cnn.close();
        }
    }

    public HashMap<String, OrderDetail> getOrderDetailsByOrderID(int orderID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM OrderDetail WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setInt(1, orderID);
        rs = ps.executeQuery();
        HashMap<String, OrderDetail> orderDetails = new HashMap<>();
        while (rs.next()) {
            OrderDetail detail = new OrderDetail(
                    rs.getInt("orderID"),
                    rs.getString("productID"),
                    rs.getInt("quantity")
            );
            orderDetails.put(detail.getProductID(), detail);
        }
        closeResources();
        return orderDetails;
    }

    public boolean createOrderDetails(int orderID, HashMap<String, OrderDetail> orderDetails) throws Exception {
        if (orderDetails == null || orderDetails.isEmpty()) {
            return true;
        }
        cnn = new DBContext().getConnection();
        StringBuilder sql = new StringBuilder("INSERT INTO OrderDetail (orderID, productID, quantity) VALUES ");
        boolean first = true;
        for (OrderDetail detail : orderDetails.values()) {
            if (!first) {
                sql.append(",");
            }
            sql.append("(" + orderID + ", '" + detail.getProductID() + "', " + detail.getQuantity() + ")");
            first = false;
        }
        Statement stmt = cnn.createStatement();
        int rowsInserted = stmt.executeUpdate(sql.toString());
        stmt.close();
        closeResources();
        return rowsInserted > 0;
    }

    public boolean deleteAllOrderDetails(int orderID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "DELETE FROM OrderDetail where orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setInt(1, orderID);
        boolean result = ps.executeUpdate() > 0;
        closeResources();
        return result;
    }

    public HashMap<Integer, ArrayList<OrderDetail>> getAllOrderDetails() throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "select * from orderDetail";
        ps = cnn.prepareStatement(sql);
        rs = ps.executeQuery();
        HashMap<Integer, ArrayList<OrderDetail>> list = new HashMap<>();
        
        while (rs.next()) {
            String pid = rs.getString("ProductID");
            int oid = rs.getInt("OrderID");
            int quantity = rs.getInt("Quantity");
            OrderDetail orderDetail = new OrderDetail(oid, pid, quantity);
            if(list.containsKey(oid)) {
                list.get(oid).add(orderDetail);
            } else {
                list.put(oid, new ArrayList<>());
                list.get(oid).add(orderDetail);
            }
        }
        closeResources();
        return list;
    }

    public boolean updateOrderDetail(OrderDetail detail) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "UPDATE OrderDetail SET quantity = ? WHERE orderID = ? AND productID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setInt(1, detail.getQuantity());
        ps.setInt(2, detail.getOrderID());
        ps.setString(3, detail.getProductID());
        boolean result = ps.executeUpdate() > 0;
        closeResources();
        return result;
    }

    public boolean updateOrderDetails(int orderID, HashMap<String, OrderDetail> orderDetails) throws Exception {
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        boolean success = true;

        for (OrderDetail detail : orderDetails.values()) {
            if (!orderDetailDAO.updateOrderDetail(detail)) {
                success = false;
            }
        }

        return success;
    }

    public static void main(String[] args) throws Exception {
        System.out.println(new OrderDetailDAO().getAllOrderDetails());
    }

}

