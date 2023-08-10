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

    public String getEmailTemplate(String htmlFileName) {
        try {
            String filePath = "src/main/resources/templates/mail/" + htmlFileName + ".html";
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

    public void sendMailConfirmarCadastro(String email, String tokenConfirmacaoCadastro) {
        String htmlContent = getEmailTemplate("confirmar-cadastro");
        htmlContent = htmlContent.replace("{url}", "https://www.bing.com/search?q="+tokenConfirmacaoCadastro);//TODO: trocar o bing pelo link do front

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Confirmação de cadastro");
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar o email: " + e.getMessage());
        }
    }

    public void sendMailRecuperarSenha(String email, String tokenRecuperacaoSenha) {

        String htmlContent = getEmailTemplate("recuperar-senha");
        htmlContent = htmlContent.replace("{url}", "https://www.bing.com/search?q="+tokenRecuperacaoSenha);//TODO: trocar o bing pelo link do front

        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Recuperação de senha");
            helper.setText(htmlContent, true);
            mailSender.send(message);
            System.out.println("Email enviado com sucesso!");
        } catch (MessagingException e) {
            System.out.println("Erro ao enviar o email: " + e.getMessage());
        }
    }
}
