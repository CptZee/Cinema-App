package com.github.cptzee.cinemax.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.github.cptzee.cinemax.Data.Category;
import com.github.cptzee.cinemax.Data.CategoryHelper;
import com.github.cptzee.cinemax.R;

import java.util.List;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        Button btn_categories = view.findViewById(R.id.btn_categories);
        CategoryHelper helper = new CategoryHelper(getContext());

        btn_categories.setOnClickListener(view1 -> {
            PopupMenu popupMenu = new PopupMenu(getActivity(), view1);
            List<Category> list = helper.get();
            if(list.isEmpty())
                return;

            for(Category data : list){
                popupMenu.getMenu().add(data.getName());
            }
            popupMenu.show();
        });
        return view;
    }
}