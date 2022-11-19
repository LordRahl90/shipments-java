package com.lordrahl.shipments.shipment;

import com.lordrahl.shipments.core.CoreService;
import com.lordrahl.shipments.customers.Customer;
import com.lordrahl.shipments.customers.CustomerRepository;
import com.lordrahl.shipments.requests.CustomerEnquiry;
import com.lordrahl.shipments.requests.Enquiry;
import com.lordrahl.shipments.responses.PricingResponse;
import com.lordrahl.shipments.responses.Response;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShipmentService {
    private final CustomerRepository customerRepository;
    private final ShipmentRepository shipmentRepository;

    public ShipmentService(CustomerRepository customerRepository, ShipmentRepository shipmentRepository) {
        this.customerRepository = customerRepository;
        this.shipmentRepository = shipmentRepository;
    }

    public PricingResponse getShipmentPrice(Enquiry enquiry) {
        double price = CoreService.amount(enquiry.getOrigin(), enquiry.getDestination(), enquiry.getSize());

        return new PricingResponse(enquiry.getSize(), price, enquiry.getOrigin(), enquiry.getDestination(), CoreService.fromSize(enquiry.getSize()).toString());
    }

    public Response saveShipmentRecord(CustomerEnquiry enquiry) {
        Response response = new Response();
        Customer customer;
        try {
            // find the customer first
            Optional<Customer> customerOptional = customerRepository.findByEmail(enquiry.getEmail());
            if (customerOptional.isEmpty()) {
                // build a customer model
                customer = customerRepository.save(new Customer(enquiry.getEmail(), enquiry.getName()));
            } else {
                customer = customerOptional.get();
            }

            double price = CoreService.amount(enquiry.getEnquiry().getOrigin(), enquiry.getEnquiry().getDestination(), enquiry.getEnquiry().getSize());

            String origin = enquiry.getEnquiry().getOrigin();
            String destination = enquiry.getEnquiry().getDestination();
            double size = enquiry.getEnquiry().getSize();

            // create shipment record
            Shipment shipment = shipmentRepository.save(new Shipment(customer.getId(), origin, destination, size, price));

            response.setCustomerID(customer.getId().toString());
            response.setReference(shipment.getId().toString());
            response.setPrice(shipment.getPrice());
            response.setSuccess(true);

            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
            response.setSuccess(false);
            return response;
        }
    }

    public List<Shipment> customerShipments(String email) {
        Optional<Customer> customerOptional = this.customerRepository.findByEmail(email);
        Customer customer = customerOptional.orElseThrow();

        return this.shipmentRepository.findByCustomerID(customer.getId());
    }
}
