package oysd.com.trade_app.base;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.android.ActivityEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import oysd.com.trade_app.R;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2018/7/11
 * @desc 封装带有 TitleBar 及各种 tools 的 Activity.
 */
public abstract class BaseToolActivity extends BaseActivity {

    // 默认显示 TitleBar.
    private boolean titleBarShowStatus = true;
    private boolean titleBarExist = true;

    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout rlTitleBar;
    private LinearLayout llMenuContainer;
    private TextView tvBack;
    private TextView tvTitle;
    private ImageView ivMenu01;
    private ImageView ivMenu02;
    private TextView tvMenu;

    @Override
    protected void initContentView() {
        if (setView() <= 0) {
            throw new IllegalArgumentException("must set view.");
        }
        if (setViewInRefresh()) {
            setContentView(R.layout.activity_base_tool);
            swipeRefreshLayout = find(R.id.srl_base_refresh);
            FrameLayout container = find(R.id.fl_base_container);
            LayoutInflater.from(this).inflate(setView(), container);

            swipeRefreshLayout.setOnRefreshListener(() -> {
                onRefreshData();
                onRefreshFinish();
            });
        } else {
            setContentView(setView());
        }
        // 对 title bar 进行处理。
        titleBarShowStatus = setTitleBarShowStatus();
        if (titleBarShowStatus) {
            initTitleBar();
        }
    }

    /**
     * 是否允许 SwipeRefreshLayout 进行 refresh.
     */
    protected void setEnableRefresh(boolean enable) {
        swipeRefreshLayout.setEnabled(enable);
    }

    /**
     * SwipeRefreshLayout 的刷新操作。
     * 子类可以重写这个方法实现数据的刷新。
     */
    protected void onRefreshData() {
    }

    /**
     * 结束刷新时调用，否则会一直转菊花。
     */
    protected void onRefreshFinish() {
        swipeRefreshLayout.setRefreshing(false);
    }

    private void initTitleBar() {
        try {
            initTitleBarView();
            tvBack.setOnClickListener(this);
            tvTitle.setOnClickListener(this);
            ivMenu01.setOnClickListener(this);
            ivMenu02.setOnClickListener(this);
            tvMenu.setOnClickListener(this);
        } catch (Exception e) {
            titleBarShowStatus = false;
            titleBarExist = false;
            Logger.xe("No Title bar in Layout file.");
        }
    }

    private void initTitleBarView() {
        rlTitleBar = find(R.id.rl_title_bar);
        llMenuContainer = find(R.id.ll_title_bar_menu_container);
        tvBack = find(R.id.tv_title_bar_back);
        tvTitle = find(R.id.tv_title_bar_title);
        ivMenu01 = find(R.id.iv_title_bar_menu_image01);
        ivMenu02 = find(R.id.iv_title_bar_menu_image02);
        tvMenu = find(R.id.tv_title_bar_menu_text);
    }


    @Override
    protected void doBusiness() {
        initView();//初始化
        initClicks();//监听
        initData();//实例化
    }

    // 3 个方法， 基本业务流程。
    protected void initView() {
    }

    protected void initClicks() {
    }

    protected void initData() {
    }

    /**
     * 设置 Activity 的布局文件。
     * 如果继承 BaseToolActivity，子类必须提供布局。
     *
     * @return Resource Id
     */
    protected abstract int setView();

    /**
     * 是否将 view 放置一个 SwipeRefreshLayout 中。
     * 默认是不放入的，所以需要自己管理滚动效果等。
     */
    protected boolean setViewInRefresh() {
        return false;
    }

    /**
     * Toobar 点击相关
     *
     * @param v View
     */
    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_title_bar_back:
                clickBack();
                break;
            case R.id.tv_title_bar_title:
                clickTitle();
                break;
            case R.id.iv_title_bar_menu_image01:
                clickImage01();
                break;
            case R.id.iv_title_bar_menu_image02:
                clickImage02();
                break;
            case R.id.tv_title_bar_menu_text:
                clickMenuText();
            default:
                break;
        }
    }

    protected void clickBack() {
        hideSoftInput(tvBack);
        finish();
    }

    protected void clickTitle() {
    }

    protected void clickImage01() {
    }

    protected void clickImage02() {
    }

    protected void clickMenuText() {
    }

    /**
     * @return View TitleBar, 方便子类对其进行一些特殊设置。
     */
    protected View getTitleBar() {
        return rlTitleBar;
    }

    /**
     * TitleBar 是否展示。
     * 如果子类 Activity 不需要显示 TitleBar，需要重写该方法，并返回 false。
     */
    protected boolean setTitleBarShowStatus() {
        return true;
    }

    /**
     * 设置 TitleBar 为隐藏.
     */
    protected void setTitleBarGone() {
        rlTitleBar.setVisibility(View.GONE);
    }

    /**
     * 设置 TitleBar 显示.
     */
    protected void setTitleBarVisible() {
        rlTitleBar.setVisibility(View.VISIBLE);
    }

    /**
     * 设置 TitleBar 的背景色。
     */
    protected void setTitleBarColor(@ColorRes int color) {
        if (titleBarShowStatus) {
            rlTitleBar.setBackgroundColor(getResources().getColor(color));
        }
    }

    /**
     * 设置 TitleBar 的字体颜色
     */
    protected void setTitleBarTextColor(@ColorRes int color) {
        if (titleBarShowStatus) {
            tvTitle.setTextColor(getResources().getColor(color));
        }
    }

    /**
     * 给 title 添加 drawable right icon.
     */
    protected void setDrawableRightOfTitle(@DrawableRes int resId) {
        if (titleBarShowStatus) {
            Drawable drawable = ContextCompat.getDrawable(this, resId);
            tvTitle.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, drawable, null);
            tvTitle.setCompoundDrawablePadding(Utils.dip2px(this, 8)); // 和文字的间距 8dp
        }
    }

    /**
     * 设置是否显示 back button。
     */
    protected void setBackShow(boolean show) {
        if (titleBarShowStatus) {
            if (show) {
                tvBack.setVisibility(View.VISIBLE);
            } else {
                tvBack.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置返回的图标
     */
    protected void setBackImg(@DrawableRes int resId) {
        Drawable nav_up = getResources().getDrawable(resId);
        nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
        if (titleBarShowStatus) {
            tvBack.setCompoundDrawables(nav_up, null, null, null);
        }
    }

    /**
     * 设置显示 TitleBar 中的 menu。
     */
    protected void setMenuShow(boolean show) {
        if (titleBarShowStatus) {
            if (show) {
                llMenuContainer.setVisibility(View.VISIBLE);
            } else {
                llMenuContainer.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置是否显示 Menu 中的第一个 ImageView。
     */
    protected void setMenuImage01Show(boolean show) {
        if (titleBarShowStatus) {
            if (show) {
                llMenuContainer.setVisibility(View.VISIBLE);
                ivMenu01.setVisibility(View.VISIBLE);
            } else {
                ivMenu01.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置是否显示 Menu 中的第二个 ImageView。
     */
    protected void setMenuImage02Show(boolean show) {
        if (titleBarShowStatus) {
            if (show) {
                llMenuContainer.setVisibility(View.VISIBLE);
                ivMenu02.setVisibility(View.VISIBLE);
            } else {
                ivMenu02.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置是否显示 Menu 中的 TextView。
     */
    protected void setMenuTextShow(boolean show) {
        if (titleBarShowStatus) {
            if (show) {
                llMenuContainer.setVisibility(View.VISIBLE);
                tvMenu.setVisibility(View.VISIBLE);
            } else {
                tvMenu.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 设置 Menu 中第一个 ImageView 显示的图片内容。
     *
     * @param resId DrawableRes Id.
     */
    protected void setMenuImage01(@DrawableRes int resId) {
        if (titleBarShowStatus) {
            ivMenu01.setImageResource(resId);
        }
    }

    /**
     * 设置 Menu 中第二个 ImageView 显示的图片内容。
     *
     * @param resId DrawableRes Id.
     */
    protected void setMenuImage02(@DrawableRes int resId) {
        if (titleBarShowStatus) {
            ivMenu02.setImageResource(resId);
        }
    }

    /**
     * 设置 Menu 中 TextView 显示的文字内容。
     *
     * @param o String or StringRes Id.
     */
    protected void setMenuText(@NonNull Object o) {
        if (titleBarShowStatus) {

            if (o instanceof String) {
                tvMenu.setText((String) o);
            } else if (o instanceof Integer) {
                tvMenu.setText((int) o);
            }
        }
    }

    /**
     * 设置 title 的文字。
     */
    protected void setTitleText(@NonNull Object o) {
        if (titleBarShowStatus) {

            if (o instanceof String) {
                tvTitle.setText((String) o);
            } else if (o instanceof Integer) {
                tvTitle.setText((int) o);
            }
        }
    }

    /**
     * 封装跳转相关
     */
    protected void quickStartActivity(@NonNull Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void quickStartActivity(@NonNull Intent intent) {
        startActivity(intent);
    }

    protected void quickStartActivity(@NonNull Class clazz, @Nullable Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    protected void quickStartActivityResult(@NonNull Class clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 弹出提示相关
     *
     * @param msg
     */
    protected void showToastLong(String msg) {
        // 使用 ToastUtils 来处理。
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    protected void showToastShort(String msg) {
        // 使用 ToastUtils 来处理。
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    // 隐藏键盘
    protected void hideSoftInput(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) return;
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }


    /**
     * 点击监听
     * 根据view
     */
    public Observable<Object> rxClickById(View view) {
        return RxView.clicks(view)
                .throttleFirst(300, TimeUnit.MILLISECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY));
    }

    /**
     * 点击监听
     * 根据资源ID
     */
    public Observable<Object> rxClickById(@IdRes int id) {
        return RxView.clicks(find(id))
                .throttleFirst(3000, TimeUnit.MILLISECONDS)
                .compose(this.bindUntilEvent(ActivityEvent.DESTROY));
    }


    /**
     * 控件绑定
     */
    public <T extends View> T find(@IdRes int viewId) {
        return (T) this.findViewById(viewId);
    }

    /**
     * 获取输入框的文本
     */
    public String getEditTextString(EditText e) {
        String str;
        str = (e == null ? "" : e.getText().toString().trim());
        return str;
    }

    /**
     * 判断集合对象为空
     */
    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 暂未用到
     */
    protected void showProcessingDialog() {

    }

    protected void showWaitingDialog() {

    }

    protected void showConfirmDialog() {

    }

    protected void dissDialog() {

    }
}
