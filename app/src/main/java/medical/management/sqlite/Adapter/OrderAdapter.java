package medical.management.sqlite.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import medical.management.sqlite.Activities.CreateOrderActivity;
import medical.management.sqlite.Model.Bill;
import medical.management.sqlite.Model.Order;
import medical.management.sqlite.R;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context context;
    private List<Order> orderList;
    public String billNo;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext()).inflate(R.layout.row_order, parent, false));

        return new MyViewHolder(view);
    }

    public OrderAdapter(Context context, List<Order> orderList) {
        this.orderList = orderList;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Order Order = orderList.get(position);
        holder.tvCustomerName.setText(Order.getP_cust_name());
        holder.tvQuantity.setText(String.valueOf(Order.getQty()));
        holder.tvTotalAmount.setText(String.valueOf(Order.getAmount() + " /-"));
        billNo = Order.getP_bill_no();
        holder.tvBillNumber.setText("BIll NO : " + billNo);


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCustomerName, tvBillNumber;
        private TextView tvQuantity;
        private TextView tvTotalAmount;

        public MyViewHolder(View vIew) {
            super(vIew);
            tvBillNumber = (TextView) vIew.findViewById(R.id.tvBillNumber);
            tvCustomerName = (TextView) vIew.findViewById(R.id.tvCustomerName);
            tvQuantity = (TextView) vIew.findViewById(R.id.tvQuantity);
            tvTotalAmount = (TextView) vIew.findViewById(R.id.tvTotalAmount);
            vIew.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, CreateOrderActivity.class);
            int pos = getAdapterPosition();
            intent.putExtra("BillNO", orderList.get(pos).getP_bill_no());
            intent.putExtra("Orderpos", getAdapterPosition());
            context.startActivity(intent);


        }
    }
}
