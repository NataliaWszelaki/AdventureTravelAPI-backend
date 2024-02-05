package com.crud.adventuretravel.controller;

import com.crud.adventuretravel.domain.Customer;
import com.crud.adventuretravel.domain.CustomerDto;
import com.crud.adventuretravel.exception.CustomerNotFoundException;
import com.crud.adventuretravel.mapper.CustomerMapper;
import com.crud.adventuretravel.service.CustomerDBService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerDBService customerDBService;

    @MockBean
    private CustomerMapper customerMapper;

    private Customer customer;
    private CustomerDto customerDto;

    @BeforeEach
    void setUp() {
        customer = new Customer(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18));
        customerDto = new CustomerDto(342L, "Johnny", "Black",
                "black@test.com", 223456, LocalDate.of(2023, 2, 18));
    }
    @Test
    void shouldFetchEmptyList() throws Exception {

        //Given
        when(customerDBService.getAllCustomers()).thenReturn(List.of());

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldFetchList() throws Exception {

        //Given
        List<Customer> customerList = List.of(
                new Customer(12L, "John", "Smith", "john@test.com", 123456,
                        LocalDate.of(2021, 12, 12)),
                customer);

        List<CustomerDto> customerDtoList = List.of(
                new CustomerDto(12L, "John", "Smith", "john@test.com", 123456,
                        LocalDate.of(2021, 12, 12)),
                customerDto);

        when(customerDBService.getAllCustomers()).thenReturn(customerList);
        when(customerMapper.mapToCustomerDtoList(customerList)).thenReturn(customerDtoList);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(12)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("John")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName", Matchers.is("Smith")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("black@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].phoneNumber", Matchers.is(223456)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].accountCreationDate", Matchers.is("2023-02-18")));
    }

    @Test
    void shouldFetchById() throws Exception {

        //Given
        long customerId = 342L;

        when(customerDBService.getCustomerById(customerId)).thenReturn(customer);
        when(customerMapper.mapToCustomerDto(customer)).thenReturn(customerDto);

        //When&Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/customers/342")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(342)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("Johnny")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("Black")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", Matchers.is("black@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is(223456)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountCreationDate", Matchers.is("2023-02-18")));
    }

    @Test
    void shouldNotFetchById() throws Exception {

        //Given
        Long customerId = 223L;

        when(customerDBService.getCustomerById(customerId)).thenThrow(CustomerNotFoundException.class);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/customers/223")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$").value("Customer with given ID doesn't exist."))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldCreate() throws Exception {

        // Given
        when(customerMapper.mapToCustomer(any(CustomerDto.class))).thenReturn(customer);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(customerDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(customerDBService, times(1)).createCustomer(customer);
    }

    @Test
    void shouldUpdate() throws Exception {

        // Given
        when(customerMapper.mapToCustomer(any(CustomerDto.class))).thenReturn(customer);

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) ->
                                jsonSerializationContext.serialize(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (com.google.gson.JsonDeserializer<LocalDate>)
                        (jsonElement, type, jsonDeserializationContext) ->
                                LocalDate.parse(jsonElement.getAsJsonPrimitive().getAsString()))
                .create();
        String jsonContent = gson.toJson(customerDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().is(200));
        verify(customerDBService, times(1)).updateCustomer(customer);
    }

    @Test
    void shouldDelete() throws Exception {

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(200));

        verify(customerDBService, times(1)).deleteCustomer(1L);
    }
}