package com.yash.aws.lambdademo;

import java.io.File;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.amazonaws.services.s3.transfer.Upload;

public class App {

	public static void main(String[] args) throws Exception {

		String bucketName = args[0];
		String keyName = args[1];
		String filePath = args[2];

		try {
			AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
									.withRegion(Regions.US_EAST_1)
									.withCredentials(new ProfileCredentialsProvider())
									.build();
			TransferManager tm = TransferManagerBuilder.standard().withS3Client(s3Client).build();

			// TransferManager processes all transfers asynchronously,
			// so this call returns immediately.
			Upload upload = tm.upload(bucketName, keyName, new File(filePath));
			System.out.println("Object upload started");

			// Optionally, wait for the upload to finish before continuing.
			upload.waitForCompletion();
			System.out.println("Object upload complete");
		} catch (AmazonServiceException ex) {
			// The call was transmitted successfully, but Amazon S3 couldn't process
			// it, so it returned an error response.
			ex.printStackTrace();
		} catch (SdkClientException ex) {
			// Amazon S3 couldn't be contacted for a response, or the client
			// couldn't parse the response from Amazon S3.
			ex.printStackTrace();
		}
	}
}
