package com.zeus.javadevs;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zeus.javadevs.dummy.DevContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * An activity representing a list of devs. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link devDetailActivity} representing
 * item profileURL. On tablets, the activity presents the list of items and
 * item profileURL side-by-side using two vertical panes.
 */
public class devListActivity extends AppCompatActivity {

    List<Bitmap> profileDP = new ArrayList<>();
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Snackbar.make(view, "Closing App", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                finish();
            }
        });

        new JsonReader().execute();


        //View recyclerView = findViewById(R.id.dev_list);
        //assert recyclerView != null;
        //setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.dev_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(DevContent.ITEMS));
    }

    static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];

            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DevContent.DummyItem> mValues;

        public SimpleItemRecyclerViewAdapter(List<DevContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.dev_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mItem = mValues.get(position);

            /*profileDP.add(position, null);

            if (profileDP.get(position) == null) {
                new DownloadImageTask(profileDP.get(position)).execute(mValues.get(position).profilePix);
            }*//*

            holder.mIdView.setImageBitmap(mValues.get(position).profileDP);*/

            new DownloadImageTask(holder.mIdView).execute(mValues.get(position).profilePix);
            holder.mContentView.setText(mValues.get(position).username);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(devDetailFragment.ARG_ITEM_USERNAME, holder.mItem.username);
                        devDetailFragment fragment = new devDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.dev_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, devDetailActivity.class);
                        intent.putExtra(devDetailFragment.ARG_ITEM_USERNAME, holder.mItem.username);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mIdView;
            public final TextView mContentView;
            public DevContent.DummyItem mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mIdView = (ImageView) view.findViewById(R.id.profile_pix);
                mContentView = (TextView) view.findViewById(R.id.content);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mContentView.getText() + "'";
            }
        }
    }

    public class JsonReader extends AsyncTask<Void, Void, Void> {

        public JsonReader() {

            //onPreExecute();
            //doInBackground();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Json Data is downloading", Toast.LENGTH_LONG).show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            View recyclerView = findViewById(R.id.dev_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView);

            super.onPostExecute(aVoid);

        }

        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();
            // Making a request to url and getting response
            String url = "https://api.github.com/search/users?q=+location:Lagos+language:java";//?access_token=0a1bec29480cc0709e0e2e8f6fe4e9432b6c8338&per_page=100";
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);
            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray devs = jsonObj.getJSONArray("items");

                    // looping through All Contacts
                    for (int i = 0; i < devs.length(); i++) {
                        JSONObject dev = devs.getJSONObject(i);
                        String username = dev.getString("login");
                        String profileUrl = dev.getString("html_url");
                        String profilePix = dev.getString("avatar_url");

                        DevContent.DummyItem item = new DevContent.DummyItem(String.valueOf(i), username, profileUrl, profilePix);
                        DevContent.ITEMS.add(item);
                        DevContent.ITEM_MAP.put(item.username, item);
                        // tmp hash map for single contact
                        //HashMap<String, String> contact = new HashMap<>();

                        // adding each child node to HashMap key => value
                    /*contact.put("id", id);
                    contact.put("name", name);
                    contact.put("email", email);
                    contact.put("mobile", mobile);*/

                        // adding contact to contact list
                        //contactList.add(contact);
                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });

                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG).show();
                    }
                });
            }

            return null;
        }
    }

}
