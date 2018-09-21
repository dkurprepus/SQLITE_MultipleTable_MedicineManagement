package medical.management.sqlite.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.List;

import medical.management.sqlite.Model.Bill;
import medical.management.sqlite.R;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.MyViewHolder> {
    private Context context;
    private List<Bill> billList;
    public MyAdapterListener onClickListener;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = (LayoutInflater.from(parent.getContext()).inflate(R.layout.row_bill, parent, false));

        return new MyViewHolder(view);
    }

    public BillAdapter(Context context, List<Bill> billList, MyAdapterListener onClickListener) {
        this.billList = billList;
        this.context = context;
        this.onClickListener = onClickListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bill bill = billList.get(position);
        holder.tvMedicineName.setText(bill.getProduct_name());
        holder.tvPrice.setText(String.valueOf(bill.getPrice()) + " /-");
        holder.tvQty.setText(String.valueOf(bill.getQty()));
        holder.tvTotalAmount.setText(String.valueOf(bill.getTotal_Amount()) + " /-");

    }

    @Override
    public int getItemCount() {
        return billList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMedicineName;
        private TextView tvPrice;
        private Button btnEdit, btnDelete;
        private TextView tvQty;
        private TextView tvTotalAmount;

        public MyViewHolder(View vIew) {
            super(vIew);
            tvMedicineName = (TextView) vIew.findViewById(R.id.tvMedicineName);
            tvPrice = (TextView) vIew.findViewById(R.id.tvPrice);
            btnEdit = (Button) vIew.findViewById(R.id.btnEdit);
            btnDelete = (Button) vIew.findViewById(R.id.btnDelete);
            tvQty = (TextView) vIew.findViewById(R.id.tvQty);
            tvTotalAmount = (TextView) vIew.findViewById(R.id.tvTotalAmount);
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.EditClick(v, getAdapterPosition());
                }
            });
            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.DeleteClick(v, getAdapterPosition());
                }
            });

        }


    }

    public interface MyAdapterListener {

        void EditClick(View v, int position);

        void DeleteClick(View v, int position);
    }
}
