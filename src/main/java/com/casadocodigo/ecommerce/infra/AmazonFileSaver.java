/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.casadocodigo.ecommerce.infra;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.servlet.http.Part;

/**
 *
 * @author vyniciuspontes
 */
@RequestScoped
public class AmazonFileSaver {
    
    private final static Logger LOGGER =
            Logger.getLogger(FileSaver.class.getCanonicalName());
    
    private static final String END_POINT = "https://s3-sa-east-1.amazonaws.com";
    private static final String BUCKET_NAME = "book-ecommerce";
    private static final String CONTENT_DISPOSITION = "content-disposition";
    private static final String FILENAME_KEY = "filename=";
    
    public String write(Part part){
        
        try {
            AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());
            
            String fileName = extractFilename(part.getHeader(CONTENT_DISPOSITION));
            
            OutputStream out = null;
            InputStream filecontent = null;
            
            File f = new File(fileName);
            
            out = new FileOutputStream(f);
            filecontent = part.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            
            s3client.putObject(BUCKET_NAME,fileName, f);
            

            return "";
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AmazonFileSaver.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException | AmazonClientException ex) {
            Logger.getLogger(AmazonFileSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
    public String write(String baseFolder, Part multipartFile) throws IOException {
        
        String fileName = extractFilename(multipartFile.getHeader(CONTENT_DISPOSITION));
        
        
        String path = baseFolder + File.separator + fileName;
        
        AmazonS3 s3client = new AmazonS3Client(new ProfileCredentialsProvider());

        
        ObjectMetadata metaData = new ObjectMetadata();
        byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
        metaData.setContentLength(bytes.length);
        /*ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, key, byteArrayInputStream, metadata);
        client.putObject(putObjectRequest);*/

        s3client.putObject(new PutObjectRequest(BUCKET_NAME, path, 
                multipartFile.getInputStream(), metaData)
        .withCannedAcl(CannedAccessControlList.PublicRead));
            
        /*s3client.putObject(BUCKET_NAME,fileName, 
                multipartFile.getInputStream(), metaData);*/
        return END_POINT + File.separator + BUCKET_NAME 
                + File.separator + path;
        
    }
    
    private String extractFilename(String contentDisposition) {
        if (contentDisposition == null) {
            return null;
        }
        int startIndex = contentDisposition
                .indexOf(FILENAME_KEY);
        if (startIndex == -1) {
            return null;
        }
        String filename = contentDisposition.substring(startIndex
                        + FILENAME_KEY.length());
        if (filename.startsWith("\"")) {
            int endIndex = filename.indexOf("\"", 1);
            if (endIndex != -1) {
                return filename.substring(1, endIndex);
            }
        } else {
            int endIndex = filename.indexOf(";");
            if (endIndex != -1) {
                return filename.substring(0, endIndex);
            }
        }
        return filename;
    }
}
