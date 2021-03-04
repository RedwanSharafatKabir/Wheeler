package com.example.wheeler.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.wheeler.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class HorizontalRecyclerViewAdapter extends RecyclerView.Adapter<HorizontalRecyclerViewAdapter.viewHolder>{

    private ArrayList<String> brandsName = new ArrayList<>();
    private ArrayList<String> brandsImageUrl = new ArrayList<>();
    private Context context;

    public HorizontalRecyclerViewAdapter(Context context, ArrayList<String> brandsName, ArrayList<String> brandsImageUrl) {
        this.context = context;
        this.brandsName = brandsName;
        this.brandsImageUrl = brandsImageUrl;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_recyclerview_adapter, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Glide.with(context).asBitmap().load(brandsImageUrl.get(position)).into(holder.circleImageView);
        holder.brandNameText.setText(brandsName.get(position));
        holder.circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, brandsName.get(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return brandsImageUrl.size();
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
