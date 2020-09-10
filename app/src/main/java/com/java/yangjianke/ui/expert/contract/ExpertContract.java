package com.java.yangjianke.ui.expert.contract;

import com.java.yangjianke.data.Expert;
import com.java.yangjianke.data.News;

import java.util.List;

public interface ExpertContract {
    interface View {
        void returnData(List<Expert> data);
    }

    interface OnLoadFirstDataListener {
        void onSuccess(List<Expert> list);

        void onFailure(String str, Throwable e);
    }

    interface Presenter {
        void loadData();
    }

    interface Model {
        void loadData(OnLoadFirstDataListener listener);
    }
}
