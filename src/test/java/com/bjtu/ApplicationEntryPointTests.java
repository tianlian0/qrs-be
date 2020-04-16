package com.bjtu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationEntryPointTests {

	@Test
	public void transaction() {
		System.out.println("content load.");
	}
}
