package com.example.ionut.infoschedule;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.ionut.infoschedule.home.TeachersAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    private static int TIMEOUT_TIME = 10000;
    public static final String URL = "https://profs.info.uaic.ro/~orar/orar_profesori.html";
    @BindView(R.id.rv_teachers)
    RecyclerView rvTeachers;

    private ArrayList<String> teachers;
    private TeachersAdapter adapter;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        setAdapter();
        Description description = new Description();
        description.execute();
    }

    private void setAdapter(){
        teachers = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvTeachers.setLayoutManager(layoutManager);
        adapter = new TeachersAdapter(teachers);
        rvTeachers.setAdapter(adapter);
    }

    private void updateUI(ArrayList<String> newData){
        teachers.clear();
        teachers.addAll(newData);
        adapter.notifyDataSetChanged();
    }

    private class Description extends AsyncTask<Void, ArrayList<String>, ArrayList<String>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(HomeActivity.this);
            mProgressDialog.setTitle("Fetching data");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            ArrayList<String> teachers = new ArrayList<>();
            try {
                // Connect to the web site
                Document doc = Jsoup.connect(URL).timeout(TIMEOUT_TIME).get();
                Element tr = doc.getElementsByTag("tr").get(0);
                Elements td = tr.getElementsByTag("td");

                for (Element element : td){
                    for (Element el : element.getElementsByTag("li")){
                        String teacherName = el.text();
                        teachers.add(teacherName.contains(",") ? teacherName.split(",")[0] : teacherName);
                        //Element elem = el.getElementsByTag("a").get(0);
                        //teachers.add(elem.attr("href"));
                        // TODO: 12/3/2018 Uncomment to get also teacher's schedule url 
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return teachers;
        }

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            // Set description into TextView
            updateUI(result);
            mProgressDialog.dismiss();
        }
    }
}
