package com.maciejgontar.zoogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.maciejgontar.zoogs.model.Gif;
import com.maciejgontar.zoogs.model.GifData;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ShowGifsActivity extends Activity {
    final int limit = 30;
    final String API_KEY = "97Kg8Kl8eLtqd4r5f4AYUarlZXu1mwXt";
    String query = "cute+animal";
    private CompositeDisposable compositeDisposable;
    private RecyclerView gifRecyclerView;
    private GifAdapter gifAdapter;
    private RecyclerView.LayoutManager gifLayoutManager;

    ArrayList<String> gifUrlList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        query = getIntent().getStringExtra("DESIRED_ANIMAL");
        setContentView(R.layout.activity_show_gifs);
        this.compositeDisposable = new CompositeDisposable();

        gifRecyclerView = (RecyclerView) findViewById(R.id.gifrecyclerview);
        //gifRecyclerView.setHasFixedSize(false);

        gifLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        gifRecyclerView.setLayoutManager(gifLayoutManager);

        gifAdapter = new GifAdapter(this, gifUrlList);
        gifRecyclerView.setAdapter(gifAdapter);

//        gifGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> parent, View v,
//                                    int position, long id) {
//                Toast.makeText(ShowGifsActivity.this, "Gif tapped, pos: " + position,
//                        Toast.LENGTH_SHORT).show();
//            }
//        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.giphy.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        GiphyService giphyService = retrofit.create(GiphyService.class);
        Single<GifData> gifData = giphyService.getGifData(API_KEY, query, limit);
        gifData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GifData>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }
                @Override
                public void onSuccess(GifData gd) {
                    System.out.println("old");
                    System.out.println(gifAdapter.getUrlList());
                    gifAdapter.getUrlList().clear();
                    //ArrayList<String> newGifUrlList = new ArrayList<String>();
                    for (Gif gif : gd.getData()) {
                        gifAdapter.getUrlList().add(gif.getGifUrl());
                        //newGifUrlList.add(gif.getGifUrl());
                    }
                    System.out.println("new");
                    gifAdapter.notifyDataSetChanged();
                    System.out.println(gifAdapter.getUrlList());

                    System.out.println("daneeeeeee");
                    System.out.println(gd.getData()[0].getGifUrl());
                    System.out.println("udałoś");
                }
                @Override
                public void onError(Throwable e) {
                    System.out.println(e);
                }
            });
    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }
}
