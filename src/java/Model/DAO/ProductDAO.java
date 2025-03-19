/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DAO;

import DB.Context.DBContext;
import Model.DTO.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mndkh
 */
public class ProductDAO {

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

    public Product getProductByID(String ID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "select * from Product where ProductID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, ID);
        rs = ps.executeQuery();
        if (rs.next()) {
            String productID = rs.getString("ProductID");
            String productName = rs.getString("ProductName");
            String supplierID = rs.getString("SupplierID");
            String categoryID = rs.getString("CategoryID");
            String quantityPerUnit = rs.getString("QuantityPerUnit");
            double unitPrice = rs.getDouble("UnitPrice");
            String img = rs.getString("ProductImage");
            closeResources();
            return new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, img);
        }
        closeResources();
        return null;
    }

    public List<Product> getProductList() throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "select * from Product";
        ps = cnn.prepareStatement(sql);
        rs = ps.executeQuery();
        List<Product> list = new ArrayList();
        Product product = null;
        while (rs.next()) {
            String productID = rs.getString("ProductID");
            String productName = rs.getString("ProductName");
            String supplierID = rs.getString("SupplierID");
            String categoryID = rs.getString("CategoryID");
            String quantityPerUnit = rs.getString("QuantityPerUnit");
            double unitPrice = rs.getDouble("UnitPrice");
            String img = rs.getString("ProductImage");
            product = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, img);
            list.add(product);
        }
        closeResources();
        return list;
    }

    public List<Product> searchByMinMaxPrice(double min, double max) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * "
                + "FROM Product "
                + "where UnitPrice >= ? and UnitPrice <= ? "
                + "ORDER BY UnitPrice asc ";

        ps = cnn.prepareStatement(sql);
        ps.setDouble(1, min);
        ps.setDouble(2, max);
        rs = ps.executeQuery();
        List<Product> list = new ArrayList<>();
        Product product = null;
        while (rs.next()) {
            String productID = rs.getString("ProductID");
            String productName = rs.getString("ProductName");
            String supplierID = rs.getString("SupplierID");
            String categoryID = rs.getString("CategoryID");
            String quantityPerUnit = rs.getString("QuantityPerUnit");
            double unitPrice = rs.getDouble("UnitPrice");
            String img = rs.getString("ProductImage");
            product = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, img);
            list.add(product);
        }
        closeResources();
        return list;
    }

    public List<Product> searchProductByID(String searchValue) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM product "
                + "WHERE ProductID like ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, "%" + searchValue + "%");
        rs = ps.executeQuery();
        List<Product> list = new ArrayList<>();
        Product product = null;
        while (rs.next()) {
            String productID = rs.getString("ProductID");
            String productName = rs.getString("ProductName");
            String supplierID = rs.getString("SupplierID");
            String categoryID = rs.getString("CategoryID");
            String quantityPerUnit = rs.getString("QuantityPerUnit");
            double unitPrice = rs.getDouble("UnitPrice");
            String img = rs.getString("ProductImage");
            product = new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, img);
            list.add(product);
        }
        closeResources();
        return list;
    }

    public List<Product> searchProductByName(String searchValue) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Product WHERE ProductName LIKE ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, "%" + searchValue + "%");
        rs = ps.executeQuery();
        List<Product> list = new ArrayList<>();
        while (rs.next()) {
            String productID = rs.getString("ProductID");
            String productName = rs.getString("ProductName");
            String supplierID = rs.getString("SupplierID");
            String categoryID = rs.getString("CategoryID");
            String quantityPerUnit = rs.getString("QuantityPerUnit");
            double unitPrice = rs.getDouble("UnitPrice");
            String img = rs.getString("ProductImage");
            list.add(new Product(productID, productName, supplierID, categoryID, quantityPerUnit, unitPrice, img));
        }
        closeResources();
        return list;
    }

    public boolean updateProduct(Product product) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "UPDATE Product SET ProductName = ?, SupplierID = ?, CategoryID = ?, QuantityPerUnit = ?, UnitPrice = ?, ProductImage = ? WHERE ProductID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, product.getProductName());
        ps.setString(2, product.getSupplierID());
        ps.setString(3, product.getCategoryID());
        ps.setString(4, product.getQuantityPerUnit());
        ps.setDouble(5, product.getUnitPrice());
        ps.setString(6, product.getImg());
        ps.setString(7, product.getProductID());

        int rowsAffected = ps.executeUpdate();
        closeResources();
        return rowsAffected > 0;
    }

    public static void main(String[] args) throws Exception {
        ProductDAO dao = new ProductDAO();
        System.out.println(dao.searchByMinMaxPrice(0, 100));
    }

}
