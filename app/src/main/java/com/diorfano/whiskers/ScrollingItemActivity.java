package com.diorfano.whiskers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.diorfano.models.Cat;
import com.squareup.picasso.Picasso;
public class ScrollingItemActivity extends AppCompatActivity {

    Toolbar mToolbar;
    Cat mCat;
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    TextView mScrollbarText;
    ImageView iv_backgroundImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling_item);

        Intent intent = getIntent();
        mCat = intent.getExtras().getParcelable("cat");

        mToolbar = (Toolbar) findViewById(R.id.mToolbar);
        mScrollbarText = (TextView) findViewById(R.id.mScrollbarText);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.mCollapsingToolbarLayout);
        mCollapsingToolbarLayout.setTitle(mCat.getBreed());
        iv_backgroundImage = (ImageView) findViewById(R.id.iv_backgroundImage);


        setSupportActionBar(mToolbar);

        String foodText = mCat.getPrefered_food().getPackage1() == null ?
                mCat.getPrefered_food().getName() :
                mCat.getPrefered_food().getName() + " in a " + mCat.getPrefered_food().getPackage1() + " package";

        mScrollbarText.setText(Html.fromHtml(
                "<h2>Breed : " + mCat.getBreed() + "</h2><br>" +
                        "<h2>Legs : " + mCat.getLegs() + "</h2><br>" +
                        "<h2>Preferred Food : " + foodText + "</h2><br>" +
                        "<h2>Colour : " + mCat.getColour() + "</h2><br>" +
                        "<h2>Size : " + mCat.getSize() + "</h2><br>" +
                        "<h2>Whiskers : " + mCat.getWhiskers() + "</h2><br>"
        ));

        iv_backgroundImage.setImageBitmap(null);
        Picasso.with(this).cancelRequest(iv_backgroundImage);

        int id = this.getResources().getIdentifier(mCat.getImage() + "", "drawable", this.getPackageName());
        if (id != 0) {
            Picasso.with(this).load(id).resize((int) getScrennsize().xdpi, (int) getScrennsize().ydpi).centerCrop().into(iv_backgroundImage);
        }else {
            Picasso.with(this).load("http://www.treffpunkt-regional.com/wp-content/themes/white/assets/images/placeholder.jpg").resize((int) getScrennsize().xdpi, (int) getScrennsize().ydpi).into(iv_backgroundImage);
        }

    }


    public DisplayMetrics getScrennsize() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

}