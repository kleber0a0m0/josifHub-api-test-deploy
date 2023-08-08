package br.edu.josifHubapi;
import br.edu.josifHubapi.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class JosifHubApiApplication {
//	@Autowired
//	private MailService mailService;
	public static String dataAtual() {
		LocalDateTime agora = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy");
		return agora.format(formatter);
	}

	public static void main(String[] args) {
		SpringApplication.run(JosifHubApiApplication.class, args);
		System.out.println("=================================================");
		System.out.println("Servidor Iniciado!!!!!\n=)");
		System.out.println(dataAtual());
		System.out.println("=================================================");

	}
//	@EventListener(ApplicationReadyEvent.class)
//	public void sendEmail() {
//		mailService.sendMailConfirmarCadastro("kleber0a0m@gmail.com");
//	}

}
