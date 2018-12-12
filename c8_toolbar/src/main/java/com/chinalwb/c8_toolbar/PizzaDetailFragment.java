package com.chinalwb.c8_toolbar;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class PizzaDetailFragment extends Fragment {

    private int pizzaId;

    public PizzaDetailFragment() {
        //
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pizza_detail, container, false);
        return view;
    }

    public void setPizzaId(int id) {
        this.pizzaId = id;
    }

    @Override
    public void onStart() {
        super.onStart();

        View root = getView();
        init(root);
    }

    private void init(View root) {
        String pizzaName = Pizza.pizzas[pizzaId].getName();
        int imageId = Pizza.pizzas[pizzaId].getImageResourceId();

        TextView textView = root.findViewById(R.id.pizza_text);
        textView.setText(pizzaName);

        ImageView imageView = root.findViewById(R.id.pizza_image);
        Drawable drawable = ContextCompat.getDrawable(getContext(), imageId);
        imageView.setImageDrawable(drawable);
    }
}
