package com.java.yangjianke.ui.slideshow;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import butterknife.BindView;
import com.java.yangjianke.R;

public class ClusterResultFragment extends Fragment {
    @BindView(R.id.imageview_cluster)
    ImageView mImg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cluster_result, container, false);
        return view;
    }

}
