package io.github.kopake.spitball.ui.activity.gameinprogress;

import static androidx.core.content.ContextCompat.getColor;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

import androidx.appcompat.widget.AppCompatButton;

import io.github.kopake.spitball.R;
import io.github.kopake.spitball.Spitball;
import io.github.kopake.spitball.event.DisabledNextButtonPressEvent;
import io.github.kopake.spitball.event.EventManager;
import io.github.kopake.spitball.event.NextButtonPressEvent;


public class NextButton extends AppCompatButton {

    private int enabledColor;
    private boolean isDisabled = false;
    private float progressAngle = 0f;
    private Paint paint;

    public NextButton(Context context) {
        super(context);
        init(null);
    }

    public NextButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public NextButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        // Set the background to transparent
        setBackground(null);

        enabledColor = getColor(getContext(), R.color.colorAccent);

        if (attrs != null) {
            TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.NextButton);
            enabledColor = typedArray.getColor(R.styleable.NextButton_buttonColor, enabledColor);
            typedArray.recycle();
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }

    @Override
    public boolean performClick() {
        // Ignore touch events when disabled
        if (isDisabled) {
            EventManager.getInstance().dispatchEvent(new DisabledNextButtonPressEvent());
            return false;
        }
        EventManager.getInstance().dispatchEvent(new NextButtonPressEvent());
        getRootView().performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP);
        popAnimation();
        enterDisabledState();
        return super.performClick();
    }

    private void enterDisabledState() {
        isDisabled = true;
        progressAngle = 0;

        SharedPreferences sharedPreferences = Spitball.getSharedPreferences();
        int disabledDuration = 1000 * sharedPreferences.getInt("next_button_refractory_time", 2);

        if (disabledDuration != 0) {
            ValueAnimator animator = ValueAnimator.ofFloat(0, 360);
            animator.setInterpolator(new DecelerateInterpolator());
            animator.setDuration(disabledDuration);
            animator.addUpdateListener(animation -> {
                progressAngle = (float) animation.getAnimatedValue();
                invalidate();
            });
            animator.start();
        }


        new Handler().postDelayed(this::exitDisabledState, disabledDuration);
    }

    private void exitDisabledState() {
        isDisabled = false;
        invalidate();
        popAnimation();
    }

    private void popAnimation() {
        ObjectAnimator scaleAnim = ObjectAnimator.ofPropertyValuesHolder(
                this,
                PropertyValuesHolder.ofFloat("scaleX", 1.05f, 1.0f),
                PropertyValuesHolder.ofFloat("scaleY", 1.05f, 1.0f)
        );
        scaleAnim.setDuration(300);
        scaleAnim.setInterpolator(new AccelerateDecelerateInterpolator());
        scaleAnim.start();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //Set the text to be 1/3 the height of the button
        setTextSize(TypedValue.COMPLEX_UNIT_PX, h / 3f);
    }

    @Override
    @SuppressLint("DrawAllocation")
    protected void onDraw(Canvas canvas) {
        int width = getWidth();
        int height = getHeight();
        int radius = Math.min(width, height) / 2;

        float cornerRadius = Math.min(width, height) / 2.0f;

        int backgroundColor = isDisabled ? Color.GRAY : enabledColor;

        paint.setColor(backgroundColor);
        RectF rect = new RectF(0, 0, width, height);
        canvas.drawRoundRect(rect, cornerRadius, cornerRadius, paint);

        canvas.save();

        // Create a path for clipping the pie chart so it only shows inside the button's bounds
        Path buttonPath = new Path();
        buttonPath.addRoundRect(rect, cornerRadius, cornerRadius, Path.Direction.CW);
        canvas.clipPath(buttonPath);

        if (isDisabled) {
            paint.setColor(Color.argb(200, 200, 200, 200));

            RectF pieRect = new RectF(-radius, -radius, width + radius, height + radius);
            canvas.drawArc(pieRect, -90, progressAngle, true, paint);
        }

        canvas.restore();
        super.onDraw(canvas);
    }
}
