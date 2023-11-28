package com.better.betterbackend.domain.challenge.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile


@Service
class S3UploadService (

    private val amazonS3: AmazonS3,

    @Value("\${cloud.aws.s3.bucket}")
    private val bucket: String,

) {

    fun saveFile(multipartFile: MultipartFile): String {
        val originalFilename = multipartFile.originalFilename
        val metadata = ObjectMetadata()
        metadata.contentLength = multipartFile.size
        metadata.contentType = multipartFile.contentType
        amazonS3.putObject(bucket, originalFilename, multipartFile.inputStream, metadata)

        return amazonS3.getUrl(bucket, originalFilename).toString()
    }

}