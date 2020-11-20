package com.example.kareem.booklisting;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kareem on 1/10/2019.
 */

public class bookData{
    //the name of the book.
    private String mName;
    //the name of the author.
    private String mAuthor;
    //the url of information of the book (play store)
    private String mUrl;
    //the image of the book
    private String mImage;
    //the description of book
    private String mDescription;

    /*
    * Create a new book object
    *
    *  @param nameOfBook is the name of the book.
    *  @param nameOfAuthor is the  name of the author.
    *  @param url is the website URL of the book's information.
    * */
    public bookData(String author, String name,String Url,String image/*,String description*/ ) {
        mName = name;
        mAuthor = author;
        mUrl = Url;
        mImage=image;
        /*mDescription = description;*/

    }
    /*
    * Get the name of the book.
    * */
    public String getName() {
        return mName;
    }

    /*
     * Get the name of the author.
     * */
    public String getAuthor() {
        return mAuthor;
    }
     /*
     * Get the url  of the book's information.
     * */
     public String getUrl()
     {
         return mUrl;
     }

     public String getImage(){
         return mImage;
     }

    /* public String getDescription(){
         return mDescription;
     }*/
}