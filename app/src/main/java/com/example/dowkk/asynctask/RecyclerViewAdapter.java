package com.example.dowkk.asynctask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{
    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Product> mProducts = new ArrayList<>();
    private Context mContext;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    public RecyclerViewAdapter(Context context, ArrayList<Product> products) {
        this.mProducts = products;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");
        Glide.with(mContext)
                .asBitmap()
                .load(mProducts.get(position).getProductImage())
                .into(holder.product_img);
        holder.product_productName.setText(mProducts.get(position).getProductName());
        holder.product_price.setText(mProducts.get(position).getProductPrice());
        holder.product_seller.setText(mProducts.get(position).getProductCode());

        final String title = mProducts.get(position).getProductName();
        final String content = mProducts.get(position).getProductPrice();

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked on : " + mProducts.get(position).getProductName());
                Toast.makeText(mContext, mProducts.get(position).getProductName(), Toast.LENGTH_SHORT).show();
                FirebaseHelper firebaseHelper = FirebaseHelper.getInstance();
                firebaseHelper.setUserId("hyunah");
                firebaseHelper.saveProduct(mProducts.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView product_img;
        TextView product_productName,product_price,product_seller;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            product_img = itemView.findViewById(R.id.product_img);
            product_productName = itemView.findViewById(R.id.product_productName);
            product_price = itemView.findViewById(R.id.product_price);
            product_seller = itemView.findViewById(R.id.product_seller);
        }
    }


}
