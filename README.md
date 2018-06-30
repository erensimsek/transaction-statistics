# A Restful API for transaction statistics.
Restful API for transaction statistics
The main use case for this API is to calculate realtime statistic from the last 60 seconds. There are two APIs, one of them is
called every time a transaction is made. It is also the sole input of this rest API. The other one
returns the statistic based of the transactions of the last 60 seconds.


Compile and Run with:

    mvn spring-boot:run

For API Documentation Swagger-UI point your browser to:

    http://localhost:8080/
     
For REST API with all methods

    http://localhost:8080/demo/swagger-ui.html
    http://localhost:8080/swagger-ui.html#!/statistics-controller/getStatisticsUsingGET
    http://localhost:8080/swagger-ui.html#!/statistics-controller/createTransactionUsingPOST
   
    
For getting statistic with curl:

    curl -X GET --header 'Accept: application/json' 'http://localhost:8080/api/v1/statistics'
       
For add new transaction with curl:

    curl -X POST --header 'Content-Type: application/json;charset=UTF-8' --header 'Accept: */*' -d '{ \ 
       "amount": 2.6, \ 
       "timestamp": 1530382681105 \ 
     }' 'http://localhost:8080/api/v1/transactions'
     
