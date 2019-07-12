package com.chinalwb.c8_toolbar;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private String[] captions;
    private int[] imageIds;
    private Listener listener;

    public CaptionedImagesAdapter(String[] captions, int[] imageIds) {
        this.captions = captions;
        this.imageIds = imageIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(viewGroup.getContext()).inflate(
                R.layout.card_captioned_image,
                viewGroup,
                false
        );
        ViewHolder viewHolder = new ViewHolder(cardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {
        CardView cardView = viewHolder.cardView;
        ImageView imageView = cardView.findViewById(R.id.info_image);
        imageView.setImageResource(imageIds[position]);
        imageView.setContentDescription(captions[position]);
        TextView textView = cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ViewHolder (CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }

    interface Listener {
        void onClick(int pos);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }
}
