package me.vrublevsky.neotech.domains.config

import ch.tutteli.atrium.api.fluent.en_GB.*
import me.vrublevsky.neotech.domains.expect
import org.junit.jupiter.api.Test

class LoggingMaskingConverterTest {

    @Test
    fun `api key is masked`() {
        convert("https://api.sandbox.namecheap.com/xml.response?Command=namecheap.users.getPricing&ProductType=DOMAIN&ActionName=REGISTER&ApiUser=FylmTM&ApiKey=ccaaaaaaaaa1111111111111___bbbbb&UserName=FylmTM&ClientIp=95.68.90.78")
            .expect
            .toBe("https://api.sandbox.namecheap.com/xml.response?Command=namecheap.users.getPricing&ProductType=DOMAIN&ActionName=REGISTER&ApiUser=FylmTM&ApiKey=***&UserName=FylmTM&ClientIp=95.68.90.78")
        convert("https://www.whoisxmlapi.com/whoisserver/WhoisService?domainName=buy-new-test-domain.com&outputFormat=json&apiKey=cc_1234bccccccccdd_1111111223ttt")
            .expect
            .toBe("https://www.whoisxmlapi.com/whoisserver/WhoisService?domainName=buy-new-test-domain.com&outputFormat=json&apiKey=***")
    }
}
