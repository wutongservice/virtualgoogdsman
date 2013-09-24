package com.borqs.market;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.borqs.market.fragment.ProductGridFragment;
import com.borqs.market.utils.BLog;
import com.borqs.market.utils.MarketUtils;

public class ProductListActivity extends BasicActivity {
    private final String TAG = "ProductListActivity";

    private FragmentManager mFragmentManager;
    private ProductGridFragment productFragment;
    private final String TAG_FRAGMENT = "TAG_FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BLog.d(TAG, "onCreate(savedInstanceState)");
        setContentView(R.layout.product_list_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        parseIntent();
        if(MarketUtils.CATEGORY_THEME.equals(categoryStr)) {
            getActionBar().setTitle(R.string.top_navigation_theme);
        }else if(MarketUtils.CATEGORY_OBJECT.equals(categoryStr)){
            getActionBar().setTitle(R.string.top_navigation_object);
        }

        productFragment = new ProductGridFragment(categoryStr, app_version,
                package_name);
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.fragment_container, productFragment, TAG_FRAGMENT)
                .commit();

    }

    private int app_version = 0;
    private String package_name;
    private String categoryStr;

    private void parseIntent() {
        app_version = getIntent().getIntExtra(MarketUtils.EXTRA_APP_VERSION, 0);
        package_name = getIntent().getStringExtra(
                MarketUtils.EXTRA_PACKAGE_NAME);
        if (TextUtils.isEmpty(package_name)) {
            throw new IllegalArgumentException("package name is null");
        }
        categoryStr = getIntent().getStringExtra(MarketUtils.EXTRA_CATEGORY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        BLog.d(TAG, "onSaveInstanceState(outState)");
        mFragmentManager.putFragment(outState, TAG_FRAGMENT, productFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        BLog.d(TAG, "onRestoreInstanceState(outState)");
        if (savedInstanceState != null) {
            mFragmentManager.getFragment(savedInstanceState, TAG_FRAGMENT);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu optionMenu) {
        getMenuInflater().inflate(R.menu.basic_menu, optionMenu);

        return super.onCreateOptionsMenu(optionMenu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            finish();
        } else if (itemId == R.id.menu_refresh) {
            if (productFragment != null) {
                productFragment.onRefresh();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public interface ActionListener {
        void onrefresh();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFragmentManager.beginTransaction().remove(productFragment);
        productFragment = null;
    }
}
