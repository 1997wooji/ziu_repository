package com.example.ziu.a0806mymovie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    TextView tvDirector,tvActor,tvPlot, tvGenre, tvRelease;
    Button btnSearch;
    ImageView ivPoster;
    EditText etTitle;
    MySearchTask task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDirector=(TextView)findViewById(R.id.tvDirector);
        tvActor=(TextView)findViewById(R.id.tvActor) ;
        tvPlot=(TextView)findViewById(R.id.tvPlot) ;
        tvGenre=(TextView)findViewById(R.id.tvGenre) ;
        tvRelease=(TextView)findViewById(R.id.tvRelease) ;
        btnSearch=(Button)findViewById(R.id.btnSearch);
        ivPoster=(ImageView)findViewById(R.id.ivPoster);
        etTitle=(EditText)findViewById(R.id.etTitle);

        task=new MySearchTask();

        btnSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                if(etTitle.getText()!=null&& ! etTitle.getText().toString().isEmpty()){
                    //비어있지 않으면
                    task.execute(etTitle.getText().toString());

                }else{
                    //비어있으면
                     Toast.makeText(getApplicationContext(),"Please Input Title :(",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    class MySearchTask extends AsyncTask<String, String, String>{

        String urlStr=null;
        StringBuilder sb=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sb=new StringBuilder();

            tvDirector.setVisibility(View.INVISIBLE);
            tvActor.setVisibility(View.INVISIBLE);
            tvPlot.setVisibility(View.INVISIBLE);
            tvGenre.setVisibility(View.INVISIBLE);
            tvRelease.setVisibility(View.INVISIBLE);
            ivPoster.setVisibility(View.INVISIBLE);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            if(values[0].equals("error")){
                Toast.makeText(getApplicationContext(),values[1],Toast.LENGTH_LONG).show();
            }else if(values[0].equals("OK")){

                tvDirector.setVisibility(View.VISIBLE);
                tvActor.setVisibility(View.VISIBLE);
                tvPlot.setVisibility(View.VISIBLE);
                tvGenre.setVisibility(View.VISIBLE);
                tvRelease.setVisibility(View.VISIBLE);

                tvRelease.setText(values[1]);
                tvGenre.setText(values[2]);
                tvDirector.setText(values[3]);
                tvActor.setText(values[4]);
                tvPlot.setText(values[5]);

                final String imgUri=values[6];

                if(imgUri!=null) {
                    //poster가 null일 수도 있으므로

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            try {
                                URL imageUri = new URL(imgUri);
                                HttpURLConnection conn2 = (HttpURLConnection) imageUri.openConnection();
                                conn2.connect();
                                conn2.setRequestMethod("GET");
                                BufferedInputStream bis = new BufferedInputStream(conn2.getInputStream());
                                final Bitmap bm = BitmapFactory.decodeStream(bis);
                                System.out.println(bm != null);

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        ivPoster.setVisibility(View.VISIBLE);
                                        ivPoster.setImageBitmap(bm);
                                    }
                                });

                                bis.close();
                                conn2.disconnect();

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
            }

        }

        @Override
        protected void onCancelled(String result) {
            super.onCancelled(result);
        }

        @Override
        protected String doInBackground(String... params) {

            urlStr="http://www.omdbapi.com/?apikey=57531d86&t="+params[0];

            String release=null;
            String genre=null;
            String director=null;
            String actors=null;
            String plot=null;
            String poster=null;

            try {
                URL url =new URL(urlStr);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);

                int responseCode = conn.getResponseCode();


                if (responseCode == HttpURLConnection.HTTP_OK) {

                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    JSONObject json=new JSONObject(sb.toString());
                    release=(String)json.get("Released");
                    genre=(String)json.get("Genre");
                    director=(String)json.get("Director");
                    actors=(String) json.get("Actors");
                    plot=(String)json.get("Plot");
                    poster=(String)json.get("Poster");

                    publishProgress("OK",release,genre,director,actors,plot,poster);

                } else {

                    publishProgress("error","No movie exists :(");
                }


            }
            catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
}
