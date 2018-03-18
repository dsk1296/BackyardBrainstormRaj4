package hackathon.rajasthan.rajasthantourism;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ProgressBar;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineRecyclerViewAdapter;

public class SocialFeedActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    TweetTimelineRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SwipeRefreshLayout                  swipeRefreshLayout;
    private static final String         LOG_TAG ="Refresh" ;
    ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_feed);

        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET)))
                .debug(true)
                .build();
        Twitter.initialize(config);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Social Feed");

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerViewSocialFeed);
        swipeRefreshLayout=findViewById(R.id.socialFeedSwipeRefreshLayout);
        recyclerView.setLayoutManager(new LinearLayoutManager(SocialFeedActivity.this));

        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.md_red_500,R.color.md_blue_500,R.color.md_green_500);


        final SearchTimeline searchTimeline = new SearchTimeline.Builder()
                .query("#incrediblerajasthan")
                .maxItemsPerRequest(50)
                .build();

        adapter = new TweetTimelineRecyclerViewAdapter.Builder(SocialFeedActivity.this)
                .setTimeline(searchTimeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
                .build();

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        adapter.refresh(new Callback<TimelineResult<Tweet>>() {
            @Override
            public void success(Result<TimelineResult<Tweet>> result) {
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void failure(TwitterException exception) {
                swipeRefreshLayout.setRefreshing(true);
            }

        });
    }
}
