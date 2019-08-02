package com.huatec.edu.mobileshop.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huatec.edu.mobileshop.R;
import com.huatec.edu.mobileshop.entity.AddressEntity;
import com.huatec.edu.mobileshop.entity.City;
import com.huatec.edu.mobileshop.entity.Province;
import com.huatec.edu.mobileshop.entity.Region;
import com.huatec.edu.mobileshop.http.presenter.AddressPresenter;
import com.huatec.edu.mobileshop.utils.ParserTool;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

public class AddressModifyActivity extends BaseActivity {
    private static final String TAG ="AddressModifyActivity" ;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.btn_saveAddress)
    Button btnSaveAddress;
    @BindView(R.id.et_receiver)
    EditText etReceiver;
    @BindView(R.id.et_phoneNumber)
    EditText etPhoneNumber;
    @BindView(R.id.spinner_province)
    AppCompatSpinner spinnerProvince;
    @BindView(R.id.spinner_city)
    AppCompatSpinner spinnerCity;
    @BindView(R.id.spinner_region)
    AppCompatSpinner spinnerRegion;
    @BindView(R.id.et_detailAddress)
    EditText etDetailAddress;
    private ArrayAdapter<Province> prov_adapter;
    private ArrayAdapter<City> city_adapter;
    private ArrayAdapter<Region> region_adapter;

    private List<Province> provs;
    private int prov_position;
    private int city_position;

    private Map<String, Object> params = new HashMap<>();//保存上传的参数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_modify);
        ButterKnife.bind(this);
        initData();

    }

    private void initData() {
        //数据解析
        InputStream in = getResources().openRawResource(R.raw.pcr);
        try {
            provs = ParserTool.parserProvince(in);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        prov_adapter = new ArrayAdapter<Province>(this, android.R.layout.simple_spinner_item, provs);
        //适配数据
        spinnerProvince.setAdapter(prov_adapter);
        spinnerProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                prov_position = position;
                //设置市的适配器
                city_adapter = new ArrayAdapter<City>(AddressModifyActivity.this, android.R.layout
                        .simple_spinner_item, provs.get(position).getCitys());
                spinnerCity.setAdapter(city_adapter);

                String provice = provs.get(position).getName();
                params.put("provice",provice);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                //设置地区的适配器
                region_adapter = new ArrayAdapter<Region>(AddressModifyActivity.this, android.R.layout
                        .simple_spinner_item, provs.get(prov_position).getCitys().get(position).getDiQus());
                spinnerRegion.setAdapter(region_adapter);

                String city = provs.get(prov_position).getCitys().get(position).getName();
                params.put("city",city);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String region = provs.get(prov_position).getCitys().get(city_position).getDiQus().get(position)
                        .getName();
                params.put("region",region);
                String receiver = etReceiver.getText().toString().trim();
                String phoneNumber = etPhoneNumber.getText().toString().trim();
                String detailAddress = etDetailAddress.getText().toString().trim();
                params.put("receiver",receiver);
                params.put("phoneNumber",phoneNumber);
                params.put("detailAddress",detailAddress);
                Log.e(TAG,"params:"+params);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @OnClick({R.id.title_back, R.id.btn_saveAddress})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.title_back:
                break;
            case R.id.btn_saveAddress:
                AddressPresenter.add(new Subscriber<AddressEntity>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AddressEntity addressEntity) {
                        Toast.makeText(AddressModifyActivity.this,"保存成功",Toast.LENGTH_LONG).show();
                    }
                },params);
                break;
        }
    }
}
