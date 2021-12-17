package com.example.managestorephone.Product;


import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.annotation.Nullable;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class ProductListAdapter {

    public static ProductListAdapter productListAdapter;
    public Network network;
    public RequestQueue requestQueue1;
    public ImageLoader ImageLoader1;
    public Cache cache;
    public static Context context1;
    LruCache<String, Bitmap> LRUCACHE = new LruCache<String, Bitmap>(30);

    private ProductListAdapter(Context context){
        this.context1 = context;
        this.requestQueue1  = RequestQueueFunction();
        ImageLoader1 = new ImageLoader(requestQueue1, new ImageLoader.ImageCache() {
            @Nullable
            @Override
            public Bitmap getBitmap(String url) {
                return LRUCACHE.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

                LRUCACHE.put(url,bitmap);
            }
        });
    }

    public RequestQueue RequestQueueFunction() {
        if(requestQueue1 == null){
            cache = new DiskBasedCache(context1.getCacheDir());
            network = new BasicNetwork(new HurlStack());
            requestQueue1 = new RequestQueue(cache,network);
            requestQueue1.start();
        }
        return  requestQueue1;
    }

    public ImageLoader getImageLoader(){
        return  ImageLoader1;
    }

    public static ProductListAdapter getInstance(Context context){
        if(productListAdapter == null){
            productListAdapter = new ProductListAdapter(context);
        }
        return productListAdapter;
    }

}
