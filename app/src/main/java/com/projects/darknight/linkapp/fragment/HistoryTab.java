package com.projects.darknight.linkapp.fragment;


import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.projects.darknight.linkapp.LinkAdapter;
import com.projects.darknight.linkapp.R;
import com.projects.darknight.linkapp.database.LinkContentProvider;
import com.projects.darknight.linkapp.database.LinksDbCotract;
import com.projects.darknight.linkapp.pojo.Link;

import java.util.ArrayList;
import java.util.List;


public class HistoryTab extends Fragment {

    List<Link> linkList = new ArrayList<>();
    LinkAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.linksList);
        getLinks("_id ASC");
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_history, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sortByNew:
                getLinks("_id DESC");
                break;
            case R.id.sortByStatus:
                getLinks(LinksDbCotract.LinkTable.COLUMN_STATUS + " ASC");
                break;
            case R.id.clearIncorrecturl:
                clearIncorrectUrl();
                break;
            default:
                Toast.makeText(getContext(), "Please choose some option", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    public void getLinks(String sortOrder) {
        Cursor cursor = getContext().getContentResolver().query(LinkContentProvider.LINK_URI, null, null, null, sortOrder);
        linkList.clear();
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Link link = new Link();
                link.setId(Integer.parseInt(cursor.getString(0)));
                link.setLink(cursor.getString(1));
                link.setStatus(Integer.parseInt(cursor.getString(2)));
                link.setOpenTime(Double.parseDouble(cursor.getString(3)));
                linkList.add(link);
            } while (cursor.moveToNext());
        }
        if (adapter == null) adapter = new LinkAdapter(getContext(), linkList);
        else adapter.notifyDataSetChanged();
    }

    public void clearIncorrectUrl() {
        if(!linkList.isEmpty()) {
            new AlertDialog.Builder(getContext()).setMessage("Clear All history or only incorrect Url?").setCancelable(false)
                    .setPositiveButton(R.string.btn_incorrect, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getContext().getContentResolver().delete(LinkContentProvider.LINK_URI, LinksDbCotract.LinkTable.COLUMN_STATUS + "=?", new String[]{String.valueOf(3)});
                            getLinks("_id ASC");
                        }
                    })
                    .setNegativeButton(R.string.btn_all, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getContext().getContentResolver().delete(LinkContentProvider.LINK_URI, null, null);
                            getLinks("_id ASC");
                        }
                    }).create().show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getLinks("_id ASC");
    }
}
