package medical.management.sqlite.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import medical.management.sqlite.Model.Bill;
import medical.management.sqlite.Model.Order;


public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "medicine_db";

    //Fields for Bill Table
    private static final String TABLE_BILL = "TABLE_BILL";
    private static final String BILL_ID = "BILL_ID";
    private static final String BILL_NO = "BILL_NO";
    private static final String CUSTOMER_NAME = "CUSTOMER_NAME";
    private static final String PRODUCT_NAME = "PRODUCT_NAME";
    private static final String PRODUCT_PRICE = "PRODUCT_PRICE";
    private static final String PRODUCT_QTY = "PRODUCT_QTY";
    private static final String TOTAL_AMOUNT = "TOTAL_AMOUNT";

    //fields for Order Table

    private static String TABLE_ORDER = "TABLE_ORDER";
    private static String ORDER_ID = "ORDER_ID";
    //private static String P_BILL_ID = "P_BILL_ID";
    private static String P_BILL_NO = "P_BILL_NO";
    private static String P_CUSTOMER_NAME = "P_CUSTOMER_NAME";
    private static String QTY = "QTY";
    private static String AMOUNT = "Amount";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_BILL + "("
                + BILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BILL_NO + " TEXT," + PRODUCT_NAME + " TEXT," + PRODUCT_PRICE
                + " TEXT," + PRODUCT_QTY + ""
                + " TEXT," + TOTAL_AMOUNT + " TEXT," + CUSTOMER_NAME
                + ")";
        String CREATE_TABLE_ORDER = "CREATE TABLE " + TABLE_ORDER + "("
                + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                /*+ P_BILL_ID + " TEXT,"*/ + P_BILL_NO + " TEXT ," + P_CUSTOMER_NAME
                + " TEXT," + QTY + ""
                + " TEXT," + AMOUNT
                + ")";


        // create notes table
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_TABLE_ORDER);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDER);

        // Create tables again
        onCreate(db);
    }

    public long insertProduct(String bill_no, String custName, String productName, String price, String qty, String totalAmount) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(BILL_NO, bill_no);
        values.put(PRODUCT_NAME, productName);
        values.put(CUSTOMER_NAME, custName);
        values.put(PRODUCT_PRICE, price);
        values.put(PRODUCT_QTY, qty);
        values.put(TOTAL_AMOUNT, totalAmount);

        // insert row
        long id = db.insert(TABLE_BILL, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertOrder(/*String bill_id,*/ String bill_no, String custName, String qty, String totalAmount) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        // values.put(P_BILL_ID, bill_id);
        values.put(P_BILL_NO, bill_no);
        values.put(P_CUSTOMER_NAME, custName);
        values.put(QTY, qty);
        values.put(AMOUNT, totalAmount);

        // insert row
        long id = db.insert(TABLE_ORDER, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public Bill getProduct(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_BILL,
                new String[]{BILL_ID, BILL_NO, PRODUCT_NAME, CUSTOMER_NAME, PRODUCT_PRICE, PRODUCT_QTY, TOTAL_AMOUNT},
                BILL_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Bill bill = new Bill(
                cursor.getInt(cursor.getColumnIndex(BILL_ID)),
                cursor.getString(cursor.getColumnIndex(BILL_NO)),
                cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME)),
                cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)),
                cursor.getInt(cursor.getColumnIndex(PRODUCT_PRICE)),
                cursor.getInt(cursor.getColumnIndex(PRODUCT_QTY)),
                cursor.getInt(cursor.getColumnIndex(TOTAL_AMOUNT)));

        // close the db connection
        cursor.close();

        return bill;
    }

    public Order getOrder(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ORDER,
                new String[]{ORDER_ID/*, P_BILL_ID*/, P_BILL_NO, P_CUSTOMER_NAME, QTY, AMOUNT},
                ORDER_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Order order = new Order(
                cursor.getInt(cursor.getColumnIndex(ORDER_ID)),
                //cursor.getInt(cursor.getColumnIndex(P_BILL_ID)),
                cursor.getString(cursor.getColumnIndex(P_BILL_NO)),
                cursor.getString(cursor.getColumnIndex(P_CUSTOMER_NAME)),
                cursor.getInt(cursor.getColumnIndex(QTY)),
                cursor.getInt(cursor.getColumnIndex(AMOUNT)));

        // close the db connection
        cursor.close();

        return order;
    }

    public List<Bill> getAllProduct(String billNo) {
        List<Bill> Products = new ArrayList<>();

        // Select All Query
        //String selectQuery = "SELECT  * FROM " + TABLE_BILL +" WHERE "+BILL_NO+  " = '" + billNo + "'";;
        String selectQuery = "SELECT  * FROM " + TABLE_BILL + " WHERE " + BILL_NO + " = ?";
        String[] args = {billNo};

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, args);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Bill bill = new Bill();
                bill.setBillID(cursor.getInt(cursor.getColumnIndex(BILL_ID)));
                bill.setBillNo(cursor.getString(cursor.getColumnIndex(BILL_NO)));
                bill.setCustomerName(cursor.getString(cursor.getColumnIndex(CUSTOMER_NAME)));
                bill.setProduct_name(cursor.getString(cursor.getColumnIndex(PRODUCT_NAME)));
                bill.setPrice(cursor.getInt(cursor.getColumnIndex(PRODUCT_PRICE)));
                bill.setQty(cursor.getInt(cursor.getColumnIndex(PRODUCT_QTY)));
                bill.setTotal_Amount(cursor.getInt(cursor.getColumnIndex(TOTAL_AMOUNT)));

                Products.add(bill);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return Products;
    }

    public List<Order> getAllOrder() {
        List<Order> Orders = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_ORDER + " ORDER BY " +
                ORDER_ID ;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setOrderID(cursor.getInt(cursor.getColumnIndex(ORDER_ID)));
                //order.setP_bill_id(cursor.getInt(cursor.getColumnIndex(P_BILL_ID)));
                order.setP_bill_no(cursor.getString(cursor.getColumnIndex(P_BILL_NO)));
                order.setP_cust_name(cursor.getString(cursor.getColumnIndex(P_CUSTOMER_NAME)));
                order.setQty(cursor.getInt(cursor.getColumnIndex(QTY)));
                order.setAmount(cursor.getInt(cursor.getColumnIndex(AMOUNT)));

                Orders.add(order);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return Orders;
    }


    public int getOrderCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ORDER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();


        // return count
        return count;
    }

    public int updateProduct(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(BILL_NO, bill.getBillNo());
        values.put(CUSTOMER_NAME, bill.getCustomerName());
        values.put(PRODUCT_NAME, bill.getProduct_name());
        values.put(PRODUCT_PRICE, bill.getPrice());
        values.put(PRODUCT_QTY, bill.getQty());
        values.put(TOTAL_AMOUNT, bill.getTotal_Amount());

        // updating row
        return db.update(TABLE_BILL, values, BILL_ID + " = ?",
                new String[]{String.valueOf(bill.getBillID())});
    }

    public int updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(P_BILL_NO, order.getP_bill_no());
        values.put(P_CUSTOMER_NAME, order.getP_cust_name());
        values.put(QTY, order.getQty());
        values.put(AMOUNT, order.getAmount());

        // updating row
        return db.update(TABLE_ORDER, values, ORDER_ID + " = ?",
                new String[]{String.valueOf(order.getOrderID())});
    }

    public void deleteProduct(Bill bill) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_BILL, BILL_ID + " = ?",
                new String[]{String.valueOf(bill.getBillID())});
        db.close();
    }
}
