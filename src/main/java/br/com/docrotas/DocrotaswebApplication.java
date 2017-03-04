package br.com.docrotas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "br.com.docrotas.docrotasweb.entity")
@EnableJpaRepositories(basePackages = "br.com.docrotas.docrotasweb.repository")
public class DocrotaswebApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocrotaswebApplication.class, args);
	}
}
