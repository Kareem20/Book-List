package com.example.kareem.booklisting;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class baseBookItem extends Fragment implements LoaderManager.LoaderCallbacks<List<bookData>>
        , SwipeRefreshLayout.OnRefreshListener {

    /**
     * Constant value for the book loader ID.
     */
    private static final int BOOKS_LOADER_ID = 1;
    //get the name of this class
    private static final String LOG_TAG = baseBookItem.class.getName();

    /**
     * Loading indicator that is displayed before the first load is completed
     */
    private View loadingIndicator;

    /**
     * Adapter for the list of books
     */
    private bookAdapter mAdapter;
    /**
     * TextView that is displayed when the recycler view is empty
     */
    private TextView mEmptyStateTextView;
    /**
     * The {@link SwipeRefreshLayout} that detects swipe gestures and
     * triggers callbacks in the app.
     */
    private SwipeRefreshLayout mSwipeRefreshLayout;
    public String search;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment, container, false);


        //find a reference to the progress bar.
        loadingIndicator = rootView.findViewById(R.id.prograss_bar);
        // find a reference to the listView.
        EmptyRecylerView listViewRecycler = rootView.findViewById(R.id.list_recycler);
        //grid layout to put the books.
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);

        listViewRecycler.setHasFixedSize(true);
        // Set the layoutManager on the {@link RecyclerView}
        listViewRecycler.setLayoutManager(manager);

        // find a reference to the textView(NO Book FOUND).
        mEmptyStateTextView = rootView.findViewById(R.id.empty_text);
        listViewRecycler.setEmptyView(mEmptyStateTextView);
        // find a reference to the Swipe for refresh.
        mSwipeRefreshLayout = rootView.findViewById(R.id.swiperefresh);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        // Create a new adapter {@link ArrayAdapter} of book
        mAdapter = new bookAdapter(getActivity(), new ArrayList<bookData>());
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        listViewRecycler.setAdapter(mAdapter);

        //method to check the internet Connection.
        checkInternet();
        return rootView;
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
                .appendQueryParameter("q", "java")
                // .appendQueryParameter("maxResults", "20")
                .appendQueryParameter("api-key", "164ddb0c-58d3-4abb-8e7f-b4dfa93991e4");


        return new booksLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<bookData>> loader, List<bookData> data) {
        loadingIndicator.setVisibility(View.GONE);

        mEmptyStateTextView.setText(R.string.no_books);

        mAdapter.clear();
        // If there is a valid list of {@link book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<bookData>> loader) {
// Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /* * this function to appear the menu activity
     * */
/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_icon).getActionView();
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(new ComponentName(this, search.class)));
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Intent intent = new Intent(getApplicationContext(), search.class);
                intent.putExtra("search", query);
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        };

        return true;
    }*/

    private boolean checkInternet() {
        // get a reference to the connectivityManager to check state of network connectivity.
        ConnectivityManager comMang = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        //get details on the currently active default data network.
        NetworkInfo networkInfo = comMang.getActiveNetworkInfo();
        // if there is a network connection , fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            androidx.loader.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOKS_LOADER_ID, null, this);
            return true;
        } else {
            loadingIndicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_Internet_connection);
            return false;

        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        if (checkInternet()) {
            androidx.loader.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(BOOKS_LOADER_ID, null, this);
            Toast.makeText(getActivity(), "Articles is Updated.", Toast.LENGTH_SHORT).show();
        } else {
            if (mAdapter.getItemCount() == 0) {
                mEmptyStateTextView.setText(R.string.No_Internet_Connection);
            } else {
                mEmptyStateTextView.setText("");
            }
            Toast.makeText(getActivity(), "No Internet Connection.", Toast.LENGTH_SHORT).show();
        }
        mSwipeRefreshLayout.setRefreshing(false);
    }
}


