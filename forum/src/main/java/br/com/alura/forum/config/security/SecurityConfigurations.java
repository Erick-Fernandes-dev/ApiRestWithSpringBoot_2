package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Todas as configurações do projeto

@EnableWebSecurity//habilitar o Spring security, tudo habilitado
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	//obs: em configuracoes de service é possivel tmb fazer injecao de dependencias
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	
	// Configuracoes de autenticacao, a parte de login, controle de acesso
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(autenticacaoService)
		.passwordEncoder(
				//serve para criptografar senha de usuario, e é um das classes mais usadas
				new BCryptPasswordEncoder()
				);
		
	}
	
	 // Configuracoes de Autorizacao, configuracao de urls, quem pode acessa esssa url, perfil de acesso
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests()
		//Liberando acesso aos endpoints públicos
		// tudo que for /topicos permite todo o acesso
		.antMatchers(HttpMethod.GET, "/topicos").permitAll()
		.antMatchers(HttpMethod.GET, "/topicos/*").permitAll()
		//qualquer outra requisicao tem que está autenticado
		.anyRequest().authenticated()
		.and().formLogin();
		
	}
	
	// Configuracoes de recursos estaticos(JS, CSS, images, etc), requisicoes para arquivos CSS , HTML
	@Override
	public void configure(WebSecurity web) throws Exception {
		
	}
	
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
	
//	* --> vai representar qualquer coisa
//	.formLogin() --> O Spring vai gerar um formulario de autenticacao

}
