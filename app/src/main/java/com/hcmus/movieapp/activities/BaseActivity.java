package com.hcmus.movieapp.activities;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;

import com.hcmus.movieapp.R;
import com.hcmus.movieapp.adapters.ViewPagerHomeAdapter;
import com.hcmus.movieapp.models.Account;
import com.hcmus.movieapp.utils.AppManager;

import java.util.Map;


public class BaseActivity extends AppCompatActivity {

    private static final int MULTIPLE_REQUEST_CODE = 121;
    protected Account account;

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

    public void showFragmentWithCustomAnimation(Fragment fragment, int containerLayoutId, int firstAnim, int secondAnim) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(firstAnim, secondAnim);
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

    public void hideFragmentWithCustomAnimation(Fragment fragment, int firstAnim, int secondAnim) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(firstAnim, secondAnim);
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
        builder.setTitle(Html.fromHtml("<font color='#323232'>"  + title +"</font>"));
        builder.setMessage(content);
        builder.setPositiveButton("OK", (dialog, which) -> {

        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void showDialogErrorWithOKButtonListener(Context context, String title, String content, DialogInterface.OnClickListener onClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(Html.fromHtml("<font color='#323232'>"  + title +"</font>"));
        builder.setMessage(content);
        builder.setPositiveButton("OK", onClickListener);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.colorPrimary));
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
