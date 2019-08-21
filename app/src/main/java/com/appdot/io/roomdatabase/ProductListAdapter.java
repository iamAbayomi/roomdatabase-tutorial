package com.appdot.io.roomdatabase;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private int productItemLayout;
    private List<Product> productList;

    public ProductListAdapter(int productItemLayout) {
        this.productItemLayout = productItemLayout;
    }

    public void setProductList(List<Product> products){
        productList = products;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(productItemLayout,
                parent, false);
        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int listPosition) {

        TextView item = holder.item;
        item.setText(productList.get(listPosition).getName());

    }

    @Override
    public int getItemCount() {
        return productList == null ? 0 : productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
         TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item= itemView.findViewById(R.id.product_row);
        }


    }
}
