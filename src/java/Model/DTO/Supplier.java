package Model.DTO;

/**
 * Supplier DTO class
 */
public class Supplier {

    private String supplierID;
    private String companyName;
    private String address;
    private String phone;

    // Constructor không tham số
    public Supplier() {
    }

    // Constructor đầy đủ tham số
    public Supplier(String supplierID, String companyName, String address, String phone) {
        this.supplierID = supplierID;
        this.companyName = companyName;
        this.address = address;
        this.phone = phone;
    }

    // Getter và Setter
    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Phương thức toString()
    @Override
    public String toString() {
        return "Supplier{"
                + "supplierID='" + supplierID + '\''
                + ", companyName='" + companyName + '\''
                + ", address='" + address + '\''
                + ", phone='" + phone + '\''
                + '}';
    }
}
