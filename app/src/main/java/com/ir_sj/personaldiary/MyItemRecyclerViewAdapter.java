package com.ir_sj.personaldiary;/*package com.ir_sj;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ir_sj.personaldiary.FileExplorerFragment.OnListFragmentInteractionListener;
import com.ir_sj.dummy.DummyContent.DummyItem;
import com.ir_sj.personaldiary.R;

import java.util.List;

*
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link //OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.

public class MyItemRecyclerViewAdapter extends RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder> {

    private final List<Item> mValues=null;
    //private final OnListFragmentInteractionListener mListener;
    Context c;

    /*public MyItemRecyclerViewAdapter(List<Item> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mNameView.setText(mValues.get(position).getName());
        holder.mSizeView.setText(mValues.get(position).getData());
        holder.mDateModifiedView.setText(mValues.get(position).getDate());

        String uri = "drawable/" + mValues.get(position).getImage();
        int imageResource = c.getResources().getIdentifier(uri, null, c.getPackageName());
        Drawable image = c.getResources().getDrawable(imageResource, null);
        holder.icon.setImageDrawable(image);

        holder.mView.setOnClickListener(new View.OnClickListener() {
           /* @Override
            public void onClick(View v) {
               // if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNameView;
        public final TextView mSizeView;
        public final TextView mDateModifiedView;
        public final ImageView icon;
        public Item mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNameView = (TextView) view.findViewById(R.id.fName);
            mSizeView = (TextView) view.findViewById(R.id.size);
            mDateModifiedView = (TextView) view.findViewById(R.id.date);
            icon = (ImageView) view.findViewById(R.id.icon);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNameView.getText() + "'";
        }
    }
}
*/