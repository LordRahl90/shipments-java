package com.lordrahl.shipments.shipment;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lordrahl.shipments.core.Weight;
import com.lordrahl.shipments.requests.CustomerEnquiry;
import com.lordrahl.shipments.requests.Enquiry;
import com.lordrahl.shipments.responses.PricingResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
public class ShipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/api/v1/"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello world"));
    }


    @Test
    public void testGetPricing() throws Exception {
        ResultActions actions = mockMvc.perform(get("/api/v1/enquiry")
                        .param("origin", "us")
                        .param("destination", "se")
                        .param("weight", "45"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        String expJson=actions.andReturn().getResponse().getContentAsString();
        PricingResponse response= objectMapper.readValue(expJson, PricingResponse.class);
        System.out.println(response.getPrice());
        assertThat(response.getPrice()).isEqualTo(1250);
        assertThat(response.getCategory()).isEqualTo(Weight.LARGE.toString());
    }

    @Test
    public void testSavePricing() throws Exception {
        Enquiry enquiry = new Enquiry(45,"us","se");
        CustomerEnquiry customerEnquiry = new CustomerEnquiry();
        customerEnquiry.setName("Abiodun Alugbin");
        customerEnquiry.setEmail("tolaabbey00@gmail.com");
        customerEnquiry.setEnquiry(enquiry);
        ResultActions actions = mockMvc.perform(
                post("/api/v1/save")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(customerEnquiry))
        );

        actions.andExpect(status().isCreated());

        String expJson=actions.andReturn().getResponse().getContentAsString();
        System.out.println(expJson);
    }

    @Test
    public void testCustomerHistory() throws Exception {
        String email="tolaabbey00@gmail.com";
        ResultActions actions = mockMvc.perform(
                get("/api/v1/history?email="+email)
        );
        actions.andExpect(status().isOk());
        String expJson=actions.andReturn().getResponse().getContentAsString();
        System.out.println(expJson);
    }
}
