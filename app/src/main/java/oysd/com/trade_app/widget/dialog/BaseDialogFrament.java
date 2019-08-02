package oysd.com.trade_app.widget.dialog;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Base DialogFragment.
 * Created by Liam on 2018/8/9
 */
public abstract class BaseDialogFrament extends DialogFragment {

    public static final String TAG_DIALOG = "dialog";

    protected int layoutId;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Build dialog.
        if (layoutId == 0) {
            throw new IllegalArgumentException("must set layoutId of dialog");
        }

        View view = LayoutInflater.from(getActivity()).inflate(layoutId, null);
        return setDialog(view);
    }

    /**
     * 子类需要实现，对 dialog 进行设置。
     *
     * @param view View
     * @return Dialog
     */
    protected abstract Dialog setDialog(View view);

    /**
     * 显示 dialog.
     *
     * @param fragmentManager FragmentManager
     */
    public void showDialog(@NonNull FragmentManager fragmentManager) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        Fragment fragment = fragmentManager.findFragmentByTag(TAG_DIALOG);
        if (fragment != null) transaction.remove(fragment);

        // Adds dialogFragment in.
        show(transaction, TAG_DIALOG);
        fragmentManager.executePendingTransactions();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
