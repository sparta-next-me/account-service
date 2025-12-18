package org.nextme.account_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class AccountServerApplicationTests {

	@Test
	void contextLoads() {
        String a = "%7B%22result%22%3A%7B%22code%22%3A%22CF-00000%22%2C%22extraMessage%22%3A%22%22%2C%22message%22%3A%22%EC%84%B1%EA%B3%B5%22%2C%22transactionId%22%3A%2269438514efde5fa624a32126%22%7D%2C%22data%22%3A%7B%22successList%22%3A%5B%7B%22clientType%22%3A%22P%22%2C%22code%22%3A%22CF-00000%22%2C%22loginType%22%3A%221%22%2C%22countryCode%22%3A%22KR%22%2C%22organization%22%3A%220088%22%2C%22extraMessage%22%3A%22%22%2C%22businessType%22%3A%22BK%22%2C%22message%22%3A%22%EC%84%B1%EA%B3%B5%22%7D%5D%2C%22errorList%22%3A%5B%5D%2C%22connectedId%22%3A%226HXf4K6j4Rl8azcymLxKnM%22%7D%7D";
        String b = URLDecoder.decode(a, StandardCharsets.UTF_8);
        System.out.println(b);
    }

}
