package com.projects.darknight.linkapp.fragment;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.projects.darknight.linkapp.R;

import java.io.IOException;
import java.util.List;

public class TestTab extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_test, null);
        final Button btnOk = view.findViewById(R.id.btnOk);
        final EditText linkText = view.findViewById(R.id.linkText);
        linkText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                btnOk.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (linkText.length() == 0) {
                    linkText.setError("Input required!");
                    btnOk.setVisibility(View.INVISIBLE);
                } else
                    linkText.setError(null);
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()) {
                    Intent intent = new Intent();
                    intent.setAction("com.darknight.imgapp.imgshow");
                    intent.putExtra("appName", getResources().getString(R.string.app_name));
                    intent.putExtra("id", 0);
                    intent.putExtra("link", linkText.getText().toString());
                    intent.putExtra("status", 0);
                    intent.putExtra("open_time", 0);
                    intent.putExtra("start_time", System.currentTimeMillis());
                    PackageManager packageManager = view.getContext().getPackageManager();
                    List activities = packageManager.queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY);
                    if (activities.size() > 0) startActivity(intent);
                    else
                        new AlertDialog.Builder(getContext()).setMessage("No app found to show this link.\nMake Sure you have installed ImpApp!").setCancelable(false).setPositiveButton(R.string.btn_ok, null).create().show();
                } else
                    new AlertDialog.Builder(getContext()).setMessage("Check your internet connection").setCancelable(false).setPositiveButton(R.string.btn_ok, null).create().show();
            }
        });
        return view;
    }

    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return false;
    }
}
