package com.algonquincollege.arms0333.doors_open_final;

/**
 * Created by codyarmstrong on 2016-12-06.
 */

import android.app.DialogFragment;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.algonquincollege.arms0333.doors_open_final.model.Building;
import com.algonquincollege.arms0333.doors_open_final.model.BuildingJsonParser;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {

    public static final String REST_URI = "https://doors-open-ottawa-hurdleg.mybluemix.net/buildings";
    public static final String IMAGES_BASE_URL = "https://doors-open-ottawa-hurdleg.mybluemix.net/";
    private ProgressBar pb;
    private List<MyTask> tasks;
    private List<Building> buildingList;

    private static final String ABOUT_DIALOG_TAG = "About Dialog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        pb = (ProgressBar) findViewById(R.id.progressBar1);
        pb.setVisibility(View.INVISIBLE);

        tasks = new ArrayList<>();

        if (isOnline()) {
            requestData(REST_URI);// brings  in the data with the http manager
        } else {
            Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
        }

        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Building chosenBuilding = buildingList.get(position);
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("pointofinterestnam", chosenBuilding.getName());
        intent.putExtra("pointofinterestaddr", chosenBuilding.getAddress());
        intent.putExtra("pointofinterestdesc", chosenBuilding.getDescription());
        intent.putStringArrayListExtra("date", (ArrayList<String>) chosenBuilding.getOpen_doors());

        startActivity(intent);
    }

    // starts the menu section
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            DialogFragment newFragment = new AboutDialogFragment();
            newFragment.show(getFragmentManager(), ABOUT_DIALOG_TAG);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void requestData(String uri) {
        MyTask task = new MyTask();
        task.execute(uri);
    }

    protected void updateDisplay() {//Use buildingAdapter to display data
        BuildingAdapter adapter = new BuildingAdapter(this, R.layout.row_list_item, buildingList);
        setListAdapter(adapter);
    }

    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    // ASYNC TASK
    private class MyTask extends AsyncTask<String, String, List<Building>> {
        @Override
        protected void onPreExecute() {
            if (tasks.size() == 0) {
                pb.setVisibility(View.VISIBLE);
            }
            tasks.add(this);
        }

        @Override
        protected List<Building> doInBackground(String... params) {
            String content = HttpManager.getData(params[0], "arms0333", "password");
            buildingList = BuildingJsonParser.parseFeed(content);
            return buildingList;
        }

        @Override
        protected void onPostExecute(List<Building> result) {
            tasks.remove(this);
            if (tasks.size() == 0) {
                pb.setVisibility(View.INVISIBLE);
            }

            if (result == null) {
                Toast.makeText(MainActivity.this, "Web service not available", Toast.LENGTH_LONG).show();
                return;
            }

            buildingList = (result);
            updateDisplay();
        }
    }

}
