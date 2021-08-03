package com.example.wheeler.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheeler.ModelClass.StoreCartList;
import com.example.wheeler.R;
import com.example.wheeler.ViewOrderAddCart.ParticularCarDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CartListCustomAdapter extends RecyclerView.Adapter<CartListCustomAdapter.MyViewHolder> {

    public Context context;
    public ArrayList<StoreCartList> storeCartLists;
    public DatabaseReference cartReference;

    public CartListCustomAdapter(Context c, ArrayList<StoreCartList> p) {
        context = c;
        storeCartLists = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cart_list_adapter, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String carId = storeCartLists.get(position).getCarId();
        String carBrand = storeCartLists.get(position).getCarBrand();
        String carModel = storeCartLists.get(position).getCarModel();
        String carHorsepower = storeCartLists.get(position).getCarHorsepower();
        int carQuantity = storeCartLists.get(position).getQuantity();
        String carCost = storeCartLists.get(position).getCarFinalPrice();
        String carSinglePrice = storeCartLists.get(position).getCarSinglePrice();
        String carImageUrl = storeCartLists.get(position).getCarImageUrl();
        String userPhone = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        Picasso.get().load(carImageUrl).into(holder.imageView);
        holder.textView1.setText("Brand: " + carBrand);
        holder.textView2.setText("Model: " + carModel);
        holder.textView3.setText("Horsepower: " + carHorsepower);
        holder.textView4.setText("Quantity: " + carQuantity);
        holder.textView5.setText("Cost: " + carCost);

        holder.deleteCarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeFromCartMethod(carId, userPhone);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(context, ParticularCarDetails.class);
                it.putExtra("carImageUrl_key", carImageUrl);
                it.putExtra("carId_key", carId);
                it.putExtra("carBrand_key", carBrand);
                it.putExtra("carModel_key", carModel);
                it.putExtra("carHorsepower_key", carHorsepower);
                it.putExtra("carPrice_key", carSinglePrice);
                it.putExtra("carQuantity_key", String.valueOf(carQuantity));
                it.putExtra("carCost_key", carCost);
                context.startActivity(it);
            }
        });
    }

    private void removeFromCartMethod(String carId, String userPhone){
        cartReference = FirebaseDatabase.getInstance().getReference("Cart Information");
        AlertDialog.Builder alertDialogBuilder;
        alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder.setMessage("Do you want to remove from cart ?");
        alertDialogBuilder.setIcon(R.drawable.exit);
        alertDialogBuilder.setCancelable(false);

        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    cartReference.child(userPhone).child(carId).removeValue();
                    Toast.makeText(context, "Car removed from cart", Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.i("Removed ", e.getMessage());
                }
            }
        });

        alertDialogBuilder.setNeutralButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public int getItemCount() {
        return storeCartLists.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView textView1, textView2,textView3, textView4, textView5;
        ImageView imageView, deleteCarBtn;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            imageView = itemView.findViewById(R.id.imageAdapterID);
            deleteCarBtn = itemView.findViewById(R.id.deleteItemBtnId);
            textView1 = itemView.findViewById(R.id.carMakeBrandAdapterID);
            textView2 = itemView.findViewById(R.id.carModelAdapterID);
            textView3 = itemView.findViewById(R.id.horsePowerAdapterID);
            textView4 = itemView.findViewById(R.id.quantityAdapterID);
            textView5 = itemView.findViewById(R.id.priceAdapterID);
        }
    }
}
