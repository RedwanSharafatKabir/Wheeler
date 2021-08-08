package com.example.wheeler.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.ModelClass.CarApiData;
import com.example.wheeler.R;
import com.example.wheeler.ViewOrderAddCart.ParticularCarDetails;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewCustomAdapter extends RecyclerView.Adapter<RecyclerViewCustomAdapter.RecyclerViewHolder> {

    Context context;
    List<CarApiData> carApiDataArrayList;

    public RecyclerViewCustomAdapter(Context context, List<CarApiData> carApiDataArrayList) {
        this.context = context;
        this.carApiDataArrayList = carApiDataArrayList;
    }

    public void setData(List<CarApiData> carApiData){
        this.carApiDataArrayList = carApiData;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View carApiDataView = inflater.inflate(R.layout.recycler_view_custom_adapter, parent, false);

        return new RecyclerViewHolder(carApiDataView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        CarApiData carApiData = carApiDataArrayList.get(position);

        holder.horsepowerText.setText("Horsepower: " + carApiData.getHorsepower() + " hp");
        holder.carBrandText.setText("Brand: " + carApiData.getMake());
        holder.carModelText.setText("Model: " + carApiData.getModel());
        holder.priceText.setText("Price: " + carApiData.getPrice() + " $");
        Picasso.get().load(carApiData.getImg_url()).into(holder.imageUrlImageView);
    }

    @Override
    public int getItemCount() {
        return carApiDataArrayList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public ImageView imageUrlImageView;
        public TextView carBrandText, carModelText, horsepowerText, priceText;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            horsepowerText = itemView.findViewById(R.id.horsePowerID);
            carBrandText = itemView.findViewById(R.id.carMakeBrandID);
            carModelText = itemView.findViewById(R.id.carModelID);
            priceText = itemView.findViewById(R.id.priceID);
            imageUrlImageView = itemView.findViewById(R.id.imageUrlID);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            String carImageUrl = carApiDataArrayList.get(position).getImg_url();
            String carId = carApiDataArrayList.get(position).getId();
            String carBrand = carApiDataArrayList.get(position).getMake();
            String carModel = carApiDataArrayList.get(position).getModel();
            String carHorsepower = carApiDataArrayList.get(position).getHorsepower();
            String carPrice = carApiDataArrayList.get(position).getPrice();

            Intent it = new Intent(context, ParticularCarDetails.class);
            it.putExtra("carImageUrl_key", carImageUrl);
            it.putExtra("carId_key", carId);
            it.putExtra("carBrand_key", carBrand);
            it.putExtra("carModel_key", carModel);
            it.putExtra("carHorsepower_key", carHorsepower);
            it.putExtra("carPrice_key", carPrice);
            context.startActivity(it);
        }
    }
}
