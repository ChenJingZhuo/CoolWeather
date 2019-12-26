package com.cjz.coolweather.gson;

public class Basic {
    /**
     * cid : CN101281001
     * location : 湛江
     * parent_city : 湛江
     * admin_area : 广东
     * cnty : 中国
     * lat : 29.70923805
     * lon : 118.31732178
     * tz : +8.00
     * city : 湛江
     * id : CN101281001
     * update : {"loc":"2019-12-26 23:10","utc":"2019-12-26 15:10"}
     */

    private String cid;
    private String location;
    private String city;
    private UpdateBean update;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public static class UpdateBean {
        /**
         * loc : 2019-12-26 23:10
         * utc : 2019-12-26 15:10
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }

    /*@SerializedName("city")
    public String cityName;
    @SerializedName("id")
    public String weatherId;
    public Update update;

    public class Update {
        @SerializedName("loc")
        public String updateTime;
    }*/


}
