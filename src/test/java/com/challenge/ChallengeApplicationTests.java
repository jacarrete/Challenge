package com.challenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChallengeApplication.class)
public class ChallengeApplicationTests {

	@Test
	public void contextLoads() {
	}

	@Test
	public void challengeApplicationTest() {
		ChallengeApplication.main(new String[] {});
	}
}
