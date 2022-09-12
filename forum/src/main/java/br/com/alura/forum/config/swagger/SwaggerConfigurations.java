package br.com.alura.forum.config.swagger;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.alura.forum.modelo.Usuario;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfigurations {
	
	@Bean
	public Docket forumApi() {
		
		return new Docket(DocumentationType.SWAGGER_2)//tipo de documentacao
				.select()
				.apis(RequestHandlerSelectors.basePackage("br.com.alura.forum"))//pacote onde ele vai comecar a ler as calsses do projeto
				.paths(PathSelectors.ant("/**"))//barra qualquer coisa para ler os enderecos para gerar documentacao
				.build()
				//ignorar o tipo Usuario dos meus endpoints
				.ignoredParameterTypes(Usuario.class)
				//Enviando token JWT no Swagger, configurando autorizacao
				.globalOperationParameters(Arrays.asList(
                        new ParameterBuilder()
                                .name("Authorization")
                                .description("Header para token JWT")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()));
		
		
	}

}
