package com.cjz.coolweather.gson;

import com.google.gson.annotations.SerializedName;

public class Now {
    /**
     * cloud : 100
     * cond_code : 101
     * cond_txt : 多云
     * fl : 21
     * hum : 67
     * pcpn : 0.0
     * pres : 1012
     * vis : 16
     * wind_deg : 6
     * wind_dir : 北风
     * wind_sc : 2
     * wind_spd : 7
     * cond : {"code":"101","txt":"多云"}
     */

    public String cond_txt;
    public String hum;
    public String wind_dir;

    /*@SerializedName("tmp")
    public String temperature;
    @SerializedName("cond")
    public More more;

    public class More {
        @SerializedName("txt")
        public String info;
    }*/


}
