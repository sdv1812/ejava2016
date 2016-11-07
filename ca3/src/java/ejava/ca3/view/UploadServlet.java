/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejava.ca3.view;

import ejava.ca3.business.PodBean;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.glassfish.jersey.media.multipart.MultiPart;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.media.multipart.file.FileDataBodyPart;

@WebServlet(urlPatterns = {"/upload"})
@MultipartConfig
public class UploadServlet extends HttpServlet {

    @EJB
    PodBean podBean;
    @Inject private ClientInstance client; 

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        
        
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            String podId = new String(readPart(req.getPart("podId")));
            String note = new String(readPart(req.getPart("note")));
            String time = new String(readPart(req.getPart("time")));
            byte[] image = readPart(req.getPart("image"));
            
            podBean.submitPodDetails(podId, note, image, time);
            
            File image_ = convertToFile(req.getPart("image"));
            uploadToServer(podId,note,image_,time); 
        } catch (ParseException ex) {
            Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private byte[] readPart(Part p) throws IOException {
        byte[] buffer = new byte[1024 * 8];
        int sz = 0;
        try (InputStream is = p.getInputStream()) {
            BufferedInputStream bis = new BufferedInputStream(is);
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                while (-1 != (sz = bis.read(buffer))) {
                    baos.write(buffer, 0, sz);
                }
                buffer = baos.toByteArray();
            }
        }
        return buffer;
    }
    
    public void uploadToServer(String podId, String note, File image, String time){
        Client client = ClientBuilder.newBuilder()
            .register(MultiPartFeature.class).build();
        
        MultiPart multiPart = new MultiPart();
        System.out.println("class of this" +image + "MMMM" +image.exists());
        FileDataBodyPart imgPart = new FileDataBodyPart("image", 
				image,
				MediaType.APPLICATION_OCTET_STREAM_TYPE);
		imgPart.setContentDisposition(
				FormDataContentDisposition.name("image")
				.fileName("ca3.png").build());

		MultiPart formData = new FormDataMultiPart()
				.field("podId", podId, MediaType.TEXT_PLAIN_TYPE)
				.field("note", note, MediaType.TEXT_PLAIN_TYPE)
				.field("teamId", "ed29b478", MediaType.TEXT_PLAIN_TYPE)
                                .field("callback", "http://10.10.2.80:8080/ca3/callback")
				.bodyPart(imgPart);
		formData.setMediaType(MediaType.MULTIPART_FORM_DATA_TYPE);

        WebTarget target = client.target("http://10.10.0.50:8080/epod/upload");
		Invocation.Builder inv = target.request();

		System.out.println(">>> part: " + formData);

		Response callResp = inv.post(Entity.entity(formData, formData.getMediaType()));

    }
    
    public File convertToFile(Part file){
        BufferedImage bfr ;
        File filenew = new File("newImage.jpg");
        
        try {
            bfr = ImageIO.read(file.getInputStream());
           ImageIO.write(bfr, "jpg", filenew);
        } catch (IOException ex) {
            Logger.getLogger(UploadServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return filenew;
    }

//        MultivaluedMap<String, String> form = new MultivaluedHashMap<>();
//        form.add("teamId", "ejava_srisan");
//        form.add("callback", "10.10.2.80:8080/ca3/upload");
//        form.add("podId",podId);
//        form.add("note", note);
//        form.add("image", image.toString());
//        
//        Response resp = client.target("http://10.10.0.50:8080/epod").request(MediaType.MULTIPART_FORM_DATA)
//                .post(Entity.form(form),Response.class);
        
    
}
