package com.bcbsma.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = SpringBootApp.class)
public class TestDrivenDevelopmentApplicationTest {

	@Test
	public void contextLoads() {
	}

//	@Test
//	public void main() throws Exception {
//		SpringBootApp.main(new String[] {});
//	}
}
