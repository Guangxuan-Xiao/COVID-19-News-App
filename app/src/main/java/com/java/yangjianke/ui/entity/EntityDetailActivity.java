package com.java.yangjianke.ui.entity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.java.yangjianke.R;
import com.java.yangjianke.data.DataBase;
import com.java.yangjianke.data.Entity;
import com.java.yangjianke.data.RelatedEntity;
import com.java.yangjianke.data.Relation;
import com.java.yangjianke.network.DownloadImageTask;
import com.java.yangjianke.util.PixelUtil;
import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.SpaceDecoration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EntityDetailActivity extends AppCompatActivity {
    @BindView(R.id.relatedEntityRecyclerView)
    EasyRecyclerView relatedEntityRecyclerView;
    @BindView(R.id.propertyRecyclerView)
    EasyRecyclerView propertyRecyclerView;
    @BindView(R.id.entity_name_detail)
    TextView entityNameTextView;
    @BindView(R.id.entity_image)
    ImageView entityImage;
    @BindView(R.id.baidu)
    TextView entityInfo;
    @BindView(R.id.toolbarEntityDetail)
    Toolbar toolbar;
    @BindView(R.id.title_entity_detail)
    TextView mTitle;

    private EntityRelationAdapter adapter;

    private PropertyAdapter adapter1;

    private List<RelatedEntity> relatedEntity;

    private List<String> properties;

    public void returnRelationData(){
        adapter.addAll(relatedEntity);
    }

    public void returnPropertyData(){
        adapter1.addAll(properties);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_detail);
        ButterKnife.bind(this);
        relatedEntity = new ArrayList<RelatedEntity>();
        adapter = new EntityRelationAdapter(this);
        SpaceDecoration itemDecoration = new SpaceDecoration((int) PixelUtil.convertDpToPixel(8, this));
        itemDecoration.setPaddingEdgeSide(true);
        itemDecoration.setPaddingStart(true);
        itemDecoration.setPaddingHeaderFooter(false);
        relatedEntityRecyclerView.addItemDecoration(itemDecoration);
        relatedEntityRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        relatedEntityRecyclerView.setAdapter(adapter);

        adapter1 = new PropertyAdapter(this);
        propertyRecyclerView.addItemDecoration(itemDecoration);
        propertyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        propertyRecyclerView.setAdapter(adapter1);

        //新页面接收数据
        Bundle bundle = this.getIntent().getExtras();
        final ArrayList<String> data = bundle.getStringArrayList("data");
        ArrayList<Relation> relations = bundle.getParcelableArrayList("relations");
        properties = bundle.getStringArrayList("properties");
        returnPropertyData();
        //System.out.println(relations);
        entityNameTextView.setText(data.get(0));
        entityInfo.setText(data.get(1));
        String url = data.get(2);
        if (url != null){
            new DownloadImageTask(entityImage).execute(data.get(2));
        }
        else{
            entityImage.setImageResource(R.drawable.default_img);
        }
        DataBase db = new DataBase(this);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 123) {
                    returnRelationData();
                }
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                int cnt = 0;
                for (Relation target: relations){
                    String targetLabel = target.label;
                    //System.out.println(targetLabel);
                    List<Entity> tmp_list = db.getEntityList(targetLabel);
                    if (tmp_list.size() == 0){
                        continue;
                    }
                    Entity targetEntity = db.getEntityList(targetLabel).get(0);
                    RelatedEntity relatedTargetEntity = new RelatedEntity();
                    relatedTargetEntity.entity = targetEntity;
                    relatedTargetEntity.relation = target.relation;
                    relatedEntity.add(relatedTargetEntity);
                    cnt += 1;
                    if (cnt == 20){
                        break;
                    }
                }
                handler.sendEmptyMessage(123);
            }
        }).start();
        toolbar.setTitle("实体详情");
        setSupportActionBar(toolbar);
//        设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Activity a = this;
        adapter.setOnItemClickListener(new RecyclerArrayAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                final ArrayList<String> data = new ArrayList<String>();
                data.add(adapter.getAllData().get(position).entity.label);
                data.add(adapter.getAllData().get(position).entity.getInfo());
                URL url = adapter.getAllData().get(position).entity.img;
                if (url != null)
                    data.add(adapter.getAllData().get(position).entity.img.toString());
                else
                    data.add(null);
                List<Relation> relations =
                        adapter.getAllData().get(position).entity.getRelations();
                ArrayList<String> properties =
                        adapter.getAllData().get(position).entity.getProperties();
                Intent intent = new Intent(a, EntityDetailActivity.class);
                //用Bundle携带数据
                final Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", data);
                bundle.putStringArrayList("properties", properties);
                bundle.putParcelableArrayList("relations", (ArrayList<? extends Parcelable>) relations);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
