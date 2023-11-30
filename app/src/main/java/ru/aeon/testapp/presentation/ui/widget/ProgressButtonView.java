package ru.aeon.testapp.presentation.ui.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.progressindicator.CircularProgressIndicator;

import ru.aeon.testapp.presentation.util.FormatUtilKt;

/**
 * Created by roman on 22.04.2023
 */
@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal", "unused"})
public class ProgressButtonView extends RelativeLayout {

    private MaterialButton button;
    private CircularProgressIndicator progress;

    private final int indicatorMargin = 16;
    private final int indicatorSize = 24;
    private int indicatorTrackThickness = 4;
    private int buttonPadding = indicatorMargin + indicatorSize + 8;

    private boolean loading;
    private AnimatorListenerAdapter onHideProgress;


    public ProgressButtonView(@NonNull Context context) {
        super(context);
        init(null);
    }

    public ProgressButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressButtonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        Context context = getContext();

        button = new MaterialButton(context, attrs);
        button.setId(generateViewId());

        progress = new CircularProgressIndicator(context);
        progress.setId(generateViewId());
        progress.setVisibility(GONE);

        onHideProgress = new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progress.setVisibility(GONE);
            }
        };
    }

    private void initViews() {
        initButton();
        initProgress();
    }

    private void initButton() {
        var llp = getLayoutParams();
        int width = llp.width == 0 ? LayoutParams.MATCH_PARENT : llp.width;
        LayoutParams lp = new LayoutParams(width, llp.height);
        button.setLayoutParams(lp);
        button.setPadding(FormatUtilKt.dp(buttonPadding), 0, FormatUtilKt.dp(buttonPadding), 0);
        addView(button);
    }

    private void initProgress() {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.setMarginStart(FormatUtilKt.dp(indicatorMargin));
        lp.addRule(ALIGN_PARENT_START);
        lp.addRule(CENTER_VERTICAL);
        progress.setZ(button.getZ() + FormatUtilKt.dp(8));
        progress.setLayoutParams(lp);
        progress.setIndeterminate(true);
        progress.setIndicatorColor(button.getCurrentTextColor());
        progress.setIndicatorSize(FormatUtilKt.dp(indicatorSize));
        progress.setTrackThickness(FormatUtilKt.dp(indicatorTrackThickness));
        addView(progress);
    }

    private void disableParentClipChildren() {
        if (getParent() instanceof ViewGroup parent)
            parent.setClipChildren(false);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        disableParentClipChildren();
        initViews();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeAllViews();
    }
    
    public void setText(CharSequence text) {
        button.setText(text);
    }
    
    public void setText(@StringRes int resId) {
        button.setText(resId);
    }

    public MaterialButton getButton() {
        return button;
    }

    public void showProgress() {
        if (loading)
            return;
        
        loading = true;
        progress.setAlpha(0.0f);
        progress.setVisibility(VISIBLE);
        progress.animate()
                .alpha(1.0f)
                .setDuration(200)
                .setStartDelay(500)
                .setListener(null)
                .start();
    }

    public void hideProgress() {
        if (!loading)
            return;
        
        loading = false;
        progress.animate()
                .alpha(0.0f)
                .setDuration(200)
                .setStartDelay(0)
                .setListener(onHideProgress)
                .start();
    }

    public void setLoading(boolean loading) {
        if (loading)
            showProgress();
        else
            hideProgress();
    }

    public boolean isProgress() {
        return loading;
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        button.setOnClickListener(v -> {
            if (l != null && !loading)
                l.onClick(v);
        });
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putBoolean("loading", loading);
        bundle.putParcelable("superState", super.onSaveInstanceState());
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        var viewState = state;
        if (viewState instanceof Bundle bundle) {
            viewState = bundle.getParcelable("superState");
            loading = bundle.getBoolean("loading");
            if (loading)
                progress.setVisibility(VISIBLE);
        }
        super.onRestoreInstanceState(viewState);
    }

    @Override
    public void setEnabled(boolean enabled) {
        button.setEnabled(enabled);
    }
}
