package com.javierc.timetracker.API;

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
            return HTTP.string() + "ttcheckin.eu01.aws.af.cm";
        }
    },
    LOGIN_URL() {
        @Override
        public String string() {
            return HOST.string() + "/api/login";
        }
    }
    ;

//    private String s = "";

//    com.javierc.timetracker.API(String string){
//        s = string;
//    }

    API(){}


    public abstract String string();
}
