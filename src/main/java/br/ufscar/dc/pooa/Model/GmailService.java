package br.ufscar.dc.pooa.Model;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;
import java.util.Base64;

// This class is responsible for sending emails to clients when a reservation is available
// It uses the Gmail API to send the emails(Google Cloud Platform)
// email: pooaenzo@gmail.com

public class GmailService implements Observer  {

    private static final String APPLICATION_NAME = "HotelReservation";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "C:\\Users\\enzod\\Desktop\\tokens";
    private String email;

    private static final java.util.List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);
    private static final String CREDENTIALS_FILE_PATH = "C:\\Users\\enzod\\Desktop\\credenciais.json";

    public GmailService(String email){
        this.email = email;
    }




    private static Credential getCredentials() throws Exception {
        File credentialsFile = new File(CREDENTIALS_FILE_PATH);
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(credentialsFile));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static void sendEmail(String to, String subject, String bodyText) throws Exception {
        Gmail service = new Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, getCredentials())
                .setApplicationName(APPLICATION_NAME)
                .build();

        MimeMessage email = createEmail(to, "pooaenzo@gmail.com", subject, bodyText);
        sendMessage(service, "me", email);
    }

    public static MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));
        email.setSubject(subject);
        email.setText(bodyText);

        return email;
    }

    public static void sendMessage(Gmail service, String userId, MimeMessage email) throws MessagingException, IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);
        service.users().messages().send(userId, message).execute();
    }



    @Override
    public void update(String tipo) throws SQLException, ClassNotFoundException {
        if(tipo.equals("Reserva_Disponivel")){
            try {
                sendEmail(email, "(NÃO RESPONDA) Reserva disponível", "A reserva que você estava esperando está disponível!\n" +
                        "Para mais informações, entre em contato com a recepção do hotel.\n\n"+
                        "Não responda a este email, ele é gerado automaticamente.");
            } catch (Exception e) {
                System.out.println("Erro ao enviar email de reserva disponível");
            }
        }
        else if(tipo.equals("Reserva_Feita")){
            try {
                sendEmail(email, "(NÃO RESPONDA) Reserva feita", "Sua reserva foi feita com sucesso!\n" +
                        "Para mais informações, entre em contato com a recepção do hotel.\n\n"+
                        "Não responda a este email, ele é gerado automaticamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
