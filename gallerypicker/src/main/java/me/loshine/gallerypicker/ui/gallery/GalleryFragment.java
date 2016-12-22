package me.loshine.gallerypicker.ui.gallery;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import me.loshine.gallerypicker.R;

/**
 * 描    述：
 * 作    者：longs@13322.com
 * 时    间：2016/12/22
 */
public class GalleryFragment extends Fragment implements GalleryContract.View {

    RecyclerView mRecyclerView;

    GalleryAdapter mAdapter;

    private GalleryContract.Presenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GalleryPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gallery_picker_fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        // item 高度确定的情况下可以提升性能
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        mAdapter = new GalleryAdapter(mPresenter.getItems());
        mRecyclerView.setAdapter(mAdapter);

        mPresenter.load();
    }

    @Override
    public void onLoadComplete() {
        mAdapter.notifyDataSetChanged();
    }
}
