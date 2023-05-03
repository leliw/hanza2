package pl.priv.leliwa.hanza2.invoice;

import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest(properties="spring.application.repository=Kafka")
class InvoiceApplicationTests {

    @Autowired
    ApplicationContext context;
    
	@Test
	void contextLoads() {
        assertTrue(context.containsBean("invoiceController"));
	}

}
