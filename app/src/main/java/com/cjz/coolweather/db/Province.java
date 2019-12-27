package com.cjz.coolweather.db;

import org.litepal.crud.DataSupport;

public class Province extends DataSupport {

    //定义 Bean，继承 DataSupport
    //id字段可写可不写，默认自动产生
    private int id;

    private String provinceName;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
