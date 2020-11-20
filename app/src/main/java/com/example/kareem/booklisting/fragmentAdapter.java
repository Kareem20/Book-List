package com.example.kareem.booklisting;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.kareem.booklisting.bookCategories.business;
import com.example.kareem.booklisting.bookCategories.culture;
import com.example.kareem.booklisting.bookCategories.economy;
import com.example.kareem.booklisting.bookCategories.environment;
import com.example.kareem.booklisting.bookCategories.home;
import com.example.kareem.booklisting.bookCategories.programming;
import com.example.kareem.booklisting.bookCategories.science;
import com.example.kareem.booklisting.bookCategories.society;
import com.example.kareem.booklisting.bookCategories.timeManagment;

public class fragmentAdapter extends FragmentPagerAdapter {
    /**
     * Context of the app
     */
    private final Context mContext;

    /**
     * Create a new {@link fragmentAdapter} object.
     *
     * @param context is the context of the app
     * @param fm      is the fragment manager that will keep each fragment's state in the adapter
     * across swipes.
     */
    public fragmentAdapter(Context context, @NonNull FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    /**
     * Return the {@link Fragment} that should be displayed for the given page number.
     */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new home();
            case 1:
                return new programming();
            case 2:
                return new science();
            case 3:
                return new timeManagment();
            case 4:
                return new environment();
            case 5:
                return new society();
            case 6:
                return new economy();
            case 7:
                return new business();
            case 8:
                return new culture();
            default:
                return null;
        }
    }

    /**
     * Return the total number of pages.
     */
    @Override
    public int getCount() {
        return 9;
    }

    /**
     * Return page title of the tap
     */
    @Override
    public CharSequence getPageTitle(int position) {
        int titleResId;
        switch (position) {
            case 0:
                titleResId = R.string.ic_title_home;
                break;
            case 1:
                titleResId = R.string.ic_title_programming;
                break;
            case 2:
                titleResId = R.string.ic_title_science;
                break;
            case 3:
                titleResId = R.string.ic_title_management;
                break;
            case 4:
                titleResId = R.string.ic_title_environment;
                break;
            case 5:
                titleResId = R.string.ic_title_society;
                break;
            case 6:
                titleResId = R.string.ic_title_economy;
                break;
            case 7:
                titleResId = R.string.ic_title_business;
                break;
            default:
                titleResId = R.string.ic_title_culture;
                break;
        }
        return mContext.getString(titleResId);
    }
}
