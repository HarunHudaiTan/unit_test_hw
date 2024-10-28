package com.productManagement.demo;

import com.productManagement.demo.model.Product;
import com.productManagement.demo.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.util.HashSet;
import java.util.UUID;
import java.util.function.Supplier;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {
	private final ProductRepository productRepository;

	public DemoApplication(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);


	}
	@ConditionalOnProperty(
			prefix = "command. line.runner",
			value = "enabled",
			havingValue = "true",
			matchIfMissing = true)
	@Component
	public class CommandLineTaskExecutor implements CommandLineRunner {
		@Bean
		public Clock clock(){
			return
					Clock.systemUTC();
		}
		@Bean
		public Supplier<UUID> uuidSupplier(){
			return UUID::randomUUID;
		}

		@Override
		public void run(String... args) throws Exception {

		}
	}
    @Override
	public void run(String... args) throws Exception {
		Product product =productRepository.save(new Product(null,"name","description",100.0,100));

		System.out.println(product.toString());
	}

}
