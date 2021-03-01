package com.example.wheeler.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wheeler.ModelClass.CarApiData;
import com.example.wheeler.R;
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

        holder.carIdText.setText("Car ID: " + carApiData.getId());
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
        public TextView carIdText, carBrandText, carModelText, horsepowerText, priceText;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            carIdText = itemView.findViewById(R.id.carID);
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
            String carId = carApiDataArrayList.get(position).getId();
            String carBrand = carId + " " + carApiDataArrayList.get(position).getMake();
            String carModel = carBrand + " " + carApiDataArrayList.get(position).getModel();

            Toast.makeText(context, carModel, Toast.LENGTH_SHORT).show();
        }
    }
}
