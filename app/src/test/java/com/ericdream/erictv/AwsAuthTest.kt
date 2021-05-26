package com.ericdream.erictv

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.text.DateFormat
import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

/**
 * Ref <br />
 * https://docs.aws.amazon.com/general/latest/gr/sigv4-signed-request-examples.html
 */
@RunWith(JUnit4::class)
class AwsAuthTest {
    val method = "GET"
    val service = "S3"
    val host = "s3.amazonaws.com"
    val region = "ap-northeast-1"
    val endpoint = "https://s3.amazonaws.com"
    val request_parameters = "Action=DescribeRegions&Version=2013-10-15"

    @Throws(Exception::class)
    fun HmacSHA256(data: String, key: ByteArray?): ByteArray {
        val algorithm = "HmacSHA256"
        val mac: Mac = Mac.getInstance(algorithm)
        mac.init(SecretKeySpec(key, algorithm))
        return mac.doFinal(data.toByteArray(charset("UTF-8")))
    }

    @Throws(Exception::class)
    fun getSignatureKey(
        key: String,
        dateStamp: String,
        regionName: String,
        serviceName: String
    ): ByteArray? {
        val kSecret = "AWS4$key".toByteArray(charset("UTF-8"))
        val kDate = HmacSHA256(dateStamp, kSecret)
        val kRegion = HmacSHA256(regionName, kDate)
        val kService = HmacSHA256(serviceName, kRegion)
        return HmacSHA256("aws4_request", kService)
    }

    @Test
    fun startProcess() {
        //read from env
        val access_key = "AWS_ACCESS_KEY_ID"
        val secret_key = "AWS_SECRET_ACCESS_KEY"
        // Create a date for headers and the credential string
        val df: DateFormat = DateFormat.getTimeInstance()
        df.setTimeZone(TimeZone.getTimeZone("gmt"))
        val gmtTime: String = df.format(Date())
        println(gmtTime)
    }
}