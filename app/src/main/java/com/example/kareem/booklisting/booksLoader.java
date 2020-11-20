package com.example.kareem.booklisting;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.util.List;

public class booksLoader extends AsyncTaskLoader<List<bookData>> {

    private String mUrl;
    private static final String LOG_TAG = booksLoader.class.getName();

    public booksLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    public booksLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "this when onStartLoading calling...");
        forceLoad();
    }

    @Nullable
    @Override
    public List<bookData> loadInBackground() {
        Log.i(LOG_TAG, "this when loadInBackGround calling...");
        if (mUrl == null) {
            return null;
        }
        List<bookData> list = QueryUtils.fetchBooksData(mUrl);
        return list;
    }
}
