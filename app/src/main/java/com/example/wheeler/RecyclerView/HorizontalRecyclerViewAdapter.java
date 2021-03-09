package com.example.wheeler.RecyclerView;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wheeler.AppActions.ChooseCarModelActivity;
import com.example.wheeler.AppActions.ChooseFavoriteBrandsActivity;
import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.R;

import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.viewHolder>{

    private ArrayList<String> brandsName;
    private int[] brandImages;
    private Context context;
    private Button continueBtn;
    String selectedBrandNames = "";

    public HorizontalRecyclerViewAdapter(Context context, ArrayList<String> brandsName, int[] brandImages, Button continueBtn) {
        this.brandsName = brandsName;
        this.brandImages = brandImages;
        this.context = context;
        this.continueBtn = continueBtn;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recyclerview_adapter, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        int brand_image = brandImages[position];
        holder.brandNameText.setText(brandsName.get(position));
        holder.circleImageView.setImageResource(brand_image);

        holder.circleImageView.setOnClickListener(v -> {
            holder.selectedImage.setVisibility(View.VISIBLE);
            continueBtn.setVisibility(View.VISIBLE);
            selectedBrandNames += brandsName.get(position) + "\n";
        });

        continueBtn.setOnClickListener(v1 -> {
            Toast.makeText(context, selectedBrandNames, Toast.LENGTH_SHORT).show();
            selectedBrandNames = "";

            AppCompatActivity activity = (AppCompatActivity) context;
            ChooseCarModelActivity fragment = new ChooseCarModelActivity();
            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentID, fragment).addToBackStack(null);
            transaction.commit();

            continueBtn.setVisibility(View.GONE);
            holder.selectedImage.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public int getItemCount() {
        return brandImages.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView brandNameText;
        ImageView selectedImage;

        public viewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageId);
            brandNameText = itemView.findViewById(R.id.brandsNameId);
            selectedImage = itemView.findViewById(R.id.selectImageIdID);
            selectedImage.setVisibility(View.INVISIBLE);
        }
    }
}
