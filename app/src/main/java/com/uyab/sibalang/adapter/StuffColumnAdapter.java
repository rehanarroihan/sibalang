package com.uyab.sibalang.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.uyab.sibalang.R;
import com.uyab.sibalang.Util.GlobalConfig;
import com.uyab.sibalang.model.Stuff;

import java.util.ArrayList;

public class StuffColumnAdapter extends RecyclerView.Adapter<StuffColumnAdapter.ViewHolder> {
    private ArrayList<Stuff> stuff;
    private StuffInterface stuffInterface;
    private Context context;

    public StuffColumnAdapter(ArrayList<Stuff> stuff, StuffInterface stuffInterface, Context context) {
        this.stuff = stuff;
        this.stuffInterface = stuffInterface;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stuff_column, viewGroup, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Stuff stuffe = stuff.get(i);
        Glide.with(context)
                .load(GlobalConfig.IMAGE_BASE_URL + stuffe.getPhoto())
                .into(viewHolder.imageViewPict);
        viewHolder.tvName.setText(stuffe.getName());
        viewHolder.tvDesc.setText(stuffe.getDescription());
    }

    @Override
    public int getItemCount() {
        if(stuff != null)
            return stuff.size();
        return 0;
    }

    public interface StuffInterface {
        void doClick(int post);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewPict;
        TextView tvName, tvDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPict = itemView.findViewById(R.id.imageViewPict);
            tvName = itemView.findViewById(R.id.textViewName);
            tvDesc = itemView.findViewById(R.id.textViewDesc);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stuffInterface.doClick(getAdapterPosition());
                }
            });
        }
    }
}
