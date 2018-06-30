package com.eren.n26.hiring.demo;

import com.eren.n26.hiring.demo.pojo.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StatisticControllerTests {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticControllerTests.class);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private int port;


    @Test
    public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
        @SuppressWarnings("rawtypes")
        ResponseEntity<Map> entity = this.testRestTemplate.getForEntity("http://localhost:" + this.port + "/actuator",
                Map.class);

        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturn201WhenSendingEmptyRequestToController() throws Exception {
        Transaction req = new Transaction.TransactionBuilder().amount(2.5d).timestamp(System.currentTimeMillis()).build();
        ResponseEntity entity = this.testRestTemplate
                .postForEntity("http://localhost:" + this.port + "/api/v1/transactions", req, ResponseEntity.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }
    @Test
    public void shouldReturn204WhenSendingOlder60secTransactionRequestToController() throws Exception {
        Transaction req = new Transaction.TransactionBuilder().amount(2.5d).timestamp(System.currentTimeMillis()-120000).build();
        ResponseEntity entity = this.testRestTemplate
                .postForEntity("http://localhost:" + this.port + "/api/v1/transactions", req, ResponseEntity.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
    @Test
    public void shouldReturn400WhenSendingEmptyRequestToController() throws Exception {
        Transaction req = new Transaction();
        ResponseEntity<String> entity = this.testRestTemplate
                .postForEntity("http://localhost:" + this.port + "/api/v1/transactions", req, String.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
