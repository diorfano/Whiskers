package com.diorfano.whiskers;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.diorfano.com.diorfano.volley.WebPost;
import com.diorfano.models.Cat;
import com.diorfano.models.Cat.Food;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainListActivity extends AppCompatActivity {

    public static String mUrl = "http://www.dekaimero.eu/json.html";
    SwipeRefreshLayout mSwipeRefreshLayout;
    RecyclerView mRecyclerView;
    MyRecyclerAdapter mAdapter;
    ArrayList<Cat> mCatList = new ArrayList<>();
    LinearLayoutManager mLLManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefreshLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);

        mLLManager = new LinearLayoutManager(getApplicationContext());
        mAdapter = new MyRecyclerAdapter(this, mCatList, R.layout.list_item_row);
        mRecyclerView.setLayoutManager(mLLManager);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setHasFixedSize(true);

        getData();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mCatList.clear();
                getData();
            }
        });

    }

    public void getData() {

        new WebPost(getApplicationContext()) {

            @Override
            public void parseValidJson(JSONObject jsonResponse) throws Exception {

                HashMap<String, String> mFoodHashMap = new HashMap<>();

                try {

                    JSONArray foods = jsonResponse.getJSONArray("food");
                    if (foods != null) {
                        for (int i = 0; i < foods.length(); i++) {
                            mFoodHashMap.put(foods.getJSONObject(i).getString("name"), foods.getJSONObject(i).getString("package"));
                        }
                    }

                    JSONArray cats = jsonResponse.getJSONArray("cats");
                    if (cats != null) {
                        for (int i = 0; i < cats.length(); i++) {

                            Cat cat = new Cat(
                                    cats.getJSONObject(i).getString("breed"),
                                    cats.getJSONObject(i).getInt("legs"),
                                    cats.getJSONObject(i).getJSONObject("image").getString("xxhdpi"),
                                    cats.getJSONObject(i).getString("colour"),
                                    cats.getJSONObject(i).getString("size"),
                                    cats.getJSONObject(i).getInt("whiskers"));

                            Food food = new Food(cats.getJSONObject(i).getString("prefered-food"),
                                    mFoodHashMap.get(cats.getJSONObject(i).getString("prefered-food")));

                            cat.setPrefered_food(food);

                            mCatList.add(cat);
                        }
                    }

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "mCatList set error " + e.toString(), Toast.LENGTH_LONG).show();
                }

                onItemsLoadComplete(mCatList.size() > 0, "List is Loaded");

            }

            @Override
            public void onPostFailed() {

                onItemsLoadComplete(false, "Failed to load list");

            }
        }.post();

    }

    void onItemsLoadComplete(Boolean success, String message) {

        if (success) {
            mAdapter.notifyDataSetChanged();
        }

        mSwipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

    }
}