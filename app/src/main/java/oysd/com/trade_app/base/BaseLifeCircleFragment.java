package oysd.com.trade_app.base;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import oysd.com.trade_app.util.Logger;

public class BaseLifeCircleFragment extends Fragment {

    public static final String TAG = "BaseLifeCircleFragment";


    @Override

    public void onAttach(Context context) {

        super.onAttach(context);

        //LogUtil.i(TAG,getClass().getSimpleName() + "  onAttach");

    }

    public void onClick(View v) {
    }

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

//        LogUtils.i(TAG, getClass().getSimpleName() + "  onCreate getParentFragment  " + (getParentFragment() == null));

//        LogUtils.i(TAG,getClass().getSimpleName() + "  onCreate");
    }


    @Nullable

    @Override

    public View onCreateView(@NonNull LayoutInflater inflater,

                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.i(TAG, getClass().getSimpleName() + "  onCreateView");

//        LogUtils.i(TAG, getClass().getSimpleName() + "  onCreateView getParentFragment  " + (getParentFragment() == null));

        return super.onCreateView(inflater, container, savedInstanceState);

    }


    @Override

    public void onAttachFragment(Fragment childFragment) {

        super.onAttachFragment(childFragment);

//        LogUtils.i(TAG,getClass().getSimpleName() + "  onAttachFragment");

//        LogUtils.i(TAG, getClass().getSimpleName() + "  onAttachFragment getParentFragment  " + (getParentFragment() == null));

    }


    @Override

    public void setUserVisibleHint(boolean isVisibleToUser) {

        super.setUserVisibleHint(isVisibleToUser);

        Logger.i(TAG, getClass().getSimpleName() + "  setUserVisibleHint " + isVisibleToUser);

//        LogUtils.i(TAG,getClass().getSimpleName() + "  isResumed() " + isResumed());

//        LogUtils.i(TAG,getClass().getSimpleName() + "  isAdded() " + isAdded());

//        LogUtils.i(TAG, getClass().getSimpleName() + "  setUserVisibleHint getParentFragment != null  " + (getParentFragment() != null));

    }


    @Override

    public void onHiddenChanged(boolean hidden) {

        super.onHiddenChanged(hidden);

        Logger.i(TAG, getClass().getSimpleName() + "  onHiddenChanged " + hidden);

    }


    @Override

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        Logger.i(TAG, getClass().getSimpleName() + "  onActivityCreated ");

//        LogUtils.i(TAG, getClass().getSimpleName() + "  onActivityCreated getParentFragment != null  " + (getParentFragment() != null));

    }


    @Override

    public void onStart() {

        super.onStart();

//        LogUtils.i(TAG,getClass().getSimpleName() + "  onStart ");

    }


    @Override

    public void onResume() {

        super.onResume();

        Logger.i(TAG, getClass().getSimpleName() + " onResume  ");

//        LogUtils.i(TAG,getClass().getSimpleName() + "   fragment.getUserVisibleHint() = "  + getUserVisibleHint());

//        LogUtils.i(TAG, getClass().getSimpleName() + "  onResume getParentFragment != null  " + (getParentFragment() != null));


    }


    @Override

    public void onPause() {

        super.onPause();

        Logger.i(TAG, getClass().getSimpleName() + "  onPause ");

//        LogUtils.i(TAG,getClass().getSimpleName() + "   fragment.getUserVisibleHint() = "  + getUserVisibleHint());

//        LogUtils.i(TAG, getClass().getSimpleName() + "  onPause getParentFragment != null  " + (getParentFragment() != null));


    }


    @Override

    public void onStop() {

        super.onStop();

        Logger.i(TAG, getClass().getSimpleName() + "  onStop ");

    }


    @Override

    public void onDestroyView() {

        super.onDestroyView();

        Logger.i(TAG, getClass().getSimpleName() + "  onDestroyView ");

    }


    @Override

    public void onDestroy() {

        super.onDestroy();

        Logger.i(TAG, getClass().getSimpleName() + "  onDestroy ");

    }


    @Override

    public void onDetach() {

        super.onDetach();

        Logger.i(TAG, getClass().getSimpleName() + "  onDetach ");

    }
}
