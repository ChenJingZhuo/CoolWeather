package com.cjz.coolweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cjz.coolweather.R;
import com.cjz.coolweather.db.City;
import com.cjz.coolweather.db.County;
import com.cjz.coolweather.db.Province;
import com.cjz.coolweather.util.HttpUtil;
import com.cjz.coolweather.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ChooseAreaFragment extends Fragment {

    public static final int LEVEL_PROVINCE = 0;

    public static final int LEVEL_CITY = 1;

    public static final int LEVEL_COUNTY = 2;

    private ProgressDialog progressDialog;

    private TextView titleText;

    private Button backButton;

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> dataList = new ArrayList<>();

    //省列表
    private List<Province> provinceList;

    //市列表
    private List<City> cityList;

    //县列表
    private List<County> countyList;

    //选中的省份
    private Province selectedProvince;

    //选中的城市
    private City selectedCity;

    //当前选中的级别
    private int currentLevel;

    /**
     * fragment 生命周期的利用
     *
     * 生命周期有 onAttach()，onCreate()，onCreateView()，onActivityCreated()；此为创建时会执行的方法
     *
     * onStart()，onResume()，是变得可见时会执行的方法
     *
     * onPause()，onStop()，是进入后台会执行的方法。
     *
     * 销毁时会执行 onPause()，onStop()，onDestroyView()，OnDestroy()。
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.choose_area,container,false);
        titleText = view.findViewById(R.id.title_text);
        backButton = view.findViewById(R.id.back_button);
        listView = view.findViewById(R.id.list_view);
        //三个参数 (使用android自带的 android.R.layout.simple_list_item_1)
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        return view;
    }

    //与该碎片相关联的活动被创建好后执行
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //当点击列表视图项目时执行
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == LEVEL_PROVINCE){//当点击省份时执行
                    selectedProvince = provinceList.get(position);//获取点击的省份的id
                    queryCities();//查询该省份的城市列表
                } else if (currentLevel == LEVEL_CITY){//当点击具体省份的城市时执行
                    selectedCity = cityList.get(position);//获取点击的城市的id
                    queryCounties();//查询该城市的县城列表
                }else if (currentLevel==LEVEL_COUNTY){//当点击具体城市的县城时执行
                    String weatherId = countyList.get(position).getWeatherId();//获取点击的县城的天气id
                    //instanceof 严格来说是Java中的一个双目运算符，用来测试一个对象是否为一个类的实例，用法为：
                    //boolean result = obj instanceof Class
                    if (getActivity() instanceof MainActivity) {//判断当前活动类是否为MainActivity类的实例
                        Intent intent = new Intent(getActivity(), WeatherActivity.class);//显示意图跳转
                        intent.putExtra("weather_id", weatherId);//携带天气id数据
                        startActivity(intent);//开始跳转
                    }
                }
            }
        });

        //点击标题栏的返回按钮时执行
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentLevel == LEVEL_COUNTY){//当前视图位于县城列表视图时执行
                    queryCities();
                } else if (currentLevel == LEVEL_CITY){//当前视图位于城市列表视图时执行
                    queryProvinces();
                }
            }
        });

        //查询中国的省份
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces(){
        titleText.setText("中国");//将标题栏的标题设置为中国
        backButton.setVisibility(View.GONE);//隐藏返回按钮
        provinceList = DataSupport.findAll(Province.class);//查询数据库中的省份表中的所有字段的结果，返回省份集合数据
        if (provinceList.size() > 0){//如果省份表中有数据时执行
            dataList.clear();//清空数据缓存集合
            for (Province province : provinceList){//遍历省份集合数据
                dataList.add(province.getProvinceName());//添加省份实例对象的省份名称到当前列表视图数据集合
            }
            //notifyDataSetChanged方法通过一个外部的方法控制如果适配器的内容改变时需要强制调用getView来刷新每个Item的内容。
            adapter.notifyDataSetChanged();
            listView.setSelection(0);//默认选中第一个项目
            currentLevel = LEVEL_PROVINCE;//当前列表视图级为省级列表视图
        } else {//如果省份表没有数据时执行
            String address = "http://guolin.tech/api/china/";
            queryFromSever(address,"province");
        }
    }

    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCities(){
        titleText.setText(selectedProvince.getProvinceName());//将标题栏的标题设置为选中的省份
        backButton.setVisibility(View.VISIBLE);//显示返回按钮
        cityList = DataSupport.where("provinceid = ?", String.valueOf(selectedProvince.getId())).find(City.class);//查询数据库中具体省份的城市列表的所有字段结果，返回城市集合数据
        if (cityList.size() > 0){//如果城市表中有数据时执行
            dataList.clear();//清空当前的列表视图数据
            for (City city : cityList){//遍历城市集合数据
                dataList.add(city.getCityName());//
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/"+ provinceCode;
            queryFromSever(address,"city");
        }
    }

    /**
     * 查询选中市内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties(){
        titleText.setText(selectedCity.getCityName());
        backButton.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityid = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0){
            dataList.clear();
            for (County county : countyList){
                dataList.add(county.getCountyName());
            }
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCityCode();
            String address = "http://guolin.tech/api/china/"+ provinceCode+ "/"+ cityCode;
            queryFromSever(address,"county");
        }
    }

    /**
     *根据传入的地址和类型从服务器上查询省市县的数据
     */
    private void queryFromSever(String address, final String type){
        showProcessDialog();//显示“正在加载...”进度框
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProcessDialog();
                        Toast.makeText(getContext(),"加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)){
                    result = Utility.handleProvinceResponse(responseText);
                } else if("city".equals(type)){
                    result = Utility.handleCityResponse(responseText,selectedProvince.getId());
                } else if ("county".equals(type)){
                    result = Utility.handleCountyResponse(responseText,selectedCity.getId());
                }
                if (result){
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProcessDialog();
                            if ("province".equals(type)){
                                queryProvinces();
                            } else if("city".equals(type)){
                                queryCities();
                            } else if ("county".equals(type)){
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 显示进度对话框
     */
    private void showProcessDialog(){
        if (progressDialog == null){
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

    /**
     * 关闭进度对话框
     */
    private void closeProcessDialog(){
        if (progressDialog != null){
            progressDialog.dismiss();
        }
    }
}
