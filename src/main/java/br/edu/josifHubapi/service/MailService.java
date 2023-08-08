package br.edu.josifHubapi.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail1(String to, String subject, String htmlContent) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("kleber0a0m@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar o email: " + e.getMessage());
        }
    }

    public void sendMailConfirmarCadastro(String to, String tokenConfirmacaoCadastro) {
        String htmlContent = getEmailTemplate();
        htmlContent = htmlContent.replace("{url}", "https://www.bing.com/search?q="+tokenConfirmacaoCadastro);

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("Confirmação de cadastro");
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar o email: " + e.getMessage());
        }
    }

    private String getEmailTemplate() {
        try {
            String filePath = "src/main/resources/templates/mail/confirmar-cadastro.html";
            StringBuilder contentBuilder = new StringBuilder();
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                contentBuilder.append(line);
            }
            bufferedReader.close();
            return contentBuilder.toString();
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo de template: " + e.getMessage());
            return "";
        }
    }
}
