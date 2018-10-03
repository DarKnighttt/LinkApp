package com.projects.darknight.linkapp;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projects.darknight.linkapp.pojo.Link;

import java.io.IOException;
import java.util.List;

import static android.support.v4.view.PagerAdapter.POSITION_NONE;


public class LinkAdapter extends RecyclerView.Adapter<LinkAdapter.MyViewHolder> {

    private Context context;
    private List<Link> linkList;
    

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView link;
        public FrameLayout parentLayout;
        public View rootView;

        public MyViewHolder(View view) {
            super(view);
            link = view.findViewById(R.id.linkInList);
            rootView = view;
        }
    }

    public LinkAdapter(Context context, List<Link> linkList) {
        this.linkList = linkList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_link, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final Link link = linkList.get(position);
        holder.link.setText(link.getLink());
        switch (link.getStatus()) {
            case 1:
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.link_status_1));
                break;
            case 2:
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.link_status_2));
                break;
            case 3:
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.link_status_3));
                break;
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnline()) {
                    if(link.getStatus() !=3) {
                        Intent intent = new Intent();
                        intent.setAction("com.darknight.imgapp.imgshow");
                        intent.putExtra("appName", context.getResources().getString(R.string.app_name));
                        intent.putExtra("id", link.getId());
                        intent.putExtra("link", link.getLink());
                        intent.putExtra("status", link.getStatus());
                        intent.putExtra("open_time", link.getOpenTime());
                        intent.putExtra("start_time", System.currentTimeMillis());
                        PackageManager packageManager = context.getPackageManager();
                        List activities = packageManager.queryIntentActivities(intent,
                                PackageManager.MATCH_DEFAULT_ONLY);
                        if (activities.size() > 0) context.startActivity(intent);
                        else Toast.makeText(context, "No app found", Toast.LENGTH_SHORT).show();
                    }else  new AlertDialog.Builder(context).setMessage("This URL is incorrect. Cant open it!").setCancelable(false).setPositiveButton(R.string.btn_ok, null).create().show();
                }else new AlertDialog.Builder(context).setMessage("Check your internet connection").setCancelable(false).setPositiveButton(R.string.btn_ok,null).create().show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return linkList.size();
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

    @Override
    public long getItemId(int position) {
        return POSITION_NONE;
    }
}
