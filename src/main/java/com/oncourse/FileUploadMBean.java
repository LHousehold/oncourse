package com.oncourse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.faces.bean.ManagedProperty;

@ManagedBean
@ViewScoped


public class FileUploadMBean implements Serializable {

    @ManagedProperty(value="#{dbConnection}")

    private DbConnector db;

    public void setdb(DbConnector db) {
        this.db = db;
    }


    private static final long serialVersionUID = 1L;
    private Part file1;
    private String message;
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
    public String uploadFile() throws IOException {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        FacesContext context = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) context
                .getExternalContext().getContext();
        String path = servletContext.getRealPath("");

        ///usr/share/tomcat/webapps/oncourse
        //This is the path I'm getting....

        String file1Success = "false";

        if (file1 != null && file1.getSize() > 0) {
            String fileName = Utils.getFileNameFromPart(file1);
            /**
            * destination where the file will be uploaded
            */
            File outputFile = new File(path + File.separator + "files" + File.separator + "uploads"
                    + File.separator + fileName);

            String extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());

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

            // test insert
            FileQuery nFile = new FileQuery();

            // note that no id is given because the database sets this automaticly
            // so the underliying query should not try to write its own

            nFile.cpid = 9001;
            //nFile.media_type = "pdf";

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
