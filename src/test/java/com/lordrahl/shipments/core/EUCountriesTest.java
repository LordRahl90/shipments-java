package com.lordrahl.shipments.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class EUCountriesTest {
//    private EUCountries euCountries=new EUCountries();
    @Test
    public void testNonMemberCountry() {
        String country ="ng";
        boolean result = EUCountries.member(country);
        assertThat(result).isFalse();
    }


    @Test
    public void testNonMemberCountryUpperCase() {
        String country ="NG";
        boolean result = EUCountries.member(country);
        assertThat(result).isFalse();
    }

    @Test
    public void testMemberCountry(){
        String country = "se";
        boolean result = EUCountries.member(country);
        assertThat(result).isTrue();
    }

    @Test
    public void testMemberCountryUpperCase(){
        String country = "SE";
        boolean result = EUCountries.member(country);
        assertThat(result).isTrue();
    }
}
