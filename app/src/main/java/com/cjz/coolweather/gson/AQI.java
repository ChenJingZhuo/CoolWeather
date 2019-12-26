package com.cjz.coolweather.gson;

public class AQI {
    /**
     * city : {"aqi":"29","pm25":"7","qlty":"优"}
     */

    private CityBean city;

    public CityBean getCity() {
        return city;
    }

    public void setCity(CityBean city) {
        this.city = city;
    }

    public static class CityBean {
        /**
         * aqi : 29
         * pm25 : 7
         * qlty : 优
         */

        private String aqi;
        private String pm25;
        private String qlty;

        public String getAqi() {
            return aqi;
        }

        public void setAqi(String aqi) {
            this.aqi = aqi;
        }

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        public String getQlty() {
            return qlty;
        }

        public void setQlty(String qlty) {
            this.qlty = qlty;
        }
    }

    /*public AQICity city;

    public class AQICity {
        public String aqi;
        public String pm25;
    }*/


}
