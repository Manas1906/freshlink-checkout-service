package com.freshlink.checkout;

import com.freshlink.checkout.config.TestFeignConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestFeignConfig.class)
@ActiveProfiles("test")
class CheckoutServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
