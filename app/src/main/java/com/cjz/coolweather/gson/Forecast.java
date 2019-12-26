package com.cjz.coolweather.gson;

public class Forecast {
    /**
     * date : 2019-12-27
     * cond : {"txt_d":"多云"}
     * tmp : {"max":"20","min":"11"}
     */

    private String date;
    private CondBean cond;
    private TmpBean tmp;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CondBean getCond() {
        return cond;
    }

    public void setCond(CondBean cond) {
        this.cond = cond;
    }

    public TmpBean getTmp() {
        return tmp;
    }

    public void setTmp(TmpBean tmp) {
        this.tmp = tmp;
    }

    public static class CondBean {
        /**
         * txt_d : 多云
         */

        private String txt_d;

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }
    }

    public static class TmpBean {
        /**
         * max : 20
         * min : 11
         */

        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public void setMax(String max) {
            this.max = max;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }
    }



    /*public String date;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    public class Temperature {

        public String max;

        public String min;

    }

    public class More {

        @SerializedName("txt_d")
        public String info;

    }*/

}
