package com.example.movieapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.movieapp.adapters.ViewPagerHomeAdapter;
import com.example.movieapp.utils.AppManager;

import java.util.Map;


public class BaseActivity extends AppCompatActivity {

    private static final int MULTIPLE_REQUEST_CODE = 121;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.create(this);
    }

    public void showFragment(Fragment fragment, int containerLayoutId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(containerLayoutId, fragment, fragment.getClass().getSimpleName());
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    protected void hideFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
        getSupportFragmentManager().executePendingTransactions();
    }

    protected boolean removeFragment(int containerLayoutId) {
        if (getSupportFragmentManager().findFragmentById(containerLayoutId) != null) {
            getSupportFragmentManager().beginTransaction().remove(
                    getSupportFragmentManager().findFragmentById(containerLayoutId)).commit();
            return true;
        }
        return false;
    }

    public boolean isPermissionAllowed() {
        int resultReadExternalStorage = ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int resultWriteExternalStorage = ContextCompat.checkSelfPermission(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return (resultReadExternalStorage == PackageManager.PERMISSION_GRANTED
                && resultWriteExternalStorage == PackageManager.PERMISSION_GRANTED);
    }

    public void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) ||
            ActivityCompat.shouldShowRequestPermissionRationale(BaseActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // do nothing
        }

        ActivityCompat.requestPermissions(BaseActivity.this, new String[] {
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, MULTIPLE_REQUEST_CODE );
    }

    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void showDialogErrorWithOKButton(Context context, String title, String content) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(content);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    protected void setupViewPager(ViewPager viewPager, Map<Fragment, String> fragments) {
        ViewPagerHomeAdapter adapter = new ViewPagerHomeAdapter(getSupportFragmentManager());
        for (Map.Entry<Fragment, String> entry : fragments.entrySet()) {
            Fragment fragment = entry.getKey();
            String title = entry.getValue();
            adapter.addFragment(fragment, title);
        }
        viewPager.setAdapter(adapter);
    }


}
