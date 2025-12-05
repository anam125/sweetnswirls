package com.example.sweetnswirls.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.sweetnswirls.models.IceCream;
import com.example.sweetnswirls.activities.ItemDetailActivity;
import com.example.sweetnswirls.R;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

    private List<IceCream> iceCreamList;
    private Context context;

    public MenuAdapter(Context context, List<IceCream> iceCreamList){
        this.context = context;
        this.iceCreamList = iceCreamList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.item_icecream, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position){
        IceCream ice = iceCreamList.get(position);
        holder.name.setText(ice.getName());
        holder.price.setText("Rs " + ice.getPrice());
        holder.image.setImageResource(ice.getImage());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ItemDetailActivity.class);
            intent.putExtra("name", ice.getName());
            intent.putExtra("desc", ice.getDescription());
            intent.putExtra("price", ice.getPrice());
            intent.putExtra("image", ice.getImage());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount(){ return iceCreamList.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView name, price;
        ImageView image;
        public ViewHolder(View itemView){
            super(itemView);
            name = itemView.findViewById(R.id.iceName);
            price = itemView.findViewById(R.id.icePrice);
            image = itemView.findViewById(R.id.iceImage);
        }
    }
}
