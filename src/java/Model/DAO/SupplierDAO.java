package Model.DAO;

import DB.Context.DBContext;
import Model.DTO.Supplier;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO {

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

    public String getSupplierNameByID(String supplierID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT CompanyName FROM Supplier WHERE SupplierID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, supplierID);
        rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("CompanyName");
        }
        return "Unknown Supplier";
    }

    public Supplier getSupplierByID(String ID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Supplier WHERE SupplierID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, ID);
        rs = ps.executeQuery();
        if (rs.next()) {
            String supplierID = rs.getString("SupplierID");
            String companyName = rs.getString("CompanyName");
            String address = rs.getString("Address");
            String phone = rs.getString("Phone");
            closeResources();
            return new Supplier(supplierID, companyName, address, phone);
        }
        closeResources();
        return null;
    }

    public List<Supplier> getSupplierList() throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Supplier";
        ps = cnn.prepareStatement(sql);
        rs = ps.executeQuery();
        List<Supplier> list = new ArrayList<>();
        while (rs.next()) {
            String supplierID = rs.getString("SupplierID");
            String companyName = rs.getString("CompanyName");
            String address = rs.getString("Address");
            String phone = rs.getString("Phone");
            list.add(new Supplier(supplierID, companyName, address, phone));
        }
        closeResources();
        return list;
    }
}
