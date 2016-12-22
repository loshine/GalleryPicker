package me.loshine.gallerypicker.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public abstract class BaseRecyclerViewAdapter<H extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<H> {

    private LayoutInflater mInflater;
    protected Context mContext;

    @Override
    public final H onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) mContext = parent.getContext();
        if (mInflater == null) mInflater = LayoutInflater.from(mContext);
        return onCreateViewHolder(mInflater, parent, viewType);
    }

    public abstract H onCreateViewHolder(LayoutInflater inflater,
                                         ViewGroup parent,
                                         int viewType);
}
