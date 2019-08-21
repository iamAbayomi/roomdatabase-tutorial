package com.appdot.io.roomdatabase;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class MainFragment extends Fragment {

    private  MainViewModel mViewModel;
    private  ProductListAdapter adapter;

    private TextView productId;
    private EditText productName;
    private EditText productQuantity;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mainfragment_layout, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        productId = getView().findViewById(R.id.productID);
        productName = getView().findViewById(R.id.productName);
        productQuantity = getView().findViewById(R.id.productQuantity);

        listenerSetup();
        observerSetup();
        recyclerSetup();

    }



    private void clearFields(){
        productId.setText("");
        productName.setText("");
        productQuantity.setText("");
    }


    private void listenerSetup(){
        Button addButton = getView().findViewById(R.id.addButton);

        Button findButton = getView().findViewById(R.id.findButton);

        Button deleteButton = getView().findViewById(R.id.deleteButton);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = productName.getText().toString();

                String quantity = productQuantity.getText().toString();

                if(!name.equals("") && !quantity.equals("")){
                    Product product = new Product(name, Integer.parseInt(quantity));
                    Integer.parseInt(quantity);
                    mViewModel.insertProduct(product);

                    clearFields();
                }
                else{
                    productId.setText("Incomplete information");
                }

            }
        });


        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.findProduct(productName.getText().toString());
            }
        });


        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.deleteProduct(productName.getText().toString());
                clearFields();
            }
        });

    }


    private void recyclerSetup() {
        RecyclerView recyclerView;

        adapter = new ProductListAdapter(R.layout.product_list_item);

        recyclerView = getView().findViewById(R.id.product_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

         recyclerView.setAdapter(adapter);
    }

    private void observerSetup() {

        mViewModel.getAllProducts().observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                    adapter.setProductList(products);
            }
        });

        mViewModel.getSearchResults().observe(this, new Observer<List<Product>>(){

            @Override
            public void onChanged(@Nullable List<Product> products) {
               if(products.size() > 0){
                   productId.setText(String.format(Locale.US,"%d" ,products.get(0).getId()) );

                   productName.setText(products.get(0).getName());

                   productQuantity.setText(String.format(Locale.US,"%d" ,
                           products.get(0).getQuantity()));

               }else{
                   productId.setText("No Match");
               }
            }
        });
    }

}
