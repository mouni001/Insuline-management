package com.example.sqlite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    private Button Notification, ExportHistory, PrivacyPolicy;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Notification = rootView.findViewById(R.id.Notification);
        Notification.setOnClickListener(notificationListener);

        ExportHistory = rootView.findViewById(R.id.ExportHistory);
        ExportHistory.setOnClickListener(exportListener);

        PrivacyPolicy = rootView.findViewById(R.id.PrivacyPolicy);
        PrivacyPolicy.setOnClickListener(privacyListener);
        return rootView;


    }

    private View.OnClickListener notificationListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NotificationFragment notificationPage = new NotificationFragment();
            ((MainActivity) getActivity()).fragmentOpenner(notificationPage);

        }
    };

    private View.OnClickListener exportListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Feature not supported yet.", Toast.LENGTH_SHORT).show();
        }
    };

    private View.OnClickListener privacyListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity(), "Feature not supported yet.", Toast.LENGTH_SHORT).show();
        }
    };
}
