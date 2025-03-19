package Model.DAO;

import DB.Context.DBContext;
import Model.DTO.Category;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

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

    public String getCategoryNameByID(String categoryID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT CategoryName FROM Category WHERE CategoryID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, categoryID);
        rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getString("CategoryName");
        }
        return "Unknown Category";
    }

    public Category getCategoryByID(String ID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Category WHERE CategoryID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, ID);
        rs = ps.executeQuery();
        if (rs.next()) {
            String categoryID = rs.getString("CategoryID");
            String categoryName = rs.getString("CategoryName");
            String description = rs.getString("Description");
            closeResources();
            return new Category(categoryID, categoryName, description);
        }
        closeResources();
        return null;
    }

    public List<Category> getCategoryList() throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Category";
        ps = cnn.prepareStatement(sql);
        rs = ps.executeQuery();
        List<Category> list = new ArrayList<>();
        while (rs.next()) {
            String categoryID = rs.getString("CategoryID");
            String categoryName = rs.getString("CategoryName");
            String description = rs.getString("Description");
            list.add(new Category(categoryID, categoryName, description));
        }
        closeResources();
        return list;
    }
}
