package medical.management.sqlite.Model;

public class Bill {
    private int billID;
    private String BillNo;
    private String CustomerName;
    private String Product_name;
    private int Price;
    private int Qty;
    private int Total_Amount;

    public Bill(int billID, String billNo, String customerName, String product_name, int price, int qty, int total_Amount) {
        this.billID = billID;
        BillNo = billNo;
        CustomerName = customerName;
        Product_name = product_name;
        Price = price;
        Qty = qty;
        Total_Amount = total_Amount;
    }

    public Bill() {
    }

    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public String getBillNo() {
        return BillNo;
    }

    public void setBillNo(String billNo) {
        BillNo = billNo;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getProduct_name() {
        return Product_name;
    }

    public void setProduct_name(String product_name) {
        Product_name = product_name;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getTotal_Amount() {
        return Total_Amount;
    }

    public void setTotal_Amount(int total_Amount) {
        Total_Amount = total_Amount;
    }
}
