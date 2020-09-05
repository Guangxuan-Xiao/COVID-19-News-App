package com.example.covid_news.ui.news;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import com.example.covid_news.R;
import com.example.covid_news.gson.NewsGson;
import com.example.covid_news.ui.news.NewsDetailsActivity;
import com.example.covid_news.ui.news.contract.NewsContract;
import com.example.covid_news.ui.news.presenter.NewsPresenter;
import com.example.covid_news.util.PixelUtil;
import com.example.covid_news.util.PicUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.content.Context;

class NewsViewHolder extends BaseViewHolder<NewsGson.NewslistBean> {

    private TextView mTv_name;
    private ImageView mImg_face;
    private TextView mTv_sign;

    public NewsViewHolder(ViewGroup parent) {
        super(parent,R.layout.news_recycler_item);
        mTv_name = $(R.id.person_name);
        mTv_sign = $(R.id.person_sign);
        mImg_face = $(R.id.person_face);
    }

    @Override
    public void setData(final NewsGson.NewslistBean data) {
        mTv_name.setText(data.getTitle());
        mTv_sign.setText(data.getCtime());
        PicUtil.showImage(mImg_face,getContext(),data.getPicUrl());
    }

}

class NewsAdapter extends RecyclerArrayAdapter<NewsGson.NewslistBean> {
    public NewsAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        return new NewsViewHolder(parent);
    }
}

public class NewsClassFragment extends Fragment implements NewsContract.View{
    private NewsAdapter adapter;
    private NewsContract.Presenter mPresenter;

    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private boolean hasFetchData; // 标识已经触发过懒加载数据
    private int type;


    public static NewsClassFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        NewsClassFragment fragment = new NewsClassFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }



    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;
    private int pageIndex = 1;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_class, container, false);
        ButterKnife.bind(this, view);

        mPresenter=new NewsPresenter(this,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(getContext());
        recyclerView.setAdapter(adapter);

        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int)PixelUtil.convertDpToPixel(8, getContext()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);

        //更多加载
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                Log.e("更多","更多");
                mPresenter.loadData(type,pageIndex);
                pageIndex=pageIndex+1;
            }

            @Override
            public void onMoreClick() {

            }
        });
        //写刷新事件
        recyclerView.setRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clear();
                        pageIndex = 0;
                        mPresenter.loadData(type,pageIndex);
                    }
                }, 1000);
            }
        });

        //点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(adapter.getAllData().get(position).getPicUrl());
                data.add(adapter.getAllData().get(position).getUrl());
                Intent intent = new Intent(getActivity(), NewsDetailsActivity.class);
                //用Bundle携带数据
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", data);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


        isViewPrepared = true;

        return view;

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void returnData(List<NewsGson.NewslistBean> datas) {


        adapter.addAll(datas);

        Log.e("adapter",adapter.getAllData().size()+"");
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void lazyFetchDataIfPrepared() {
        Log.e("data",type+""+isViewPrepared+"&&&"+hasFetchData);
        if (isViewPrepared && getUserVisibleHint() && !hasFetchData) {
            lazyFetchData();
            hasFetchData = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //视图销毁 数据设置为空
        hasFetchData=false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyFetchDataIfPrepared();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hasFetchData = false;
        isViewPrepared = false;
    }

    protected void lazyFetchData() {
        mPresenter.loadData(type,pageIndex);
        pageIndex=pageIndex+1;
    }
}
