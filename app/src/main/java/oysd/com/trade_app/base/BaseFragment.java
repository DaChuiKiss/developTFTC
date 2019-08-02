package oysd.com.trade_app.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import oysd.com.trade_app.util.Logger;
import oysd.com.trade_app.util.Utils;

/**
 * @author houmingkuan
 * @time 2016/4/3
 * @desc Base fragment for all fragments.
 */
public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    // 为 sub fragment 提供方便 context.
    protected Context context;
    // fragment 布局 view
    protected View rootView;
    // args passed when fragment being instantiating.
    protected Bundle bundle;

    protected Unbinder unbinder;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.d(" ---> onAttach");

        if (context != null) {
            this.context = context;
        } else {
            this.context = getActivity();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(" ---> onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Logger.d(" ---> onCreateView");

        if (rootView == null) {
            rootView = inflater.inflate(getLayoutRes(), container, false);
        }

        bundle = getArguments();
        unbinder = ButterKnife.bind(this, rootView);

        initView(rootView);
        initClicks();
        initData();

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.d(" ---> onActivityCreated");

        if (getActivity() == null) return;
        getActivity().getWindow()
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Logger.d(" ---> setUserVisibleHint");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Logger.d(" ---> onHiddenChanged  + hidden: " + hidden);
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.d(" ---> onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.d(" ---> onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.d(" ---> onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.d(" ---> onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.d(" ---> onDestroyView");

        unbinder.unbind();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.d(" ---> onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.d(" ---> onDetach");
    }

    /**
     * 继承的 Fragment 必须实现，提供布局。
     */
    @LayoutRes
    protected abstract int getLayoutRes();

    /**
     * 页面内容初始化。
     *
     * @param rootView View
     */
    protected abstract void initView(View rootView);

    /**
     * 点击事件的初始化。
     */
    protected void initClicks() {
    }

    /**
     * 初始化数据。
     */
    protected void initData() {
    }

    @Override
    public void onClick(View v) {
    }



}
