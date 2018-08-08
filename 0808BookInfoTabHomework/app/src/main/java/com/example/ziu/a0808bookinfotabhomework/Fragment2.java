package com.example.ziu.a0808bookinfotabhomework;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {

    private ArrayList<BookInfo> data=null;
    ListView listview=null;

    static final String TAG="0808 BookInfoTabHomework";

    String dbName;
    String tableName;

    boolean isDbCreated=false;
    boolean isTableCreated=false;
    SQLiteDatabase db;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2,container,false);

        dbName="mybook";
        tableName="tbl_book";

        createDatabase(dbName);
        createTable(tableName);

        data=selectRecord();

        BookAdapter adapter=new BookAdapter(view.getContext(),data);

        //리스트 뷰에 어댑터 설정
        listview = (ListView)view.findViewById(R.id.listview);
        listview.setAdapter(adapter);

        return view;
    }


    private void createDatabase(String dbName){
        try {
            db = getActivity().openOrCreateDatabase(dbName,android.content.Context.MODE_PRIVATE,null);
            isDbCreated = true;
            Log.d(TAG,"DB 생성 성공");
        }
        catch(Exception ex){
            ex.printStackTrace();
            Log.d(TAG,"DB 생성 실패 :(");
        }

    }

    private void createTable(String tableName){

        if(isDbCreated){
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                        "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " title text," +
                        " author text," +
                        " plot text);");

                isTableCreated=true;
                Log.d(TAG,"Table 생성 성공");
            }
            catch(Exception e){
                e.printStackTrace();
                Log.d(TAG,"Table 생성 실패 :(");
            }

        }else{
            Log.d(TAG,"Table 생성 실패, DB가 생성되있지 않음 :(");
        }

    }

    private ArrayList<BookInfo> selectRecord(){

        ArrayList<BookInfo> list=new ArrayList<>();

        String sql="SELECT title,author from " +tableName;
        String[] args=null;
        Cursor c=db.rawQuery(sql,args);
        int count=c.getCount();

        if(count>0){

            while(c.moveToNext()){
                list.add(new BookInfo(c.getString(0),c.getString(1)));
            }


        }

        return list;
    }


}
