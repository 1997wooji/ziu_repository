package com.example.ziu.a0808bookinfohomework;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    static final String TAG="0808BookInfoHomework";

    EditText etTitle;
    EditText etAuthor;
    EditText etPlot;

    boolean isDbCreated=false;
    boolean isTableCreated=false;
    SQLiteDatabase db;

    String dbName;
    String tableName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbName="mybook";
        tableName="tbl_book";

        createDatabase(dbName);
        createTable(tableName);

        etTitle=(EditText)findViewById(R.id.etTitle);
        etAuthor=(EditText)findViewById(R.id.etAuthor);
        etPlot=(EditText)findViewById(R.id.etPlot);

        final Button btnSave=(Button)findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int result=insertRecordParam(etTitle.getText().toString(),
                        etAuthor.getText().toString(),
                        etPlot.getText().toString());

                if(result>0){
                    etTitle.setText("");
                    etAuthor.setText("");
                    etPlot.setText("");
                    Toast.makeText(getApplicationContext(),
                            "Insert 성공! XD",Toast.LENGTH_LONG).show();

                }else{
                    Toast.makeText(getApplicationContext(),
                            "Insert 실패 :(",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createDatabase(String dbName){
        try {
            db = openOrCreateDatabase(dbName, MODE_PRIVATE, null);
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
