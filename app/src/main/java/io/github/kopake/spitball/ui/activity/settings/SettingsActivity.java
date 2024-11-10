package io.github.kopake.spitball.ui.activity.settings;

import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.settings.SpitballSettings;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        hideNavigationBar();

        ConstraintLayout rootLayout = findViewById(R.id.settingsRootLayout);
        Button button = findViewById(R.id.backButton);

        int guiSeparationDistance = getResources().getDimensionPixelSize(R.dimen.gui_separation_distance);

        // Moves the back button up and down based on the presence of the keyboard
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            Rect rect = new Rect();
            rootLayout.getWindowVisibleDisplayFrame(rect);
            int screenHeight = rootLayout.getRootView().getHeight();

            // Calculate the height difference
            int keypadHeight = screenHeight - rect.bottom;

            ConstraintSet constraintSet = new ConstraintSet();
            constraintSet.clone(rootLayout);

            if (keypadHeight > screenHeight * 0.15) {
                // Keyboard is visible, move button above keyboard
                constraintSet.clear(button.getId(), ConstraintSet.BOTTOM);
                constraintSet.connect(button.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, keypadHeight + guiSeparationDistance);
            } else {
                // Keyboard is hidden, reset button to bottom-right corner
                constraintSet.connect(button.getId(), ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, guiSeparationDistance);
            }

            constraintSet.applyTo(rootLayout);
        });

        // Sets the scrollview separation from the top and bottom to the proper distance (gets messed up because of resizing)
        int statusBarHeight = getStatusBarHeight();
        int combinedMargin = guiSeparationDistance - statusBarHeight;

        ScrollView settingsScrollView = findViewById(R.id.settingsScrollView);
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) settingsScrollView.getLayoutParams();
        params.topMargin = combinedMargin;
        params.bottomMargin = guiSeparationDistance;
        settingsScrollView.setLayoutParams(params);

        setEditTextListener(findViewById(R.id.leftTeamNameEditText));
        setEditTextListener(findViewById(R.id.rightTeamNameEditText));
        setEditTextListener(findViewById(R.id.averageRoundTimeEditText));
        setEditTextListener(findViewById(R.id.pointsNeededToWinEditText));

        loadPreviousValues();
    }

    private void setEditTextListener(EditText editText) {
        ScrollView settingsScrollView = findViewById(R.id.settingsScrollView);
        // Sets editTexts to scroll to view when focused
        View.OnFocusChangeListener editTextFocusChangeListener = (view, hasFocus) -> {
            // Wait for keyboard to appear before scrolling
            // TODO make this more intelligent
            view.postDelayed(() -> scrollToView(settingsScrollView, view), 200);

            // Move the cursor to the end of the edit text
            if (hasFocus) {
                editText.setSelection(editText.getText().length());
            }
        };
        editText.setOnFocusChangeListener(editTextFocusChangeListener);

        TextWatcher editTextTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //TODO change this if changing above in focuslistener
                editText.postDelayed(() -> scrollToView(settingsScrollView, editText), 200);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        editText.addTextChangedListener(editTextTextWatcher);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveValues();
    }

    private void loadPreviousValues() {
        EditText leftTeamNameEditText = findViewById(R.id.leftTeamNameEditText);
        EditText rightTeamNameEditText = findViewById(R.id.rightTeamNameEditText);
        EditText averageRoundTimeEditText = findViewById(R.id.averageRoundTimeEditText);
        EditText pointsNeededToWinEditText = findViewById(R.id.pointsNeededToWinEditText);

        leftTeamNameEditText.setText(SpitballSettings.getLeftTeamName());
        rightTeamNameEditText.setText(SpitballSettings.getRightTeamName());
        averageRoundTimeEditText.setText(String.valueOf(SpitballSettings.getAverageRoundTime()));
        pointsNeededToWinEditText.setText(String.valueOf(SpitballSettings.getPointsNeededToWin()));
    }

    private void saveValues() {
        EditText leftTeamNameEditText = findViewById(R.id.leftTeamNameEditText);
        EditText rightTeamNameEditText = findViewById(R.id.rightTeamNameEditText);
        EditText averageRoundTimeEditText = findViewById(R.id.averageRoundTimeEditText);
        EditText pointsNeededToWinEditText = findViewById(R.id.pointsNeededToWinEditText);

        SpitballSettings.putLeftTeamName(leftTeamNameEditText.getText().toString());
        SpitballSettings.putRightTeamName(rightTeamNameEditText.getText().toString());
        SpitballSettings.putAverageRoundTime(averageRoundTimeEditText.getText().toString());
        SpitballSettings.putPointsNeededToWin(pointsNeededToWinEditText.getText().toString());
    }


    private void hideNavigationBar() {
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void scrollToView(ScrollView scrollView, View view) {
        int[] locationOfView = new int[2];
        view.getLocationOnScreen(locationOfView);
        int viewY = locationOfView[1];

        int[] locationOfScrollView = new int[2];
        scrollView.getLocationOnScreen(locationOfScrollView);
        int scrollY = locationOfScrollView[1];


        scrollView.post(() -> scrollView.smoothScrollTo(0, viewY + scrollView.getScrollY() - scrollY - 64));
    }

    public void onBackButtonClick(View view) {
        view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        goBackToMainMenu();
    }

    @Override
    public void onBackPressed() {
        goBackToMainMenu();
    }

    private void goBackToMainMenu() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
