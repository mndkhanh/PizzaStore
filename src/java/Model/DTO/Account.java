/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.DTO;

/**
 *
 * @author mndkh
 */
public class Account {
    private String accID;
    private String password;
    private String fullName;
    private boolean isStaff;

    public Account() {
    }

    public Account(String accID, String password, String fullName, boolean isStaff) {
        this.accID = accID;
        this.password = password;
        this.fullName = fullName;
        this.isStaff = isStaff;
    }

    public String getAccID() {
        return accID;
    }

    public void setAccID(String accID) {
        this.accID = accID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isIsStaff() {
        return isStaff;
    }

    public void setIsStaff(boolean isStaff) {
        this.isStaff = isStaff;
    }

    @Override
    public String toString() {
        return "Account{" + "accID=" + accID + ", password=" + password + ", fullName=" + fullName + ", isStaff=" + isStaff + '}';
    }
    
    
}
