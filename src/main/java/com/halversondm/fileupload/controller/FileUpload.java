package com.halversondm.fileupload.controller;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by Daniel on 9/21/2015.
 */
@RestController
@Transactional
@RequestMapping("/rest")
public class FileUpload {

    private static final Logger LOGGER = LogManager.getLogger(FileUpload.class);
    private static final String INSERT = "INSERT INTO FILE_TABLE (FILE_NAME, FILE_DATA) VALUES (?,?)";

    @Autowired
    private DataSource dataSource;

    @PostMapping(path = "/fileUpload", consumes = "multipart/form-data")
    public String uploadFile(@RequestParam(value = "file") MultipartFile attachment) {
        long startTime = System.currentTimeMillis();
        long endTime;
        Connection conn;
        PreparedStatement ps;

        // You'll notice the Base64InputStream here. I had a requirement in another
        // project to store the data in Base64. I wanted to use the Base64InputStream
        // class to prove that out.
        try {
            Base64InputStream base64InputStream = new Base64InputStream(attachment.getInputStream(), true, 0, null);
            String fileName = attachment.getOriginalFilename();
            conn = dataSource.getConnection();
            ps = conn.prepareStatement(INSERT);
            ps.setString(1, fileName);
            ps.setBinaryStream(2, base64InputStream);
            ps.execute();
            ps.close();
            conn.close();
            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            LOGGER.error("", e);
            return "Failed upload";
        }

        LOGGER.info("Time to Complete {}", endTime - startTime);
        return "Successful upload";
    }

}
