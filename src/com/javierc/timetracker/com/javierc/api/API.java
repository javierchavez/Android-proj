package com.javierc.timetracker.com.javierc.api;

/**
 * Created by javierAle on 1/5/14.
 */
public enum API {
    HOST() {
        @Override
        public String string() {
            return "ttcheckin.eu01.aws.af.cm";
        }
    };

//    private String s = "";

//    API(String string){
//        s = string;
//    }

    API(){}


    public abstract String string();
}
