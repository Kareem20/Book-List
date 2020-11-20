package com.example.kareem.booklisting.bookCategories;

import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.Loader;

import com.example.kareem.booklisting.baseBookItem;
import com.example.kareem.booklisting.bookData;
import com.example.kareem.booklisting.booksLoader;

import java.util.List;

public class programming extends baseBookItem {

    //get the name of this class
    private final static String LOG_TAG = programming.class.getName();

    @NonNull
    @Override
    public Loader<List<bookData>> onCreateLoader(int id, @Nullable Bundle args) {
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.scheme("https")
                .encodedAuthority("www.googleapis.com")
                .appendPath("books")
                .appendPath("v1")
                .appendPath("volumes")
                .appendQueryParameter("q", "programming")
                // .appendQueryParameter("maxResults", "20")
                .appendQueryParameter("api-key", "164ddb0c-58d3-4abb-8e7f-b4dfa93991e4");


        return new booksLoader(getActivity(), uriBuilder.toString());
    }
}