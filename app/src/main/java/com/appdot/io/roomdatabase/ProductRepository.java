package com.appdot.io.roomdatabase;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;

import java.util.List;

public class ProductRepository {

    private MutableLiveData<List<Product>> searchResults = new MutableLiveData<>();

    private ProductDao productDao;
    private LiveData<List<Product>> allProducts;



    public ProductRepository(Application application){
        ProductRoomDatabase db;
        db = ProductRoomDatabase.getDatabase(application);

        productDao =db.productDao();
        allProducts = productDao.getAllProducts();
    }


    public LiveData<List<Product>> getAllProducts(){
        return allProducts;
    }


    public  MutableLiveData<List<Product>> getSearchResult(){
        return searchResults;
    }


    public void insertProduct(Product newproduct){
            InsertAsyncTask task = new InsertAsyncTask(productDao);
            task.execute(newproduct);
    }

    public  void findProduct(String name){
        QueryAsyncTask task = new QueryAsyncTask(productDao);
        task.delegate = this;
        task.execute(name);
    }

    public void deleteProduct(String name){
        DeleteAsyncTask task = new DeleteAsyncTask(productDao);
        task.execute(name);
    }

    private void asyncFinished(List<Product> results){
        searchResults.setValue(results);
    }


    //To find a new product
    private static class QueryAsyncTask extends AsyncTask<String, Void,
            List<Product>> {

        private ProductDao asyncTaskDao;
        private ProductRepository delegate = null;

        QueryAsyncTask(ProductDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected List<Product> doInBackground(final String... params) {
            return asyncTaskDao.findProduct(params[0]);
        }

        @Override
        protected  void onPostExecute(List<Product> result){
                delegate.asyncFinished(result);
        }
    }


    //To insert new products
    private static class InsertAsyncTask extends AsyncTask<Product, Void, Void>{

        private ProductDao asyncTaskDao;

        InsertAsyncTask(ProductDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Product... params) {
            asyncTaskDao.insertProduct(params[0]);
            return null;
        }

    }

    //To delete existing products
    private static  class DeleteAsyncTask extends AsyncTask<String, Void, Void>{

        private  ProductDao asyncTaskDao;

        DeleteAsyncTask(ProductDao dao){
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params) {
            asyncTaskDao.deletedProduct(params[0]);
            return null;
        }
    }


}