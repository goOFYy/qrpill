package com.example.barcode.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.barcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by apple on 6/2/17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {

    private List<Item> mItems = new ArrayList<>();
    private Context context;
    private int resource;
    private OnItemClickListener onItemClickListener;


    public ItemsAdapter(Context context, List<Item> mItems, int resource) {
        super();
        this.context = context;
        this.mItems = mItems;
        this.resource = resource;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(resource, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        Item item = mItems.get(i);
        viewHolder.nameTv.setText(item.getName());
        viewHolder.hoursTv.setText(item.getHours() + " hours");
        viewHolder.nextTv.setText("To be taken next at " + item.getNext());

        // Here you apply the animation when the view is bound
//        setAnimation(viewHolder.container, i);
    }

    /*private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_up);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }*/

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        final int maxCount = 8;
        return mItems.size() > maxCount ? maxCount : mItems.size();
    }

    public Object getItem(int position) {
        return this.mItems.get(position);
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTv, hoursTv, nextTv;

        ViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.item_name);
            hoursTv = (TextView) itemView.findViewById(R.id.item_hours);
            nextTv = (TextView) itemView.findViewById(R.id.item_next);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onItemClickListener.onItemClick(getAdapterPosition());
        }
    }

}
