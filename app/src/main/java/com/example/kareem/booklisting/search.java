package com.example.kareem.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class search extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<bookData>>
        , SwipeRefreshLayout.OnRefreshListener {
    //the Search text that user type it in search bar.
    private String search;

    //Loading indicator that is displayed before the first load is completed
    private View loadingIndicator;

    //Adapter for the list of Searched books.
    private bookAdapter mAdapter;

    //TextView that is displayed when the recycler view is empty
    private TextView mEmptyStateTextView;
    /**
     * The {@link SwipeRefreshLayout} that detects swipe gestures and
     * triggers callbacks in the app.
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    //textView for the no result found.
    private TextView noResultFound;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        Intent intent = getIntent();
        search = intent.getStringExtra("search");

        //find a reference to the text no result found.
        noResultFound = findViewById(R.id.no_result);
        //find a reference to the progress bar.
        loadingIndicator = findViewById(R.id.progress_bar_search_activity);
        // find a reference to the listView recycler.
        EmptyRecylerView listViewRecycler = findViewById(R.id.list_recycler_search_activity);
        //grid layout to put the books.
        GridLayoutManager manager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        listViewRecycler.setHasFixedSize(true);
        // Set the layoutManager on the {@link RecyclerView}
        listViewRecycler.setLayoutManager(manager);

        // find a reference to the textView(NO Book FOUND).
        mEmptyStateTextView = findViewById(R.id.no_internet_search_activity);
        listViewRecycler.setEmptyView(mEmptyStateTextView);

        // find a reference to the Swipe for refresh.
        mSwipeRefreshLayout = findViewById(R.id.swipeRefresh_search_activity);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // Create a new adapter {@link ArrayAdapter} of book
        mAdapter = new bookAdapter(this, new ArrayList<bookData>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listViewRecycler.setAdapter(mAdapter);

        //method to check the internet Connection.
        checkInternet();
        //go back to Main activity.
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    @NonNull
    @Override
    public Loader<List<bookData>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .encodedAuthority("www.googleapis.com")
                .appendPath("books")
                .appendPath("v1")
                .appendPath("volumes")
                .appendQueryParameter("q", search)
                // .appendQueryParameter("maxResults", "20")
                .appendQueryParameter("api-key", "164ddb0c-58d3-4abb-8e7f-b4dfa93991e4");


        return new booksLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<bookData>> loader, List<bookData> data) {
        loadingIndicator.setVisibility(View.GONE);

        // mEmptyStateTextView.setText(R.string.no_books);

        mAdapter.clear();
        // If there is a valid list of {@link book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else {
            noResultFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<bookData>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        if (checkInternet()) {
            androidx.loader.app.LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(1, null, this);
            Toast.makeText(this, "Articles is Updated.", Toast.LENGTH_SHORT).show();
        } else {
            if (mAdapter.getItemCount() == 0) {
                mEmptyStateTextView.setText(R.string.No_Internet_Connection);
            } else {
                mEmptyStateTextView.setText("");
            }
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }

    private boolean checkInternet() {
        // get a reference to the connectivityManager to check state of network connectivity.
        ConnectivityManager comMang = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details on the currently active default data network.
        NetworkInfo networkInfo = comMang.getActiveNetworkInfo();
        // if there is a network connection , fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getSupportLoaderManager();
            loaderManager.initLoader(1, null, this);
            return true;
        } else {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_Internet_connection);
            return false;

        }
    }
}
