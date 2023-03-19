package com.github.cptzee.cinemax;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = (View) inflater.inflate(R.layout.fragment_home, container, false);

        Button btn_categories = (Button) view.findViewById(R.id.btn_categories);
        btn_categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                popupMenu.getMenuInflater().inflate(R.menu.categories_pop_up_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.item_1:
                                Toast.makeText(getContext(), "Imax", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_2:
                                Toast.makeText(getContext(), "Ordinary", Toast.LENGTH_SHORT).show();
                                return true;
                            case R.id.item_3:
                                Toast.makeText(getContext(), "3D", Toast.LENGTH_SHORT).show();
                                return true;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
        return view;
    }
}