package com.lordrahl.shipments.core;

import org.springframework.stereotype.Service;

public class CoreService {
    public static Weight fromSize(double size) {
        if(size > 0.0 && size<10){
            return Weight.SMALL;
        }else if (size>=10 && size <25){
            return Weight.MEDIUM;
        } else if (size >=25 && size <50) {
            return Weight.LARGE;
        }else if (size >50 && size< 1000){
            return Weight.HUGE;
        }else{
            return Weight.UNKNOWN;
        }
    }

    public static int price(Weight weight){
        switch (weight){
            case SMALL -> {
                return 100;
            }
            case MEDIUM -> {
                return 300;
            }
            case LARGE -> {
                return 500;
            }
            case HUGE -> {
                return 2000;
            }
            default -> {
                return 0;
            }
        }
    }

    public static double multiplier(String origin, String dest) {
        if(origin.equalsIgnoreCase(dest)){
            // local shipping assumes that the origin and destination are the same.
            return 1.0;
        }
        if (EUCountries.member(origin) && EUCountries.member(dest)){
            return 1.5;
        }
        return 2.5;
    }

    public static double amount(String origin, String dest, double size){
        Weight weight = fromSize(size);
        double multiple = multiplier(origin, dest);
        int p = price(weight);

        return p * multiple;
    }
}
