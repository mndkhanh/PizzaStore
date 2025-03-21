package Model.DAO;

import DB.Context.DBContext;
import Model.DTO.Account;
import Model.DTO.Order;
import Model.DTO.OrderDetail;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {

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

    public Order getOrderByID(int ID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Orders WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setInt(1, ID);
        rs = ps.executeQuery();
        if (rs.next()) {
            Order order = new Order(
                    rs.getInt("orderID"),
                    rs.getString("accountID"),
                    rs.getDate("orderDate"),
                    rs.getDate("shippedDate"),
                    rs.getDouble("freight"),
                    rs.getString("shipAddress"),
                    rs.getString("status")
            );
            closeResources();
            return order;
        }
        closeResources();
        return null;
    }

    public Order getUnpaidOrder(String accountID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Orders WHERE accountID = ? AND status = 'UNPAID'";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, accountID);
        rs = ps.executeQuery();
        if (rs.next()) {
            Order order = new Order(
                    rs.getInt("orderID"),
                    rs.getString("accountID"),
                    rs.getDate("orderDate"),
                    rs.getDate("shippedDate"),
                    rs.getDouble("freight"),
                    rs.getString("shipAddress"),
                    rs.getString("status")
            );
            closeResources();
            return order;
        }
        closeResources();
        return null;
    }

    public List<HashMap<String, OrderDetail>> getHistoryListByAccountID(String accountID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "select distinct o.orderID from orders o join orderDetail od on o.orderId = od.OrderID\n"
                + "where o.accountID = ? and status <> 'UNPAID'";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, accountID);
        rs = ps.executeQuery();
        List<HashMap<String, OrderDetail>> list = new ArrayList<>();
        while (rs.next()) {
            list.add(new OrderDetailDAO().getOrderDetailsByOrderID(rs.getInt("orderID")));
        }
        closeResources();
        return list;
    }

    public HashMap<String, OrderDetail> getOrderDetailsOfUnpaidOrderOfAccountID(String accountID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM OrderDetail WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);

        // avoid unpaidOrder to be null
        Order unpaidOrder = new OrderDAO().getUnpaidOrder(accountID);
        if (unpaidOrder == null) {
            closeResources();
            return new HashMap<>();
        }
        ps.setInt(1, new OrderDAO().getUnpaidOrder(accountID).getOrderID());
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

    public boolean createOrder(Order order, HashMap<String, OrderDetail> orderDetails) throws Exception {
        if (orderDetails == null || orderDetails.isEmpty()) {
            return true;
        }
        String accountID = order.getAccountID();
        String shipAddress = order.getShipAddress();

        Order unpaidOrder = getUnpaidOrder(accountID);
        if (unpaidOrder != null) {
            System.out.print("There is unpaid order of this customer ID: " + accountID);
            return false;
        }

        cnn = new DBContext().getConnection();
        String sql = "INSERT INTO Orders (accountID, shipAddress) VALUEs (?, ?)";
        ps = cnn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setString(1, accountID);
        ps.setString(2, shipAddress);
        ps.executeUpdate();
        rs = ps.getGeneratedKeys();
        int orderID = -1;
        if (rs.next()) {
            orderID = rs.getInt(1);
        }
        rs.close();
        ps.close();

        if (orderID == -1) {
            System.out.println("khong tao duoc order id trong database");
            closeResources();
            return false;
        }

        boolean success = new OrderDetailDAO().createOrderDetails(orderID, orderDetails);
        closeResources();
        return success;
    }

    public boolean updateOrder(int orderID, String shipAddress, HashMap<String, OrderDetail> orderDetails) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "UPDATE Orders SET shipAddress = ? WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, shipAddress);
        ps.setInt(2, orderID);
        boolean orderUpdated = ps.executeUpdate() > 0;
        ps.close();

        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        boolean detailsUpdated = orderDetailDAO.updateOrderDetails(orderID, orderDetails);

        closeResources();
        return orderUpdated && detailsUpdated;

    }

    public double getTotalCost(int orderID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT SUM(p.UnitPrice * od.Quantity) + MAX(o.Freight) AS TotalCost\n"
                + "FROM Orders o\n"
                + "JOIN OrderDetail od ON o.OrderID = od.OrderID\n"
                + "JOIN Product p ON od.ProductID = p.ProductID\n"
                + "WHERE o.OrderID = ?;";
        ps = cnn.prepareStatement(sql);
        ps.setInt(1, orderID);
        rs = ps.executeQuery();
        double totalCost = 0;
        if (rs.next()) {
            totalCost = rs.getDouble("TotalCost");
        }
        closeResources();
        return totalCost;
    }

    public boolean synchronizeCart(String accountID, HashMap<String, OrderDetail> cart) throws Exception {
        Account account = new AccountDAO().getAccountByID(accountID);
        if (account == null) {
            System.out.println("not found account");
            return false;
        }
        boolean success = true;
        // if this is staff or the cart attribute in the session scope does not exist, then go ahead
        if (cart != null && !account.isIsStaff()) {
            Order unpaidOrder = getUnpaidOrder(account.getAccID());

            // if the latest unpaid order is still there in the database, then update. otherwise, create new
            if (unpaidOrder != null) {
                success = updateOrderDetails(unpaidOrder.getOrderID(), cart);
            } else {
                success = createOrder(new Order(account.getAccID(), ""), cart);
            }
        }
        closeResources();
        return success;
    }

    public double getFreightByOID(int orderID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "select Freight FROM Orders WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setInt(1, orderID);
        rs = ps.executeQuery();
        double freight = 0;
        if (rs.next()) {
            freight = rs.getDouble("Freight");
        }
        closeResources();
        return freight;
    }

    public int getLatestOrderID(String accountID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT OrderID FROM Orders WHERE accountID = ? AND status = 'UNPAID'";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, accountID);
        rs = ps.executeQuery();
        int orderID = -1;
        if (rs.next()) {
            orderID = rs.getInt("orderID");
        }
        closeResources();
        return orderID;
    }

    public boolean updateOrderDetails(int orderID, HashMap<String, OrderDetail> orderDetails) throws Exception {
        new OrderDetailDAO().deleteAllOrderDetails(orderID);
        OrderDetailDAO orderDetailDAO = new OrderDetailDAO();
        boolean success = true;
        if (!orderDetailDAO.createOrderDetails(orderID, orderDetails)) {
            success = false;
        }
        return success;
    }

    public boolean deleteOrder(int orderID) throws Exception {
        new OrderDetailDAO().deleteAllOrderDetails(orderID);
        cnn = new DBContext().getConnection();
        String sql = "DELETE FROM Orders WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setInt(1, orderID);
        boolean result = ps.executeUpdate() > 0;
        closeResources();
        return result;
    }

    public boolean updateOrderStatus(int orderID, String status) throws Exception {
        if (!status.equals("PAID") && !status.equals("UNPAID") && !status.equals("SUCCESS") && !status.equals("FAILED")) {
            return false;
        }
        cnn = new DBContext().getConnection();
        String sql = "UPDATE Orders SET status = ? WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, orderID);
        boolean result = ps.executeUpdate() > 0;
        closeResources();
        return result;
    }

    public boolean updateOrderShipAddress(int orderID, String shipAddress) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "UPDATE Orders SET shipAddress = ? WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, shipAddress);
        ps.setInt(2, orderID);
        boolean result = ps.executeUpdate() > 0;
        closeResources();
        return result;
    }

    public HashMap<String, OrderDetail> getAfterReOrderList(String accountID, int oid) throws Exception {
        HashMap<String, OrderDetail> eventualList = new OrderDAO().getOrderDetailsOfUnpaidOrderOfAccountID(accountID);
        HashMap<String, OrderDetail> mergedList = new OrderDetailDAO().getOrderDetailsByOrderID(oid);

        for (Map.Entry<String, OrderDetail> entry : mergedList.entrySet()) {
            String productID = entry.getKey();
            OrderDetail newOrder = entry.getValue();

            if (eventualList.containsKey(productID)) {
                OrderDetail existingOrder = eventualList.get(productID);
                existingOrder.setQuantity(existingOrder.getQuantity() + newOrder.getQuantity());
            } else {
                eventualList.put(productID, newOrder);
            }
        }
        return eventualList;
    }

    public boolean updateOrderShippedDate(int oid, Date shippedDate) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "UPDATE Orders SET shippedDate = ? WHERE orderID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setDate(1, shippedDate);
        ps.setInt(2, oid);
        boolean result = ps.executeUpdate() > 0;
        closeResources();
        return result;

    }

    public List<Order> getAllSuccessOrders(Date startDate, Date endDate) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Orders \n"
                + "WHERE LOWER(status) = 'success' \n"
                + "AND shippedDate IS NOT NULL \n"
                + "AND shippedDate BETWEEN ? AND ? \n"
                + "ORDER BY shippedDate DESC;";
        ps = cnn.prepareStatement(sql);
        ps.setDate(1, startDate);
        ps.setDate(2, endDate);
        rs = ps.executeQuery();
        List<Order> list = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order(rs.getInt("orderID"),
                    rs.getString("accountID"),
                    rs.getDate("orderDate"),
                    rs.getDate("shippedDate"),
                    rs.getDouble("freight"),
                    rs.getString("shipAddress"),
                    rs.getString("status"));
            list.add(order);
        }
        closeResources();
        return list;
    }

    public static void main(String[] args) throws Exception {
        Date startDate = Date.valueOf(LocalDate.of(0001, 1, 1)); // Giá trị nhỏ nhất hợp lệ
        Date endDate = Date.valueOf(LocalDate.of(9999, 12, 31)); // Giới hạn theo SQL Server

        System.out.println(new OrderDAO().getAllSuccessOrders(startDate, endDate));
    }

}
