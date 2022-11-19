package com.lordrahl.shipments.shipment;


import com.lordrahl.shipments.core.CoreService;
import com.lordrahl.shipments.customers.Customer;
import com.lordrahl.shipments.customers.CustomerRepository;
import com.lordrahl.shipments.requests.CustomerEnquiry;
import com.lordrahl.shipments.requests.Enquiry;
import com.lordrahl.shipments.responses.PricingResponse;
import com.lordrahl.shipments.responses.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class ShipmentServiceTest {

    @Mock
    private CustomerRepository mockCustomerRepository;

    @Mock
    private ShipmentRepository shipmentRepository;

    @Test
    public void testSaveShipmentRecord() {
        when(mockCustomerRepository.save(any(Customer.class)))
                .thenReturn(new Customer(123456L, "Abiodun Alugbin", "tolaabbey009@gmail.com"));

        doAnswer(inv -> {
            Shipment sh = inv.getArgument(0);
            double price = CoreService.amount(sh.getOrigin(), sh.getDestination(), sh.getWeight());
            return new Shipment(UUID.randomUUID(), 123456L, "us", "se", 45, price);
        }).when(shipmentRepository).save(any(Shipment.class));

        ShipmentService svc = new ShipmentService(mockCustomerRepository, shipmentRepository);
        Enquiry enquiry = new Enquiry(45.0,"us","se");

        CustomerEnquiry customerEnquiry = new CustomerEnquiry();
        customerEnquiry.setEnquiry(enquiry);
        customerEnquiry.setName("Abiodun Alugbin");
        customerEnquiry.setEmail("tolaabbey009@gmail.com");


        Response res = svc.saveShipmentRecord(customerEnquiry);

        assertThat(res.getPrice()).isEqualTo(1250);
        assertThat(res.getReference()).isNotBlank();
        assertThat(res.getCustomerID()).isNotBlank();
        assertThat(res.isSuccess()).isTrue();

        InOrder order = inOrder(mockCustomerRepository, shipmentRepository);

        order.verify(mockCustomerRepository).save(any(Customer.class));
        order.verify(shipmentRepository).save(any(Shipment.class));

        verify(mockCustomerRepository, times(1)).save(any(Customer.class));
        verify(shipmentRepository, times(1)).save(any(Shipment.class));
    }

    @Test
    public void testShipmentPrice() {
        Enquiry enquiry = new Enquiry(45.0,"us","se");

        ShipmentService svc = new ShipmentService(mockCustomerRepository,shipmentRepository);
        PricingResponse response = svc.getShipmentPrice(enquiry);

        assertThat(response.getPrice()).isEqualTo(1250);
        assertThat(response.getOrigin()).isEqualTo(enquiry.getOrigin());
        assertThat(response.getDestination()).isEqualTo(enquiry.getDestination());
        assertThat(response.getCategory()).isEqualTo("LARGE");
    }
}
