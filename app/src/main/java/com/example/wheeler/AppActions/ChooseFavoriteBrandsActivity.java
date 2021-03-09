package com.example.wheeler.AppActions;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.wheeler.R;
import com.example.wheeler.RecyclerView.HorizontalRecyclerViewAdapter;
import java.util.ArrayList;

public class ChooseFavoriteBrandsActivity extends Fragment {

    View views;
    private ArrayList<String> brandsName = new ArrayList<>();
    private int[] brandImages = {R.drawable.porsche, R.drawable.kia, R.drawable.hyundai, R.drawable.buick, R.drawable.acura,
            R.drawable.lamborghini, R.drawable.infiniti, R.drawable.ford, R.drawable.bmw, R.drawable.mazda, R.drawable.audi,
            R.drawable.maserati, R.drawable.jeep, R.drawable.fiat, R.drawable.mitsubishi, R.drawable.lexus, R.drawable.volkswagen,
            R.drawable.ram, R.drawable.aston_martin, R.drawable.dodge, R.drawable.mini, R.drawable.mclaren, R.drawable.mercedes_benz,
            R.drawable.ferrari, R.drawable.honda, R.drawable.cadillac, R.drawable.chrysler, R.drawable.chevrolet, R.drawable.lincoln,
            R.drawable.bentley, R.drawable.alfa_romeo, R.drawable.subaru, R.drawable.rolls_royce, R.drawable.toyota, R.drawable.land_rover,
            R.drawable.nissan, R.drawable.scion, R.drawable.gmc, R.drawable.volvo, R.drawable.jaguar};
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private Button continueBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        views = inflater.inflate(R.layout.activity_choose_favorite_brands, container, false);

        recyclerView = views.findViewById(R.id.horizontalRecyclerViewID);
        continueBtn = views.findViewById(R.id.continueBtnId);
        continueBtn.setVisibility(View.GONE);
        initImageBitmap();

        return views;
    }

    private void initImageBitmap(){
        brandsName.add("Porsche");
        brandsName.add("Kia");
        brandsName.add("Hyundai");
        brandsName.add("Buick");
        brandsName.add("Acura");
        brandsName.add("Lamborghini");
        brandsName.add("Infiniti");
        brandsName.add("Ford");
        brandsName.add("BMW");
        brandsName.add("Mazda");
        brandsName.add("Audi");
        brandsName.add("Maserati");
        brandsName.add("Jeep");
        brandsName.add("Fiat");
        brandsName.add("Mitsubishi");
        brandsName.add("Lexus");
        brandsName.add("Volkswagen");
        brandsName.add("Ram");
        brandsName.add("Aston-Martin");
        brandsName.add("Dodge");
        brandsName.add("Mini");
        brandsName.add("Bclaren");
        brandsName.add("Mercedes-Benz");
        brandsName.add("Ferrari");
        brandsName.add("Honda");
        brandsName.add("Cadillac");
        brandsName.add("Chrysler");
        brandsName.add("Chevrolet");
        brandsName.add("Lincoln");
        brandsName.add("Bentley");
        brandsName.add("Alfa-Romeo");
        brandsName.add("Subaru");
        brandsName.add("Rolls-Royce");
        brandsName.add("Toyota");
        brandsName.add("Land-Rover");
        brandsName.add("Nissan");
        brandsName.add("Scion");
        brandsName.add("GMC");
        brandsName.add("Volvo");
        brandsName.add("Jaguar");

        initRecyclerView();
    }

    private void initRecyclerView(){
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        HorizontalRecyclerViewAdapter adapter = new HorizontalRecyclerViewAdapter(getContext(), brandsName, brandImages, continueBtn);
        recyclerView.setAdapter(adapter);
    }
}
