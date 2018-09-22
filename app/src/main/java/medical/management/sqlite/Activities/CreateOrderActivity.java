package medical.management.sqlite.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import medical.management.sqlite.Adapter.BillAdapter;
import medical.management.sqlite.CommanClasses.myUtils;
import medical.management.sqlite.Database.DatabaseHelper;
import medical.management.sqlite.Model.Bill;
import medical.management.sqlite.Model.Order;
import medical.management.sqlite.R;

public class CreateOrderActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtBillNo, edtCustName, edtQty, edtTotalAmount;
    TextView tvSave;
    Spinner spinnerProduct, spinnerPrices;
    Button btnAdd;
    RecyclerView rvBill;
    RecyclerView.LayoutManager layoutManager;
    DatabaseHelper db;
    BillAdapter adapter;
    List<Bill> billList = new ArrayList<>();
    TextView tvMarqueeText;
    String BillNO;
    Bill bill;
    int UpdatePositionProduct, UpdatePositionOrder;
    boolean isUpdateOrder = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        BillNO = getIntent().getStringExtra("BillNO");
        UpdatePositionOrder = getIntent().getIntExtra("Orderpos", 0);

        InitiaLiazeViews();
        if (getIntent().hasExtra("BillNO")) {
            isUpdateOrder = true;
            billList = db.getAllProduct(BillNO);
            String billNO = billList.get(0).getBillNo();
            String CustName = billList.get(0).getCustomerName();
            edtBillNo.setText(billNO);
            edtCustName.setText(CustName);
            edtBillNo.setKeyListener(null);
            edtCustName.setKeyListener(null);
            edtBillNo.setTextColor(Color.parseColor("#FFAFAFAF"));
            edtCustName.setTextColor(Color.parseColor("#FFAFAFAF"));


            adapter = new BillAdapter(CreateOrderActivity.this, billList, new BillAdapter.MyAdapterListener() {
                @Override
                public void EditClick(View v, int position) {
                    bill = billList.get(position);
                    edtBillNo.setTextColor(Color.parseColor("#FFAFAFAF"));
                    edtCustName.setTextColor(Color.parseColor("#FFAFAFAF"));

                    UpdatePositionProduct = position;
                    btnAdd.setText("Update");
                    edtQty.setText(String.valueOf(billList.get(position).getQty()));
                    edtTotalAmount.setText(String.valueOf(billList.get(position).getTotal_Amount()));


                }

                @Override
                public void DeleteClick(View v, int position) {
                    if (billList.size() == 1) {
                        myUtils.ShowToast("You Can't Delete Last Product");
                    } else {
                        Bill bill = billList.get(position);
                        db.deleteProduct(bill);
                        billList.remove(position);
                        adapter.notifyDataSetChanged();


                    }


                }
            });
            rvBill.setAdapter(adapter);
        } else {
            adapter = new BillAdapter(CreateOrderActivity.this, billList, new BillAdapter.MyAdapterListener() {
                @Override
                public void EditClick(View v, int position) {
                    edtBillNo.setTextColor(Color.parseColor("#FFAFAFAF"));
                    edtCustName.setTextColor(Color.parseColor("#FFAFAFAF"));
                    bill = billList.get(position);
                    UpdatePositionProduct = position;
                    btnAdd.setText("Update");
                    edtQty.setText(String.valueOf(billList.get(position).getQty()));
                    edtTotalAmount.setText(String.valueOf(billList.get(position).getTotal_Amount()));
                }

                @Override
                public void DeleteClick(View v, int position) {
                    if (billList.size() == 1) {
                        myUtils.ShowToast("You Can't Delete Last Product");
                    } else {
                        Bill bill = billList.get(position);
                        db.deleteProduct(bill);
                        billList.remove(position);
                        adapter.notifyDataSetChanged();

                    }

                }
            });
            rvBill.setAdapter(adapter);
        }


        edtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    edtTotalAmount.setText("");
                } else {
                    int Quantity = Integer.parseInt(edtQty.getText().toString());
                    int Product_Price = Integer.parseInt(spinnerPrices.getSelectedItem().toString());
                    int TotalPayable = 0;
                    TotalPayable = Quantity * Product_Price;
                    edtTotalAmount.setText(String.valueOf(TotalPayable));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        spinnerPrices.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    int Quantity = Integer.parseInt(edtQty.getText().toString());
                    int Product_Price = Integer.parseInt(parent.getSelectedItem().toString());
                    int TotalPayable = 0;
                    TotalPayable = Quantity * Product_Price;
                    edtTotalAmount.setText(String.valueOf(TotalPayable));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void InitiaLiazeViews() {
        tvMarqueeText = (TextView) findViewById(R.id.tvMarqueeText);
        tvMarqueeText.setSingleLine(true);
        tvMarqueeText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tvMarqueeText.setSelected(true);
        db = new DatabaseHelper(this);
        tvSave = (TextView) findViewById(R.id.tvSave);
        edtBillNo = (EditText) findViewById(R.id.edtBillNo);
        edtCustName = (EditText) findViewById(R.id.edtCustName);
        spinnerProduct = (Spinner) findViewById(R.id.spinnerProduct);
        spinnerPrices = (Spinner) findViewById(R.id.spinnerPrices);
        edtQty = (EditText) findViewById(R.id.edtQty);
        edtTotalAmount = (EditText) findViewById(R.id.edtTotalAmount);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        rvBill = (RecyclerView) findViewById(R.id.rvBill);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvBill.setLayoutManager(layoutManager);
        btnAdd.setOnClickListener(this);
        tvSave.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        hideKeyboard(CreateOrderActivity.this);

        String Bill_no = edtBillNo.getText().toString();
        String CustName = edtCustName.getText().toString();
        String ProductName = spinnerProduct.getSelectedItem().toString();
        String Price = spinnerPrices.getSelectedItem().toString();
        String Qty = edtQty.getText().toString();

        if (!btnAdd.getText().equals("Update")) {


            if (v == btnAdd) {
                if (Bill_no.isEmpty()) {
                    myUtils.ShowToast("Enter Bill Number");
                } else if (CustName.isEmpty()) {
                    myUtils.ShowToast("Enter Customer Name");
                } else if (ProductName.isEmpty()) {
                    myUtils.ShowToast("Select Product Name");
                } else if (Price.isEmpty()) {
                    myUtils.ShowToast("Select Price");
                } else if (Qty.isEmpty()) {
                    myUtils.ShowToast("Enter Quantity");
                } else {
                    InsertOrder(Bill_no, CustName, ProductName, Price, Qty, edtTotalAmount.getText().toString());
                    edtBillNo.setText(Bill_no);
                    edtCustName.setText(CustName);
                    edtBillNo.setKeyListener(null);
                    edtCustName.setKeyListener(null);
                    edtBillNo.setTextColor(Color.parseColor("#FFAFAFAF"));
                    edtCustName.setTextColor(Color.parseColor("#FFAFAFAF"));
                    edtQty.setText("");
                    edtTotalAmount.setText("");
                }
            }
        }
        if (v == tvSave) {
            if (billList.size() == 0) {
                myUtils.ShowToast("Please Insert Product");
            } else {
                SaveOrder();

            }


        } else if (btnAdd.getText().equals("Update")) {
            bill.setQty(Integer.valueOf(edtQty.getText().toString()));
            bill.setTotal_Amount(Integer.valueOf(edtTotalAmount.getText().toString()));
            bill.setProduct_name(spinnerProduct.getSelectedItem().toString());
            bill.setPrice(Integer.valueOf(spinnerPrices.getSelectedItem().toString()));
            db.updateProduct(bill);

            billList.set(UpdatePositionProduct, bill);
            adapter.notifyDataSetChanged();
            btnAdd.setText("Add");
            edtBillNo.setText(Bill_no);
            edtCustName.setText(CustName);
            edtBillNo.setKeyListener(null);
            edtCustName.setKeyListener(null);
            edtBillNo.setTextColor(Color.parseColor("#FFAFAFAF"));
            edtCustName.setTextColor(Color.parseColor("#FFAFAFAF"));
            edtQty.setText("");
            edtTotalAmount.setText("");

        }
    }

    private void SaveOrder() {
        String billno = billList.get(0).getBillNo();
        String CustomerName = billList.get(0).getCustomerName();
        int Qty = 0;
        int Amount = 0;
        for (int i = 0; i < billList.size(); i++) {
            Qty = Qty + billList.get(i).getQty();
            Amount = Amount + billList.get(i).getTotal_Amount();

        }


        if (isUpdateOrder) {
            Order order = MainActivity.orderList.get(UpdatePositionOrder);
            order.setAmount(Amount);
            order.setQty(Qty);
            db.updateOrder(order);
            MainActivity.orderList.set(UpdatePositionOrder, order);
            MainActivity.adapter.notifyDataSetChanged();

        } else {
            db.insertOrder(billno, CustomerName, String.valueOf(Qty), String.valueOf(Amount));
        }
        Intent intent = new Intent(CreateOrderActivity.this, MainActivity.class);
        startActivity(intent);
        isUpdateOrder = false;

    }

    private void InsertOrder(String bill_no, String custName, String productName, String price, String qty, String totalAmount) {
        long id = db.insertProduct(bill_no, custName, productName, price, qty, totalAmount);
        Bill bill = db.getProduct(id);
        if (bill != null) {
            billList.add(bill);
            adapter.notifyDataSetChanged();
        }
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
