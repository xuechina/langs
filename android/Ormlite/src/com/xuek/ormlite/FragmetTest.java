package com.xuek.ormlite;


import android.annotation.SuppressLint;
import android.app.Fragment;
import com.j256.ormlite.android.apptools.OpenHelperManager;



@SuppressLint("NewApi") public class FragmetTest extends Fragment {

    private DBHelper databaseHelper = null;

    protected DBHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper =
                OpenHelperManager.getHelper(getActivity(), DBHelper.class);
        }
        return databaseHelper;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
