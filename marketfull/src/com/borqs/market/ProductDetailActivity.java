package com.borqs.market;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;

import com.borqs.market.fragment.ProductDetailFragment;
import com.borqs.market.utils.IntentUtil;

public class ProductDetailActivity extends BasicActivity {
    ProductDetailFragment detailFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.product_detail_activity);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        String product_name = getIntent().getStringExtra(IntentUtil.EXTRA_KEY_NAME);
        if(TextUtils.isEmpty(product_name)) {
            getActionBar().setTitle(R.string.themes_market);
        }else {
            getActionBar().setTitle(product_name);
        }
        detailFragment = new ProductDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, detailFragment).commit();
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
            if (detailFragment != null) {
                detailFragment.onRefresh();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}