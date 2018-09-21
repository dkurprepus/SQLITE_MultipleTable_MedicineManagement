package medical.management.sqlite.Model;

public class Order {

    private int OrderID;
    private String p_bill_no;
    private String p_cust_name;
    private int qty;
    private int Amount;

    public Order() {
    }

    public Order(int orderID, String p_bill_no, String p_cust_name, int qty, int amount) {
        OrderID = orderID;
        this.p_bill_no = p_bill_no;
        this.p_cust_name = p_cust_name;
        this.qty = qty;
        Amount = amount;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }


    public String getP_bill_no() {
        return p_bill_no;
    }

    public void setP_bill_no(String p_bill_no) {
        this.p_bill_no = p_bill_no;
    }

    public String getP_cust_name() {
        return p_cust_name;
    }

    public void setP_cust_name(String p_cust_name) {
        this.p_cust_name = p_cust_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }
}
