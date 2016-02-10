package com.oncourse;

import java.io.IOException;
import java.io.FileOutputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;
import java.io.InputStream;
/**
 *
 * @author ramki
 */
@ManagedBean
@SessionScoped
public class DemoBean {

    public Part file1;
    public Part file2;

    // getters and setters for file1 and file2
    public Part getFile1(){
        return file1;
    }

    public Part getFile2(){
        return file2;
    }

    public void setFile1(Part file1){
        this.file1 = file1;
        return;
    }

    public void setFile2(Part file2){
        this.file2 = file2;
        return;
    }

    public String upload() throws IOException {
         InputStream inputStream = file1.getInputStream();
        FileOutputStream outputStream = new FileOutputStream(getFilename(file1));

        byte[] buffer = new byte[4096];
        int bytesRead = 0;
        while(true) {
            bytesRead = inputStream.read(buffer);
            if(bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }else {
                break;
            }
        }
        outputStream.close();
        inputStream.close();

        return "success";
    }

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}
