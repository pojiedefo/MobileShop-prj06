package com.huatec.edu.mobileshop.activity;

import android.os.Bundle;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.fragment.CartFragment;

public class CartActivity extends BaseActivity {
    private CartFragment cartFragment;

    public CartActivity() {
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        if (cartFragment == null) {
            cartFragment = new CartFragment();
        }
        getFragmentManager().beginTransaction()
                .add(R.id.main_frame, cartFragment)
                .addToBackStack(null)
                .commit();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
