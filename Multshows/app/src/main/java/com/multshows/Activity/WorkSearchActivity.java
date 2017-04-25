package com.multshows.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.multshows.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作品
 */
public class WorkSearchActivity extends AppCompatActivity {

    @Bind(R.id.Return)
    TextView mReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_search);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.Return)
    public void onClick() {
        finish();
    }
}
