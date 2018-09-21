package medical.management.sqlite.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import medical.management.sqlite.Adapter.OrderAdapter;
import medical.management.sqlite.Database.DatabaseHelper;
import medical.management.sqlite.Model.Order;
import medical.management.sqlite.R;

public class MainActivity extends AppCompatActivity {
    ImageView ivAddOrder;
    DatabaseHelper db;
  public static   List<Order> orderList = new ArrayList<>();
   public static OrderAdapter adapter;
    RecyclerView rv_Order;
    TextView tvEmptyOrder;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(this);
        InitialiazeViews();
        ivAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateOrderActivity.class));
            }
        });
    }

    private void InitialiazeViews() {

        tvEmptyOrder = (TextView) findViewById(R.id.tvEmptyOrder);
        ivAddOrder = (ImageView) findViewById(R.id.ivAddOrder);
        rv_Order = (RecyclerView) findViewById(R.id.rv_Order);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_Order.setLayoutManager(layoutManager);
    }

    @Override
    protected void onResume() {
        super.onResume();
         orderList = db.getAllOrder();
        if (orderList.size() == 0) {
            tvEmptyOrder.setVisibility(View.VISIBLE);
        } else {
            tvEmptyOrder.setVisibility(View.GONE);
        }
        adapter = new OrderAdapter(MainActivity.this, orderList);
        rv_Order.setAdapter(adapter);


    }
}
