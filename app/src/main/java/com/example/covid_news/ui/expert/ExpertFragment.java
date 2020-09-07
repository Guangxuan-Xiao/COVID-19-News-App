package com.example.covid_news.ui.expert;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.covid_news.R;

public class ExpertFragment extends Fragment {

    private ExpertViewModel expertViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        expertViewModel =
                ViewModelProviders.of(this).get(ExpertViewModel.class);
        View root = inflater.inflate(R.layout.fragment_expert, container, false);
        final TextView textView = root.findViewById(R.id.text_expert);
        ExpertViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}