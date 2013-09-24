package com.borqs.market.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.borqs.market.R;
import com.borqs.market.json.Product;
import com.borqs.market.utils.DownloadUtils;


public class ProductDescriptionFragment extends Fragment {
    public static final String TAG = ProductDescriptionFragment.class.getSimpleName();
    private Product mData;
    public interface ClickListener {
        void onclick();
    }

    public ProductDescriptionFragment() {
        super();
    }
    public ProductDescriptionFragment(Product data) {
        super();
        this.mData = data;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_description, null);
        if (mData != null) {

            ((TextView)view.findViewById(R.id.tv_title)).setText(TextUtils.isEmpty(mData.name) == true ? 
                                                   getActivity().getString(R.string.none) : mData.name);
            ((TextView)view.findViewById(R.id.tv_size)).setText(DownloadUtils.getAppSize(mData.size));
            ((TextView)view.findViewById(R.id.tv_version)).setText(TextUtils.isEmpty(mData.version_name) == true ? 
                                                   getActivity().getString(R.string.none) : mData.version_name);
            ((TextView)view.findViewById(R.id.tv_author)).setText(TextUtils.isEmpty(mData.author) == true ? 
                                                   getActivity().getString(R.string.none) : mData.author);
            ((TextView)view.findViewById(R.id.tv_desc)).setText(TextUtils.isEmpty(mData.description) == true ?  
                    getActivity().getString(R.string.none) : mData.description);
            SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd"); 
            fmt.format(new Date(mData.updated_time));
            ((TextView)view.findViewById(R.id.tv_update_time)).setText(fmt.format(new Date(mData.updated_time)));
            
            ((TextView)view.findViewById(R.id.tv_title)).setTextSize(17);
            ((TextView)view.findViewById(R.id.tv_size)).setTextSize(17);
            ((TextView)view.findViewById(R.id.tv_version)).setTextSize(17);
            ((TextView)view.findViewById(R.id.tv_author)).setTextSize(17);
            ((TextView)view.findViewById(R.id.tv_desc)).setTextSize(17);
            ((TextView)view.findViewById(R.id.tv_update_time)).setTextSize(17);
            
            ((TextView)view.findViewById(R.id.label_title)).setTextSize(17);
            ((TextView)view.findViewById(R.id.label_size)).setTextSize(17);
            ((TextView)view.findViewById(R.id.label_version)).setTextSize(17);
            ((TextView)view.findViewById(R.id.label_author)).setTextSize(17);
            ((TextView)view.findViewById(R.id.label_desc)).setTextSize(17);
            ((TextView)view.findViewById(R.id.label_update_time)).setTextSize(17);
        }
        return view;
    }

}
