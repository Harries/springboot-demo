package com.et.ipfilter.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public  class CountryInfo {
        private String query;
        private String status;
        private String country;
        private String countryCode;
        private String region;
        private String regionName;
        private String city;
        private String zip;
        private String lat;
        private String lon;
        private String timezone;
        private String isp;
        private String org;
        private String as;

}