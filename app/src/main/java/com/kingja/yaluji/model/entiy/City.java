package com.kingja.yaluji.model.entiy;

import java.util.List;

/**
 * Description:TODO
 * Create Time:2018/7/20 11:08
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class City {


    /**
     * cityName : 温州市
     * cityId : 330300
     * stricts : [{"id":"330303","name":"龙湾区"},{"id":"330304","name":"瓯海区"},{"id":"330382","name":"乐清市"},
     * {"id":"330324","name":"永嘉县"}]
     */

    private String cityName;
    private String cityId;
    private List<Strict> stricts;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public List<Strict> getStricts() {
        return stricts;
    }

    public void setStricts(List<Strict> stricts) {
        this.stricts = stricts;
    }

    public static class Strict {
        /**
         * id : 330303
         * name : 龙湾区
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
