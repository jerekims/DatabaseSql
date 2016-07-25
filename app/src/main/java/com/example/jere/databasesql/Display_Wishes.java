package com.example.jere.databasesql;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import data.DatabaseHandler;
import model.myWish;

public class Display_Wishes extends AppCompatActivity {

    private DatabaseHandler dba;
    private ArrayList<myWish> dbawishes = new ArrayList<>();
    private WishAdapter wishadapater;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display__wishes);
        refreshData();
    }

    private void refreshData() {
        dbawishes.clear();
        dba =new DatabaseHandler(getApplicationContext());

        ArrayList<myWish> wishesFromDb =dba.getAllWishes();

        for(int i=0;i<wishesFromDb.size();i++){
            String title =wishesFromDb.get(i).getTitle();
            String content=wishesFromDb.get(i).getContent();
            String date=wishesFromDb.get(i).getRecordate();
            myWish wish = new myWish();
            wish.setTitle(title);
            wish.setContent(content);
            wish.setRecordate(date);

            dbawishes.add(wish);

        }
        dba.close();
        //setting up the adapter
        wishadapater = new WishAdapter(Display_Wishes.this,R.layout.wish_row,dbawishes);
        listview.setAdapter(wishadapater);
        wishadapater.notifyDataSetChanged();
    }

    public class  WishAdapter extends ArrayAdapter<myWish> {
        Activity activity;
        int layoutresource;
        myWish wish;
        ArrayList<myWish> mData=new ArrayList<>();

        public WishAdapter(Activity act, int resource, ArrayList<myWish> data) {
            super(act, resource, data);
            activity=act;
            layoutresource=resource;
            mData=data;
            notifyDataSetChanged();

        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public myWish getItem(int position) {
            return mData.get(position);
        }

        @Override
        public int getPosition(myWish item) {
            return super.getPosition(item);
        }

        @Override
        public long getItemId(int position) {
            return super.getItemId(position);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            viewHolder holder =null;

            if(row ==null || (row.getTag())==null) {
                LayoutInflater inflator = LayoutInflater.from(activity);
                row =inflator.inflate(layoutresource,null);
                holder =new viewHolder();
                holder.mTitle=(TextView)findViewById(R.id.wish_row_title);
                holder.mDate=(TextView)findViewById(R.id.wish_row_date);
                row.setTag(holder);
            }
            else {
                holder =(viewHolder)row.getTag();
            }
            holder.wish =getItem(position);
            holder.mTitle.setText(holder.wish.getTitle());
            holder.mDate.setText(holder.wish.getRecordate());


            return row;
        }

        class viewHolder{
            myWish wish;
            TextView mTitle;
            TextView mId;
            TextView mContext;
            TextView mDate;

        }
    }
}
