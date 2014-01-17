package com.javierc.timetracker.API;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by javierAle on 1/5/14.
 */
public enum API {

    HTTP() {
        @Override
        public String string() {
            return "http://";
        }
    },
    HOST() {
        @Override
        public String string() {
            return HTTP.string() + "timetracker.aws.af.cm";
        }
    },
    LOGIN_URL() {
        @Override
        public String string() {
            return HOST.string() + "/api/login";
        }
    },
    CHECKIN_URL(){
        @Override
        public String string() {
            return HOST.string() + "/api/log";
        }
    },
    CHECKIN_STATUS_URL(){
        @Override
        public String string() {
            return CHECKIN_URL.string() + "/status";
        }
    },
    GET_SPEC_WEEK_URL(){
        int week = 1;
        public String string(int week) {
            return CHECKIN_URL.string() + "/"+week;
        }

        @Override
        public String string() {
            return CHECKIN_URL.string() + "/"+ new SimpleDateFormat("w").format(new Date());
        }
    },
    STATUS_OK(200){
        @Override
        public String string() {
            return String.valueOf(value());
        }
    },
    STATUS_AUTH_FAIL(401){
        @Override
        public String string() {
            return String.valueOf(value());
        }
    }
    ;

//    private String s = "";

//    com.javierc.timetracker.API(String string){
//        s = string;
//    }
    private int val = 0;
    API(){}
    API(int s){ val = s; };

    public int value(){
        return val;
    };

    public abstract String string();
}
