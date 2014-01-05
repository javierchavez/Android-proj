package com.javierc.timetracker.com.javierc.api;

/**
 * Created by javierAle on 1/5/14.
 */
public enum API {
    HOST("ttcheckin.eu01.aws.af.cm") {
        @Override
        public String toString() {
            return string;
        }
    };

    String string = "";

    API(String s){
        string = s;
    }
}
