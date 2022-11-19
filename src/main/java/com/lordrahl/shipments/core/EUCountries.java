package com.lordrahl.shipments.core;

import java.util.*;

public class EUCountries {
    private static String[] eu = {
            "AL",
            "AD",
            "AT",
            "AZ",
            "BY",
            "BE",
            "BA",
            "BG",
            "HR",
            "CY",
            "CZ",
            "DK",
            "EE",
            "FI",
            "FR",
            "GE",
            "DE",
            "GR",
            "HU",
            "IS",
            "IE",
            "IT",
            "KZ",
            "XK",
            "LV",
            "LI",
            "LT",
            "LU",
            "MK",
            "MT",
            "MD",
            "MC",
            "ME",
            "NL",
            "NO",
            "PL",
            "PT",
            "RO",
            "RU",
            "SM",
            "RS",
            "SK",
            "SI",
            "ES",
            "SE",
            "CH",
            "TR",
            "UA",
            "GB",
            "VA",
    };

    private static Set<String> countries = new HashSet<>(Arrays.asList(eu));

    public static boolean member(String country) {
        country = country.toUpperCase();
        return countries.contains(country);
    }
}
