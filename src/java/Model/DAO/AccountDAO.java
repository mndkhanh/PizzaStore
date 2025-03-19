package Model.DAO;

import DB.Context.DBContext;
import Model.DTO.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAO {

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

    public Account login(String accID, String password) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT * FROM Account WHERE AccountID = ? AND Password = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, accID);
        ps.setString(2, password);
        rs = ps.executeQuery();
        if (rs.next()) {
            String fullName = rs.getString("FullName");
            boolean isStaff = rs.getBoolean("isStaff");
            closeResources();
            return new Account(accID, password, fullName, isStaff);
        }
        closeResources();
        return null;
    }

    public boolean createAccount(String accID, String password, String fullName, boolean isStaff) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "INSERT INTO Account (AccountID, Password, FullName, isStaff) VALUES (?, ?, ?, ?)";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, accID);
        ps.setString(2, password);
        ps.setString(3, fullName);
        ps.setBoolean(4, isStaff);
        boolean success = ps.executeUpdate() > 0;
        closeResources();
        return success;
    }

    public boolean createAccount(Account newAcc) throws Exception {
        return createAccount(newAcc.getAccID(), newAcc.getPassword(), newAcc.getFullName(), newAcc.isIsStaff());
    }

    public boolean isAccExist(String accID) throws Exception {
        cnn = new DBContext().getConnection();
        String sql = "SELECT 1 FROM Account WHERE AccountID = ?";
        ps = cnn.prepareStatement(sql);
        ps.setString(1, accID);
        rs = ps.executeQuery();
        boolean exists = rs.next();
        closeResources();
        return exists;
    }

    public static void main(String[] args) throws Exception {
        AccountDAO dao = new AccountDAO();
        System.out.println(dao.login("asdf", "asdf"));
    }
}
