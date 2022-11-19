package com.lordrahl.shipments.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CoreServiceTest {
    @Test
    public void testWeightFromSize() {
        double size = 10.0;
        Weight result = CoreService.fromSize(size);
        assertThat(result).isEqualTo(Weight.MEDIUM);
        size = 0.5;
        result =CoreService.fromSize(size);
        assertThat(result).isEqualTo(Weight.SMALL);
    }

    @Test
    public void testMultiplierSameCountry() {
        String origin = "ng";
        String destination="ng";
        double m = CoreService.multiplier(origin, destination);
        assertThat(m).isEqualTo(1.0);
    }

    @Test
    public void testMultiplierSameEUCountry() {
        String origin = "se";
        String destination="se";
        double m = CoreService.multiplier(origin, destination);
        assertThat(m).isEqualTo(1.0);
    }

    @Test
    public void testDifferentEUCountries() {
        String origin = "se";
        String destination="de";
        double m = CoreService.multiplier(origin, destination);
        assertThat(m).isEqualTo(1.5);
    }

    @Test
    public void testDestinationOutsideEU(){
        String origin = "se";
        String destination="ng";
        double m = CoreService.multiplier(origin, destination);
        assertThat(m).isEqualTo(2.5);
    }

    @Test
    public void testOriginOutsideEU() {
        String origin = "ng";
        String destination="se";
        double m = CoreService.multiplier(origin, destination);
        assertThat(m).isEqualTo(2.5);
    }

    @Test
    public void testPrice() {
        int price = CoreService.price(Weight.SMALL);
        assertThat(price).isEqualTo(100);

        price =  CoreService.price(Weight.MEDIUM);
        assertThat(price).isEqualTo(300);

        price = CoreService.price(Weight.LARGE);
        assertThat(price).isEqualTo(500);

        price = CoreService.price(Weight.HUGE);
        assertThat(price).isEqualTo(2000);

        price = CoreService.price(Weight.UNKNOWN);
        assertThat(price).isEqualTo(0);
    }

    @Test
    public void testAmountForLocalShipping() {
        String origin = "ng";
        String destination="ng";
        double size = 5.0;

        double amount = CoreService.amount(origin, destination,size);
        assertThat(amount).isEqualTo(100);
    }

    @Test
    public void testOverseaShipping() {
        String origin = "us";
        String destination="se";
        double size = 45.0;

        double amount = CoreService.amount(origin, destination,size);
        assertThat(amount).isEqualTo(1250);
    }
}