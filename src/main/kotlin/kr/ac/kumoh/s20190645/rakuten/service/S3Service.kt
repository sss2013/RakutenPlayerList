package kr.ac.kumoh.s20190645.rakuten.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import software.amazon.awssdk.services.s3.presigner.S3Presigner
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest
import java.time.Duration

@Service
class S3Service (

    @Value("\${spring.cloud.aws.s3.bucket}") //application.propertiesに書いてるバケット名利用
    private val bucket : String,
    private val s3Presigner : S3Presigner
) {

    fun createPresignedUrl(path: String): String {
        val putObjectRequest = PutObjectRequest.builder()
            .bucket(bucket)
            .key(path)
            .build()
        val preSignRequest = PutObjectPresignRequest.builder()
            .signatureDuration(Duration.ofMinutes(3))
            .putObjectRequest(putObjectRequest)
            .build()
        return s3Presigner.presignPutObject(preSignRequest).url().toString()
    }
}
