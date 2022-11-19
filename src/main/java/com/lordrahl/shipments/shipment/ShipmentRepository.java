package com.lordrahl.shipments.shipment;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ShipmentRepository extends CrudRepository<Shipment,Long> {
    List<Shipment> findByCustomerID(Long customerID);
}
