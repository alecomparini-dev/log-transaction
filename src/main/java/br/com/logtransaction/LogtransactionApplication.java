package br.com.logtransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@ComponentScan("br.com.logtransaction")
public class LogtransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogtransactionApplication.class, args);
	}

}