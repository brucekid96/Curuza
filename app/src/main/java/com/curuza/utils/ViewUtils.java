package com.curuza.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;

import com.curuza.R;

public class ViewUtils {

    public static void toggleSoftInput(View view, boolean showSoftInput) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        if (showSoftInput) {
            inputMethodManager.showSoftInput(view, 0);
        } else {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


    }

//    public static SearchView setupSearchView(Menu menu) {
//        SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
//        searchView.setIconifiedByDefault(false);
//        searchView.setMaxWidth(Integer.MAX_VALUE);
//
//        // Remove search icon and underline from search view
//        ImageView searchIcon = searchView.findViewById(androidx.appcompat.R.id.search_mag_icon);
//        searchIcon.setImageDrawable(null);
//        searchIcon.setVisibility(View.GONE);
//
//        View searchPlate = searchView.findViewById(androidx.appcompat.R.id.search_plate);
//        searchPlate.setBackgroundColor(Color.TRANSPARENT);
//
//        return searchView;
//    }
}
