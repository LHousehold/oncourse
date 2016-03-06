package com.oncourse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.faces.bean.ManagedProperty;

@ManagedBean
@RequestScoped


public class FileUploadMBean implements Serializable {

    @ManagedProperty(value="#{dbConnection}")

    private DbConnector db;

    public void setdb(DbConnector db) {
        this.db = db;
    }

    private static final long serialVersionUID = 1L;
    private Part file1;
    private String message = null;
    public Part getFile1() {
        return file1;
    }
    public void setFile1(Part file1) {
        this.file1 = file1;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String uploadCourseFile(int coursePackageid) {

        System.out.println("Howard is a piece of poo and is worth " + coursePackageid);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context
                .getExternalContext().getContext();
        String path = "/var/www/oncourse";

        ///usr/share/tomcat/webapps/oncourse
        //This is the path I'm getting....

        String file1Success = "false";

        if (file1 != null && file1.getSize() > 0) {
            String fileName = Utils.getFileNameFromPart(file1);
            /**
            * destination where the file will be uploaded
            */
            // test insert
            FileQuery nFile = new FileQuery();

            nFile.cpid = coursePackageid;

            File outputFile = new File(path + File.separator + "uploads" + File.separator + nFile.cpid
                    + File.separator + fileName);

            File outputFolder = new File(path + File.separator + "uploads" + File.separator + nFile.cpid
                    + File.separator);

            outputFolder.mkdir();

            String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

            if(extension.equals("pdf"))
                nFile.media_type = "PDF";
            else if(extension.equals("mp4"))
                nFile.media_type = "VIDEO";
            else if(extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif"))
                nFile.media_type = "IMAGE";
            else if(extension.equals("mp3"))
                nFile.media_type = "MP3";
            else
                file1Success = "incompatible";

            nFile.source = path + File.separator + "files" + File.separator + "uploads"
                    + File.separator + fileName;
            nFile.name = fileName;

            if(file1Success.equals("incompatible") == false){
                try{
                    inputStream = file1.getInputStream();
                    outputStream = new FileOutputStream(outputFile);
                    byte[] buffer = new byte[Constants.BUFFER_SIZE];
                    int bytesRead = 0;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                }
                catch(Exception e){
                    System.err.println("Error in File Input");
                }

                db.writeTable(nFile);
                file1Success = "true";
            }
        }

        if (file1Success.equals("true")) {
            System.out.println("File uploaded to : " + path);
            /**
            * set the success message when the file upload is successful
            */
            setMessage("File successfully uploaded!");

        }
        else if(file1Success.equals("incompatible")){
            setMessage("Incompatible file type uploaded. Please upload a supported file type.");
        }
        else {
            /**
            * set the error message when error occurs during the file upload
            */
            setMessage("Error, no files selected!");
        }
        /**
        * return to the same view
        */
        return null;
    }
}
