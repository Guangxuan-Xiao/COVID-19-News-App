package com.java.yangjianke.ui.expert;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.java.yangjianke.R;
import com.java.yangjianke.data.Expert;
import com.java.yangjianke.data.Expert;
import com.java.yangjianke.network.DownloadImageTask;
import com.java.yangjianke.ui.expert.contract.ExpertContract;
import com.java.yangjianke.ui.expert.presenter.ExpertPresenter;
import com.java.yangjianke.util.PixelUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.util.ArrayList;
import java.util.List;

class ExpertViewHolder extends BaseViewHolder<Expert> {

    private Context context;
    private ImageView avatar;
    private TextView name;
    private TextView position;

    public ExpertViewHolder(ViewGroup parent, Context context) {
        super(parent, R.layout.expert_recycler_item);
        name = $(R.id.name);
        avatar = $(R.id.avatar);
        position = $(R.id.position);
        this.context = context;
    }

    @Override
    public void setData(final Expert data) {
        if (!data.name_zh.equals(""))
            name.setText(data.name_zh);
        else name.setText(data.name);
        position.setText(data.profile.position);
        new DownloadImageTask(avatar).execute(data.avatar);
    }

}

class ExpertAdapter extends RecyclerArrayAdapter<Expert> {
    public ExpertAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExpertViewHolder(parent, this.getContext());
    }
}

public class ExpertClassFragment extends Fragment implements ExpertContract.View {
    private ExpertAdapter adapter;
    private ExpertContract.Presenter mPresenter;

    private boolean isViewPrepared; // 标识fragment视图已经初始化完毕
    private int type;


    public static ExpertClassFragment newInstance(int type) {
        Bundle bundle = new Bundle();
        ExpertClassFragment fragment = new ExpertClassFragment();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @BindView(R.id.recyclerView)
    EasyRecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        type = getArguments().getInt("type");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_class, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new ExpertPresenter(this, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ExpertAdapter(getContext());
        recyclerView.setAdapter(adapter);

        //添加边框
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixelUtil.convertDpToPixel(8, getContext()));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        recyclerView.addItemDecoration(itemDecoration);

        //更多加载
        adapter.setMore(R.layout.view_more, new RecyclerArrayAdapter.OnMoreListener() {
            @Override
            public void onMoreShow() {
                Log.e("更多", "更多");
                mPresenter.loadData();
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
                        mPresenter.loadData();
                    }
                }, 1000);
            }
        });

        //点击事件
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ArrayList<String> data = new ArrayList<String>();
                data.add(adapter.getAllData().get(position).avatar);
                data.add(adapter.getAllData().get(position).name_zh);
                data.add(adapter.getAllData().get(position).name);
                data.add(adapter.getAllData().get(position).profile.position);
                data.add(adapter.getAllData().get(position).profile.affiliation);
                data.add(adapter.getAllData().get(position).profile.work);
                data.add(adapter.getAllData().get(position).profile.edu);
                data.add(adapter.getAllData().get(position).profile.bio);
                Intent intent = new Intent(getActivity(), ExpertDetailsActivity.class);
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
    public void returnData(List<Expert> data) {
        adapter.addAll(data);
        Log.e("adapter", adapter.getAllData().size() + "");
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isViewPrepared = false;
    }
}
