package com.example.wheeler.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wheeler.ModelClass.StoreCartList;
import com.example.wheeler.ModelClass.StoreOrderList;
import com.example.wheeler.R;
import com.example.wheeler.ViewOrderAddCart.BuyActivity;
import com.example.wheeler.ViewOrderAddCart.ParticularCarDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class OrderListCustomAdapter extends RecyclerView.Adapter<OrderListCustomAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<StoreOrderList> storeOrderLists;

    public OrderListCustomAdapter(Context c, ArrayList<StoreOrderList> p) {
        context = c;
        storeOrderLists = p;
    }

    @NonNull
    @Override
    public OrderListCustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderListCustomAdapter.MyViewHolder(LayoutInflater.from(context).inflate(R.layout.order_list_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderListCustomAdapter.MyViewHolder holder, int position) {
        String carId = storeOrderLists.get(position).getCarId();
        String carBrand = storeOrderLists.get(position).getCarBrand();
        String carModel = storeOrderLists.get(position).getCarModel();
        String carHorsepower = storeOrderLists.get(position).getCarHorsepower();
        String carQuantity = storeOrderLists.get(position).getQuantity();
        String carCost = storeOrderLists.get(position).getCost();
        String carSinglePrice = storeOrderLists.get(position).getSinglePrice();
        String carImageUrl = storeOrderLists.get(position).getCarImageUrl();

        Picasso.get().load(carImageUrl).into(holder.imageView);
        holder.textView1.setText("Brand: " + carBrand);
        holder.textView2.setText("Model: " + carModel);
        holder.textView3.setText("Quantity: " + carQuantity);
        holder.textView4.setText("Cost: " + carCost);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, BuyActivity.class);
                it.putExtra("carImageUrl_key", carImageUrl);
                it.putExtra("carId_key", carId);
                it.putExtra("carBrand_key", carBrand);
                it.putExtra("carModel_key", carModel);
                it.putExtra("carHorsepower_key", carHorsepower);
                it.putExtra("carPrice_key", carSinglePrice);
                it.putExtra("carQuantity_key", carQuantity);
                it.putExtra("totalFinalPrice_key", carCost);
                context.startActivity(it);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storeOrderLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2, textView3, textView4;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageOrderAdapterID);

            textView1 = itemView.findViewById(R.id.carBrandOrderAdapterID);
            textView2 = itemView.findViewById(R.id.carModelOrderAdapterID);
            textView3 = itemView.findViewById(R.id.quantityOrderAdapterID);
            textView4 = itemView.findViewById(R.id.totalPriceOrderAdapterID);
        }
    }
}
