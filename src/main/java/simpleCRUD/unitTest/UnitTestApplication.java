package simpleCRUD.unitTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import simpleCRUD.unitTest.model.Customer;
import simpleCRUD.unitTest.repository.CustomerRepository;
import simpleCRUD.unitTest.service.CustomerService;
import simpleCRUD.unitTest.service.implementation.CustomerImpl;

import java.util.Date;

@SpringBootApplication
public class UnitTestApplication {
    public static void main(String[] args) {
		SpringApplication.run(UnitTestApplication.class, args);

	}

}
