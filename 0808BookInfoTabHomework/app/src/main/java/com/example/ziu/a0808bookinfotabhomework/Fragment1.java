package com.example.ziu.a0808bookinfotabhomework;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment1 extends Fragment {


    static final String TAG="0808 BookInfoTabHomework";

    String dbName;
    String tableName;

    boolean isDbCreated=false;
    boolean isTableCreated=false;
    SQLiteDatabase db;

    EditText etTitle=null;
    EditText etAuthor=null;
    EditText etPlot=null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1,container,false);

        dbName="mybook";
        tableName="tbl_book";

        createDatabase(dbName);
        createTable(tableName);

        etTitle=(EditText)view.findViewById(R.id.etTitle);
        etAuthor=(EditText)view.findViewById(R.id.etAuthor);
        etPlot=(EditText)view.findViewById(R.id.etPlot);

        Button btnSave=(Button)view.findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int result=insertRecordParam(etTitle.getText().toString(),
                        etAuthor.getText().toString(),
                        etPlot.getText().toString());

                if(result>0){
                    etTitle.setText("");
                    etAuthor.setText("");
                    etPlot.setText("");
                    Toast.makeText(view.getContext(),"Insert 성공! XD",Toast.LENGTH_LONG).show();
                    //view.getcontext가 되는지는 모르겠지만..
                }else{
                    Toast.makeText(view.getContext(),
                            "Insert 실패 :(",Toast.LENGTH_LONG).show();
                }
            }
        });

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

    private int insertRecordParam(String title, String author, String plot){

        ContentValues values=new ContentValues();
        values.put("title",title);
        values.put("author",author);
        values.put("plot",plot);

        return (int)db.insert(tableName,null,values);
    }


}
