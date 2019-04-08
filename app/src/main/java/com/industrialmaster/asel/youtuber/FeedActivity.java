package com.industrialmaster.asel.youtuber;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FeedActivity extends AppCompatActivity {
    DatabaseReference databaseKeywords;
    RecyclerView feedRecycler;
    List<String> keywordList;
    private YoutubeAdapter youtubeAdapter;

    private List<VideoItem> searchResults;
    private List<VideoItem> mainResults;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        databaseKeywords = FirebaseDatabase.getInstance().getReference("Keywords");

        feedRecycler = (RecyclerView) findViewById(R.id.feed_recycler_view);
        feedRecycler.setLayoutManager(new LinearLayoutManager(this));
        feedRecycler.setHasFixedSize(true);


        keywordList = new ArrayList<String>();

        handler = new Handler();

        generateResults();
    }

    public void generateResults() {

        databaseKeywords.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot keysnapshot : dataSnapshot.getChildren()) {
                    String returnedKey = keysnapshot.getValue(String.class);

                    keywordList.add(returnedKey);


                }
                new Thread() {

                    //implementing run method
                    public void run() {
                        YoutubeConnector yc = new YoutubeConnector(FeedActivity.this);
                        mainResults = yc.searchOne("Java");
                        for (String key : keywordList) {
                            searchResults = null;
                            searchResults = yc.searchOne(key);
                            mainResults.addAll(searchResults);
                        }

                        Collections.shuffle(mainResults);

                        handler.post(new Runnable() {

                            //implementing run method of Runnable
                            public void run() {

                                fillYoutubeVideos();

                            }
                        });
                    }

                    //starting the thread
                }.start();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void fillYoutubeVideos() {
        youtubeAdapter = new YoutubeAdapter(getApplicationContext(), mainResults);
        feedRecycler.setAdapter(youtubeAdapter);
        youtubeAdapter.notifyDataSetChanged();
    }
}
