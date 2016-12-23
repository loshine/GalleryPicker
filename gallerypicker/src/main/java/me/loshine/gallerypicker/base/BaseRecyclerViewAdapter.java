package me.loshine.gallerypicker.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 描    述：
 * 作    者：loshine1992@gmail.com
 * 时    间：2016/12/22
 */
public abstract class BaseRecyclerViewAdapter<H extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<H> {

    private LayoutInflater mInflater;
    protected Context mContext;

    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public final H onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        if (mInflater == null) mInflater = LayoutInflater.from(mContext);
        final H holder = onCreateViewHolder(mInflater, parent, viewType);
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.getAdapterPosition());
                }
            });
        }
        return holder;
    }

    public abstract H onCreateViewHolder(LayoutInflater inflater,
                                         ViewGroup parent,
                                         int viewType);

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
