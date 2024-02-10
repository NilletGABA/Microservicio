package com.paymentchain.customer.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import com.paymentchain.customer.entities.Customer;
import com.paymentchain.customer.entities.CustomerProduct;
import com.paymentchain.customer.repository.CustomerRepository;
import java.time.Duration;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.concurrent.TimeUnit;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@RestController
@RequestMapping("/customer")
public class CustomerRestController {

    //Anotacion para inyeccion de Dependencia
    @Autowired
    //Referencia a nuestro repositorio
    CustomerRepository customerRepository;

    //WebClient
    private final WebClient.Builder webClientBuilder;

    public CustomerRestController(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    //webClient requiere la biblioteca HttpClient para funcionar correctamente     
    HttpClient client = HttpClient.create()
            //Tiempo de espera de conexión: es un período dentro del cual se debe establecer una conexión entre un cliente y un servidor.
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
            .option(ChannelOption.SO_KEEPALIVE, true)
            .option(EpollChannelOption.TCP_KEEPIDLE, 300)
            .option(EpollChannelOption.TCP_KEEPINTVL, 60)
            //Tiempo de espera de respuesta: el tiempo máximo que esperamos para recibir una respuesta después de enviar una solicitud.
            .responseTimeout(Duration.ofSeconds(1))
            //Tiempo de espera de lectura y escritura: se produce un tiempo de espera de lectura cuando no se leyeron datos dentro de un cierto
            //período de tiempo, mientras que el tiempo de espera de escritura cuando una operación de escritura no puede finalizar en un momento específico
            .doOnConnected(connection -> {
                connection.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS));
                connection.addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS));
            });

    @GetMapping("/list")
    public ResponseEntity<List<Customer>>findAll() {
        List<Customer> findAll = customerRepository.findAll();
        if(findAll==null || findAll.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.ok(findAll);
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> get(@PathVariable long id) {
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Customer input) {
        Customer find = customerRepository.findById(id).get();
        if (find != null) {
            find.setCode(input.getCode());
            find.setName(input.getName());
            find.setPhone(input.getPhone());
            find.setIban(input.getIban());
            find.setSurname(input.getSurname());
            find.setAddress(input.getAddress());
        }
        Customer save = customerRepository.save(find);
        return ResponseEntity.ok(save);
    }

    @PostMapping("/create")
    public ResponseEntity<?> post(@RequestBody Customer input) {
        input.getProducts().forEach(x -> x.setCustomer(input));
        Customer save = customerRepository.save(input);
        //Responda con un ok que es una respuesta http
        return new ResponseEntity<>(save,HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable long id) {
        Optional<Customer> findById = customerRepository.findById(id);
        if (findById.get() != null) {
            customerRepository.delete(findById.get());
        }
        return ResponseEntity.ok().build();
    }

    //devuelve un cliente por su codigo
    //Se esta haciendo la comunicacion entre dos microservicios: Customer y Transactions
    @GetMapping("/full")
    public Customer getByCode(@RequestParam String code) {
        Customer customer = customerRepository.finByCode(code);
        List<CustomerProduct> products = customer.getProducts();
        //para cada producto busca su nombre
        for (CustomerProduct x : products) {
            String productName = getProductName(x.getProductId());
            x.setProductName(productName);
        };
        //busca todas las transacciones que pertenecen a este número de cuenta
        List<?> transactions = getTransactions(customer.getIban());
        customer.setTransactions(transactions);
        return customer;
    }

    //
    private String getProductName(long id) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://businessdomain-product/product")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://businessdomain-product/product"))
                .build();
        JsonNode block = build.method(HttpMethod.GET).uri("/" + id)
                .retrieve().bodyToMono(JsonNode.class).block();
        String name = block.get("name").asText();
        return name;
    }

    /**
     * Llame al Microservicio de transacciones y busque todas las transacciones
     * que pertenecen a la cuenta.
     *
     * @param iban account number of the customer
     * @return All transaction that belong this account
     */
    private List<?> getTransactions(String iban) {
        WebClient build = webClientBuilder.clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://businessdomain-transactions/transaction")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "http://businessdomain-transactions/transaction"))
                .build();

        List<?> transactions = build.method(HttpMethod.GET).uri(uriBuilder -> uriBuilder
                .path("/customer/transactions")
                // ibanAccount debe ser igual a repository (transactionsRepository del metodo que esta en la interface
                .queryParam("ibanAccount", iban)
                .build())
                .retrieve().bodyToFlux(Object.class).collectList().block();

        return transactions;
    }

}
