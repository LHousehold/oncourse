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
import javax.faces.context.ExternalContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;
import javax.faces.bean.ManagedProperty;
import java.util.Map;
import java.util.List;
import java.util.Iterator;
import org.apache.commons.io.FilenameUtils;

import org.apache.pdfbox.util.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.exceptions.COSVisitorException;

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
    public String uploadCourseFile() {
        // FacesContext context = FacesContext.getCurrentInstance();
        //
        // Map<String,String> params = context.getExternalContext().getRequestParameterMap();
	    // String coursePackageids = params.get("coursePackageid");
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        String coursePackageids = ec.getRequestParameterMap().get("back_upload_form:cpid_holder");

        System.out.println(coursePackageids);

        int coursePackageid = Integer.valueOf(coursePackageids);

        InputStream inputStream = null;
        OutputStream outputStream = null;
        // ServletContext servletContext = (ServletContext) context
        //         .getExternalContext().getContext();
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

            String fileNameWithOutExt = FilenameUtils.removeExtension(fileName);

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

            nFile.source = path + File.separator + "uploads" + File.separator + nFile.cpid
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

                if(extension.equals("pdf")){

                    PDDocument document = new PDDocument();
                    try{
                        document = PDDocument.load(path + File.separator + "uploads" + File.separator + nFile.cpid
                            + File.separator + fileName);
                    }
                    catch(Exception e){
                        System.err.println("Error in Saving");
                    }

                        // Create a Splitter object
                    Splitter splitter = new Splitter();

                    // We need this as split method returns a list
                    List<PDDocument> listOfSplitPages;

                    // We are receiving the split pages as a list of PDFs
                    try{
                        listOfSplitPages = splitter.split(document);

                        // We need an iterator to iterate through them
                        Iterator<PDDocument> iterator = listOfSplitPages.listIterator();

                        // I am using variable i to denote page numbers.
                        int i = 1;
                        while(iterator.hasNext()){
                            PDDocument pd = iterator.next();
                            try{
                                // Saving each page with its assumed page no.
                                pd.save(path + File.separator + "uploads" + File.separator + nFile.cpid
                                        + File.separator + fileNameWithOutExt + "_Page_" + i + ".pdf");

                                nFile.name = fileNameWithOutExt + "_Page_" + i + ".pdf";
                                nFile.source = path + File.separator + "uploads" + File.separator + nFile.cpid
                                        + File.separator + fileNameWithOutExt + "_Page_" + i++ + ".pdf";

                                db.writeTable(nFile);

                            } catch (COSVisitorException anException){
                                // Something went wrong with a PDF object
                                System.out.println("Something went wrong with page " + (i-1) + "\n Here is the error message" + anException);
                            }
                        }
                    }
                    catch(Exception e){
                        System.err.println("Error in Splitting");
                    }

                }


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

        String temp = "?faces-redirect=true&amp;cpid = " + coursePackageid;

        return "cpid = " + coursePackageid;
    }
}
