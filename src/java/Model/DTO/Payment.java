package Model.DTO;

public class Payment {
    private int oid;
    private double amount;

    public Payment(int oid, double amount) {
        this.oid = oid;
        this.amount = amount;
    }

    public int getOid() { return oid; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return "Payment{" + "oid=" + oid + ", amount=" + amount + '}';
    }
    
    
}