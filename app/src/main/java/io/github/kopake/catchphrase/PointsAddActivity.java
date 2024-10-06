package io.github.kopake.catchphrase;

import android.os.Bundle;
import android.view.View;

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

        hideNavigationBar();
    }

    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }


    public void onTeamOneAddButtonClick(View view) {
        //TODO
    }

    public void onTeamTwoAddButtonClick(View view) {
        //TODO
    }

    public void onStartNextRoundButtonClick(View view) {
        //TODO
    }
}