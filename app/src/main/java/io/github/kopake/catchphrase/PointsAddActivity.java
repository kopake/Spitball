package io.github.kopake.catchphrase;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PointsAddActivity extends AppCompatActivity {

    private static PointsAddActivity instance;

    public static PointsAddActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_points_add);

        instance = this;
    }
}