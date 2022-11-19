package com.lordrahl.shipments.shipment;


import com.lordrahl.shipments.requests.CustomerEnquiry;
import com.lordrahl.shipments.requests.Enquiry;
import com.lordrahl.shipments.responses.PricingResponse;
import com.lordrahl.shipments.responses.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShipmentController {
    private final ShipmentService shipmentService;

    public ShipmentController(ShipmentService shipmentService) {
        this.shipmentService = shipmentService;
    }

    @GetMapping("/")
    public String hello() {
        System.out.println("Hello Whole wide world!");
        return "hello world";
    }

    @GetMapping("/enquiry")
    public ResponseEntity<PricingResponse> getPricing(
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("weight") double weight) {
        Enquiry enquiry = new Enquiry(weight, origin, destination);
        PricingResponse response = this.shipmentService.getShipmentPrice(enquiry);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveShipment(@RequestBody CustomerEnquiry enquiry) {
        Response response = this.shipmentService.saveShipmentRecord(enquiry);
        System.out.println(response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Shipment>> getHistory(@Param("email") String email) {
        var result = this.shipmentService.customerShipments(email);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
