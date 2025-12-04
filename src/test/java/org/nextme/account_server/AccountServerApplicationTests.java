package org.nextme.account_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@SpringBootTest
class AccountServerApplicationTests {

	@Test
	void contextLoads() {
        String conId = "%7B%22result%22%3A%7B%22code%22%3A%22CF-00000%22%2C%22extraMessage%22%3A%22%22%2C%22message%22%3A%22%EC%84%B1%EA%B3%B5%22%2C%22transactionId%22%3A%226930d7c6231b0a42452dbdba%22%7D%2C%22data%22%3A%7B%22successList%22%3A%5B%7B%22clientType%22%3A%22P%22%2C%22code%22%3A%22CF-00000%22%2C%22loginType%22%3A%221%22%2C%22countryCode%22%3A%22KR%22%2C%22organization%22%3A%220088%22%2C%22extraMessage%22%3A%22%22%2C%22businessType%22%3A%22BK%22%2C%22message%22%3A%22%EC%84%B1%EA%B3%B5%22%2C%22transactionId%22%3A%226930d7c6231b0a42452dbdbb%22%7D%5D%2C%22errorList%22%3A%5B%5D%2C%22connectedId%22%3A%22bhyfBzmK4DP9ld6ihlX2GX%22%7D%7D";
        String result = URLDecoder.decode(conId, StandardCharsets.UTF_8);
        System.out.println(result);


        String acc_re = "%7B%22result%22%3A%7B%22code%22%3A%22CF-00000%22%2C%22extraMessage%22%3A%22%22%2C%22message%22%3A%22%EC%84%B1%EA%B3%B5%22%2C%22transactionId%22%3A%226930d862231b0a42452dbdbc%22%7D%2C%22data%22%3A%7B%22resDepositTrust%22%3A%5B%7B%22resAccount%22%3A%22110523769877%22%2C%22resAccountDisplay%22%3A%22110-523-769877%22%2C%22resAccountBalance%22%3A%2263%22%2C%22resAccountDeposit%22%3A%2211%22%2C%22resAccountNickName%22%3A%22%22%2C%22resAccountStartDate%22%3A%2220201205%22%2C%22resAccountEndDate%22%3A%22%22%2C%22resAccountName%22%3A%22%EC%8F%A0%ED%8E%B8%ED%95%9C+%EC%9E%85%EC%B6%9C%EA%B8%88%ED%86%B5%EC%9E%A5%28%EC%A0%80%EC%B6%95%EC%98%88%EA%B8%88%29%22%2C%22resAccountCurrency%22%3A%22KRW%22%2C%22resAccountLifetime%22%3A%22%22%2C%22resLastTranDate%22%3A%2220251117%22%2C%22resOverdraftAcctYN%22%3A%220%22%2C%22resLoanKind%22%3A%22%22%2C%22resLoanBalance%22%3A%22%22%2C%22resLoanStartDate%22%3A%22%22%2C%22resLoanEndDate%22%3A%22%22%7D%2C%7B%22resAccount%22%3A%22110615483998%22%2C%22resAccountDisplay%22%3A%22110-615-483998%22%2C%22resAccountBalance%22%3A%221%22%2C%22resAccountDeposit%22%3A%2211%22%2C%22resAccountNickName%22%3A%22%22%2C%22resAccountStartDate%22%3A%2220251117%22%2C%22resAccountEndDate%22%3A%22%22%2C%22resAccountName%22%3A%22SOL+Plan+%ED%8F%AC%EC%9D%B8%ED%8A%B8%EB%B0%95%EC%8A%A4%22%2C%22resAccountCurrency%22%3A%22KRW%22%2C%22resAccountLifetime%22%3A%22%22%2C%22resLastTranDate%22%3A%2220251122%22%2C%22resOverdraftAcctYN%22%3A%220%22%2C%22resLoanKind%22%3A%22%22%2C%22resLoanBalance%22%3A%22%22%2C%22resLoanStartDate%22%3A%22%22%2C%22resLoanEndDate%22%3A%22%22%7D%2C%7B%22resAccount%22%3A%22110615485006%22%2C%22resAccountDisplay%22%3A%22110-615-485006%22%2C%22resAccountBalance%22%3A%220%22%2C%22resAccountDeposit%22%3A%2211%22%2C%22resAccountNickName%22%3A%22%22%2C%22resAccountStartDate%22%3A%2220251117%22%2C%22resAccountEndDate%22%3A%22%22%2C%22resAccountName%22%3A%22%5B%EA%B8%88%EC%9C%B5%EA%B1%B0%EB%9E%98%ED%95%9C%EB%8F%84%EA%B3%84%EC%A2%8C1%5D%EC%8B%A0%ED%95%9C+%EB%95%A1%EA%B2%A8%EC%9A%94%ED%8E%98%EC%9D%B4+%ED%86%B5%EC%9E%A5%22%2C%22resAccountCurrency%22%3A%22KRW%22%2C%22resAccountLifetime%22%3A%22%22%2C%22resLastTranDate%22%3A%2220251122%22%2C%22resOverdraftAcctYN%22%3A%220%22%2C%22resLoanKind%22%3A%22%22%2C%22resLoanBalance%22%3A%22%22%2C%22resLoanStartDate%22%3A%22%22%2C%22resLoanEndDate%22%3A%22%22%7D%2C%7B%22resAccount%22%3A%22223003027661%22%2C%22resAccountDisplay%22%3A%22223-003-027661%22%2C%22resAccountBalance%22%3A%223400000%22%2C%22resAccountDeposit%22%3A%2214%22%2C%22resAccountNickName%22%3A%22%22%2C%22resAccountStartDate%22%3A%2220090506%22%2C%22resAccountEndDate%22%3A%22%22%2C%22resAccountName%22%3A%22%EB%A7%88%EC%9D%B4%ED%99%88%ED%94%8C%EB%9E%9C+%EC%A3%BC%ED%83%9D%EC%B2%AD%EC%95%BD+%EC%A2%85%ED%95%A9%EC%A0%80%EC%B6%95%22%2C%22resAccountCurrency%22%3A%22KRW%22%2C%22resAccountLifetime%22%3A%22%22%2C%22resLastTranDate%22%3A%22%22%2C%22resOverdraftAcctYN%22%3A%220%22%2C%22resLoanKind%22%3A%22%22%2C%22resLoanBalance%22%3A%22%22%2C%22resLoanStartDate%22%3A%22%22%2C%22resLoanEndDate%22%3A%22%22%7D%5D%2C%22resForeignCurrency%22%3A%5B%5D%2C%22resFund%22%3A%5B%5D%2C%22resLoan%22%3A%5B%5D%2C%22resInsurance%22%3A%5B%5D%7D%2C%22connectedId%22%3A%22bhyfBzmK4DP9ld6ihlX2GX%22%7D";
                String conn = URLDecoder.decode(acc_re, StandardCharsets.UTF_8);
        System.out.println(conn + "conn");
    }

}
