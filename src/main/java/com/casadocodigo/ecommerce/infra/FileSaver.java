package com.casadocodigo.ecommerce.infra;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

/**
 *
 * @author vyniciuspontes
 */
@RequestScoped
public class FileSaver {
    
    private final static Logger LOGGER =
            Logger.getLogger(FileSaver.class.getCanonicalName());
    
    private static final String CONTENT_DISPOSITION = "content-disposition";

    private static final String FILENAME_KEY = "filename=";
    
    public String write(String baseFolder, Part multipartFile) throws IOException{
        
        final String path = baseFolder;
        final Part filePart = multipartFile;
        final String fileName = extractFilename(multipartFile.getHeader(CONTENT_DISPOSITION));

        new File(path).mkdir();
        
        OutputStream out = null;
        InputStream filecontent = null;

        try {
            out = new FileOutputStream(new File(path + File.separator
                    + fileName));
            filecontent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];

            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            LOGGER.log(Level.INFO, "File {0} being uploaded to {1}",
                    new Object[]{fileName, path});

        } catch (IOException ex) {
            Logger.getLogger(FileSaver.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
        return baseFolder + "/" + fileName;
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
