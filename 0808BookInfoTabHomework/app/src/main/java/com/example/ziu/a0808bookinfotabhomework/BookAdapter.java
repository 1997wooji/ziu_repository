package com.example.ziu.a0808bookinfotabhomework;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class BookAdapter extends BaseAdapter {


    Context context=null;
    ArrayList<BookInfo> data=null;
    LayoutInflater layoutInflater=null;

    public BookAdapter(Context context, ArrayList<BookInfo> data){
        this.context=context;
        this.data=data;
        this.layoutInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i; //잉??
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        View itemLayout=view;
        ViewHolder viewHolder=null;

        if(itemLayout==null){
            itemLayout=layoutInflater.inflate(R.layout.bookinfo,null);

            viewHolder=new ViewHolder();
            viewHolder.tvTitle=(TextView)itemLayout.findViewById(R.id.tvTitle);
            viewHolder.tvAuthor=(TextView)itemLayout.findViewById(R.id.tvAuthor);
            viewHolder.imageV=(ImageView) itemLayout.findViewById(R.id.imageV);
            itemLayout.setTag(viewHolder);

        }else{


            viewHolder=(ViewHolder)itemLayout.getTag();
        }

        viewHolder.imageV.setImageResource(R.drawable.bookflat);
        viewHolder.tvTitle.setText(data.get(position).getTitle());
        viewHolder.tvAuthor.setText(data.get(position).getAuthor());

        return itemLayout;
    }

    class ViewHolder{
        ImageView imageV;
        TextView tvTitle;
        TextView tvAuthor;
    }
}
