package com.maciejgontar.zoogs;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.facebook.FacebookSdk;
import com.facebook.share.model.*;
import com.facebook.share.widget.MessageDialog;
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

public class ShowGifsActivity extends Activity implements OnGifTap {
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

        gifAdapter = new GifAdapter(this, gifUrlList, this);
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
                    for (Gif gif : gd.getData()) {
                        gifAdapter.getUrlList().add(gif.getGifUrl());
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

    protected void sendGifViaMessenger(String url) {
        MessageDialog dialog = new MessageDialog(this);
// "https://www.facebook.com/sharer/sharer.php?u=" +
        ShareLinkContent content =
                new ShareLinkContent.Builder()
                        .setContentUrl(Uri.parse(url))
                        .build();

//        ShareMessengerURLActionButton actionButton =
//                new ShareMessengerURLActionButton.Builder()
//                        .setTitle(" ")
//                        .setUrl(Uri.parse(url))
//                        .build();
//        ShareMessengerMediaTemplateContent content =
//                new ShareMessengerMediaTemplateContent.Builder()
//                        .setPageId("Your page ID") // Your page ID, required
//                        .setMediaType(ShareMessengerMediaTemplateContent.MediaType.IMAGE)
//                        .setMediaUrl(Uri.parse(url)) // Must be a Facebook URL, see media template documentation
//                        .setButton(actionButton)
//                        .build();

//        ShareMessengerURLActionButton actionButton =
//                new ShareMessengerURLActionButton.Builder()
//                        .setTitle("Visit Facebook")
//                        .setUrl(Uri.parse(url))
//                        .build();
//        ShareMessengerGenericTemplateElement genericTemplateElement =
//                new ShareMessengerGenericTemplateElement.Builder()
//                        .setTitle("Visit Facebook")
//                        .setSubtitle("Visit Messenger")
//                        .setImageUrl(Uri.parse(url))
//                        .setButton(actionButton)
//                        .build();
//        ShareMessengerGenericTemplateContent content =
//                new ShareMessengerGenericTemplateContent.Builder()
//                        .setPageId("Your Page Id") // Your page ID, required
//                        .setGenericTemplateElement(genericTemplateElement)
//                        .build();

        if (dialog.canShow(content)) {
            dialog.show(this, content);
        }
    }

    @Override
    protected void onDestroy() {
        if (!compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
        super.onDestroy();
    }

    @Override
    public void onTap (String url){
        sendGifViaMessenger(url);
    }
}
