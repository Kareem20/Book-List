package com.example.kareem.booklisting;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by kareem on 1/10/2019.
 */

public class bookAdapter extends RecyclerView.Adapter<bookAdapter.ViewHolder> {
    private Context mContext;
    private List<bookData> mBookData;
    private SharedPreferences sharedPrefs;

    public bookAdapter(Context context, ArrayList<bookData> bookData) {
        mContext = context;
        mBookData = bookData;
    }

    @Override
    public void onBindViewHolder(@NonNull bookAdapter.ViewHolder holder, int position) {
//        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mContext);
// Find the current news that was clicked on
        final bookData currentNews = mBookData.get(position);
        holder.book_name.setText(currentNews.getName());
        holder.book_author.setText(currentNews.getAuthor());
      /*  holder.book_description.setText(currentNews.getDescription());*/
        // If the author does not exist, hide the authorTextView
        if (currentNews.getAuthor() == null) {
            holder.book_author.setVisibility(View.GONE);
        } else {
            holder.book_author.setVisibility(View.VISIBLE);
            holder.book_author.setText(currentNews.getAuthor());
        }
        // If the image does not exist, hide the Image.
        if (currentNews.getImage() == null) {
            holder.thumbnailImageView.setVisibility(View.GONE);
        } else {
            holder.thumbnailImageView.setVisibility(View.VISIBLE);
            // Load thumbnail with glide
            Picasso.with(mContext.getApplicationContext())
                    .load(currentNews.getImage())
                    .into(holder.thumbnailImageView);
        }
        // Set an OnClickListener to open a website with more information about the selected article.
        /*holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                //  Uri newsUri = Uri.parse(currentNews.getUrl());

                // Create a new intent to view the news URI
                // Intent websiteIntent = new Intent(Intent.ACTION_VIEW, newsUri);

                // Send the intent to launch a new activity
                //  mContext.startActivity(websiteIntent);

              *//*  Intent intent = new Intent(mContext, textBody.class);
                intent.putExtra("description", currentNews.getDescription());
                intent.putExtra("author", currentNews.getAuthor());
                intent.putExtra("image", currentNews.getImage());
                intent.putExtra("newsUri", currentNews.getUrl());
                intent.putExtra("imageOfbook", currentNews.getImage());*//*


                //  mContext.startActivity(intent);
            }
        });*/

    }


    @NonNull
    @Override
    public bookAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.book_item, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public int getItemCount() {
        return mBookData.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView book_name;
        private TextView book_author;
        private TextView book_description;
        private ImageView thumbnailImageView;
        private CardView cardView;

        ViewHolder(View itemView) {
            super(itemView);
            book_name = itemView.findViewById(R.id.book_name);
            book_author = itemView.findViewById(R.id.book_author);
            thumbnailImageView = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.card_view);

        }

    }

    /**
     * Clear all data (a list of {@link bookData} objects)
     */
    public void clear() {
        mBookData.clear();
        notifyDataSetChanged();
    }

    /**
     * Add  a list of {@link bookData}
     *
     * @param newsList is the list of news, which is the data source of the adapter
     */
    public void addAll(List<bookData> newsList) {
        mBookData.clear();
        mBookData.addAll(newsList);
        notifyDataSetChanged();
    }

}
