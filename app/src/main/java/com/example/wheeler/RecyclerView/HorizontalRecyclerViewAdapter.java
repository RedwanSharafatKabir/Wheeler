package com.example.wheeler.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wheeler.AppActions.MainActivity;
import com.example.wheeler.R;
import java.util.ArrayList;
import de.hdodenhof.circleimageview.CircleImageView;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.viewHolder>{

    private ArrayList<String> brandsName;
    private int[] brandImages;
    private Context context;

    public HorizontalRecyclerViewAdapter(Context context, ArrayList<String> brandsName, int[] brandImages) {
        this.brandsName = brandsName;
        this.brandImages = brandImages;
        this.context = context;
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
        holder.circleImageView.setImageResource(brand_image);

        holder.brandNameText.setText(brandsName.get(position));
        holder.circleImageView.setOnClickListener(v -> {
            Toast.makeText(context, brandsName.get(position), Toast.LENGTH_SHORT).show();
            Intent it = new Intent(context, MainActivity.class);
            context.startActivity(it);
        });
    }

    @Override
    public int getItemCount() {
        return brandImages.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        CircleImageView circleImageView;
        TextView brandNameText;

        public viewHolder(View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.circleImageId);
            brandNameText = itemView.findViewById(R.id.brandsNameId);
        }
    }
}
