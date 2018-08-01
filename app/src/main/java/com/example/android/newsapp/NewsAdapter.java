package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class NewsAdapter extends ArrayAdapter<News> {

    public NewsAdapter(Context context, int resource) {
        super(context, resource);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View ListView = convertView;
        News news = getItem(position);

        if (ListView == null) {

            ListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.news_row, parent, false);
        }

        TextView title = (TextView) ListView.findViewById(R.id.title);
        TextView author = (TextView) ListView.findViewById(R.id.author_name);
        title.setText(news.getTitleName());
        author.setText(news.getAuthorName());
        TextView section_name = (TextView) ListView.findViewById(R.id.section_name);
        section_name.setText(news.getSectionName());
        TextView date = (TextView) ListView.findViewById(R.id.date);
        date.setText(news.getDate());

        return ListView;
    }
}