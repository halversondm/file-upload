package org.dmhweb.controller;

import org.apache.commons.codec.binary.Base64InputStream;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;
import org.apache.cxf.jaxrs.ext.multipart.Multipart;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * Created by Daniel on 9/21/2015.
 */
@Transactional
public class FileUpload {

    private static final Logger LOGGER = LogManager.getLogger(FileUpload.class);

    private DataSource dataSource;

    @POST
    @Consumes("multipart/form-data")
    public Response uploadFile(@Multipart(value = "file") Attachment attachment) {
        long startTime = System.currentTimeMillis();
        long endTime;
        Connection conn;
        PreparedStatement ps;

        try {
            Base64InputStream base64InputStream = new Base64InputStream(attachment.getDataHandler().getInputStream(), true, 0, null);
            String fileName = attachment.getDataHandler().getName();
            conn = dataSource.getConnection();
            ps = conn.prepareStatement("INSERT INTO FILE_TABLE (FILE_NAME, FILE_DATA) VALUES (?,?)");
            ps.setString(1, fileName);
            ps.setBinaryStream(2, base64InputStream);
            ps.execute();
            ps.close();
            conn.close();
            endTime = System.currentTimeMillis();
        } catch (Exception e) {
            LOGGER.error("", e);
            return Response.serverError().build();
        }

        LOGGER.info("Time to Complete {}", endTime - startTime);
        return Response.ok().build();
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
