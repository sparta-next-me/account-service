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
        String a = "%7B%22result%22%3A%7B%22code%22%3A%22CF-04011%22%2C%22extraMessage%22%3A%22%22%2C%22message%22%3A%22%EC%82%AC%EC%9A%A9%EC%9E%90+%EA%B3%84%EC%A0%95%EC%A0%95%EB%B3%B4+%EC%82%AD%EC%A0%9C%EC%97%90+%EC%8B%A4%ED%8C%A8%ED%96%88%EC%8A%B5%EB%8B%88%EB%8B%A4.%22%2C%22transactionId%22%3A%226944ae55efde5fa624a3225f%22%7D%2C%22data%22%3A%7B%22successList%22%3A%5B%5D%2C%22errorList%22%3A%5B%7B%22clientType%22%3A%22P%22%2C%22code%22%3A%22CF-04038%22%2C%22loginType%22%3A%221%22%2C%22countryCode%22%3A%22KR%22%2C%22organization%22%3A%220088%22%2C%22extraMessage%22%3A%22%22%2C%22businessType%22%3A%22BK%22%2C%22message%22%3A%22%EC%9A%94%EC%B2%AD%ED%95%98%EC%8B%A0+%EC%BB%A4%EB%84%A5%ED%8B%B0%EB%93%9C%EC%95%84%EC%9D%B4%EB%94%94%28connectedId%29%EC%97%90+%ED%95%B4%EB%8B%B9+%EA%B3%84%EC%A0%95%EC%9D%B4+%EC%A1%B4%EC%9E%AC%ED%95%98%EC%A7%80+%EC%95%8A%EC%8A%B5%EB%8B%88%EB%8B%A4.+%EC%BB%A4%EB%84%A5%ED%8B%B0%EB%93%9C%EC%95%84%EC%9D%B4%EB%94%94%28connectedId%29%2C+%EA%B3%84%EC%A0%95+%EC%A0%95%EB%B3%B4%2C+%ED%86%A0%ED%81%B0%28access_token%29+%EB%93%B1%EC%9D%84+%ED%99%95%EC%9D%B8%ED%95%98%EC%84%B8%EC%9A%94.%22%7D%5D%2C%22connectedId%22%3A%22fNNGw-Xz4Zhax0jLQPPl24%22%7D%7D";
        String b = URLDecoder.decode(a, StandardCharsets.UTF_8);
        System.out.println(b);
    }

}
