package br.edu.josifHubapi;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class JosifHubApiApplication {
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

}
