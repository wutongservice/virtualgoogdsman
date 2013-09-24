package com.borqs.market.json;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.borqs.market.utils.QiupuConfig;

import android.text.TextUtils;

public class ProductJSONImpl extends Product {

    /**
	 * 
	 */
    private static final long serialVersionUID = -8612461811920245848L;

    public ProductJSONImpl(JSONObject obj) {
        if (obj == null)
            return;
        product_id = obj.optString("id");
        name = obj.optString("name");
        type = obj.optString("category_id");
        cover_url = obj.optString("cover_image");
        logo_url = obj.optString("logo_image");
        isFree = obj.optInt("paid",1)==1?true:false;   //1为免费，2为收费
        if(!isFree) {
            if(obj.has("price")) {
                JSONObject priceObj =  obj.optJSONObject("price");
                price = priceObj.optString("display");
                priceObj = null;
            }
        }
        purchased = obj.optBoolean("purchased");
        author = obj.optString("author_name");
        author_email = obj.optString("author_email");
        description = obj.optString("description");
        created_time = obj.optLong("created_at");
        updated_time = obj.optLong("updated_at");
        promotion_image = obj.optString("promotion_image");
        size = obj.optLong("file_size");
        app_package = obj.optString("app_package");
        mGoogleSku = obj.optString("google_iab_sku");
        mm_paycode = obj.optString("cmcc_mm_paycode");
        mm_paycode_count = obj.optInt("cmcc_mm_amount",1);
        version_name = obj.optString("version_name");
        version_code = obj.optInt("version");
        downloads_count = obj.optInt("purchase_count");

        screenshots = new ArrayList<String>();
        String screenshot1 =  obj.optString("screenshot1_image");
        String screenshot2 =  obj.optString("screenshot2_image");
        String screenshot3 =  obj.optString("screenshot3_image");
        String screenshot4 =  obj.optString("screenshot4_image");
        String screenshot5 =  obj.optString("screenshot5_image");
        if(!TextUtils.isEmpty(screenshot1)) {
            screenshots.add(screenshot1);
        }
        if(!TextUtils.isEmpty(screenshot2)) {
            screenshots.add(screenshot2);
        }
        if(!TextUtils.isEmpty(screenshot3)) {
            screenshots.add(screenshot3);
        }
        if(!TextUtils.isEmpty(screenshot4)) {
            screenshots.add(screenshot4);
        }
        if(!TextUtils.isEmpty(screenshot5)) {
            screenshots.add(screenshot5);
        }
        
    }

    public static ArrayList<Product> createProductList(JSONArray array) {
        ArrayList<Product> list = new ArrayList<Product>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj;
                obj = array.getJSONObject(i);
                list.add(new ProductJSONImpl(obj));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Product createProductInfo(String response) {
        Product c = new Product();
        JSONArray array = null;
        try {
            array = new JSONArray(response);
            if (array.length() >= 1) {

                JSONObject obj;
                obj = array.getJSONObject(0);
                c = new ProductJSONImpl(obj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return c;
    }
}
