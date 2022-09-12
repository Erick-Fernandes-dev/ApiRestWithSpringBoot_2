package br.com.alura.forum.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import br.com.alura.forum.repository.UsuarioRepository;

// Todas as configurações do projeto

@EnableWebSecurity//habilitar o Spring security, tudo habilitado
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {
	
	//obs: em configuracoes de service é possivel tmb fazer injecao de dependencias
	
	@Autowired
	private AutenticacaoService autenticacaoService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return super.authenticationManager();
	}
	
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
		.antMatchers(HttpMethod.POST, "/auth").permitAll()
		.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()//n pode permitir o actuator
		//qualquer outra requisicao tem que está autenticado
		.anyRequest().authenticated()
//		.and().formLogin();
		//desabilitando o csrf, para evitar ataques hackers
		.and().csrf().disable()
//		Avisa ao Spring que n é para criar sessao, logo, a autenticacao vai ser de maneira Stateless
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		.and()
		//rode o meu filtro para pegar o token
		.addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
		
	}
	
	// Configuracoes de recursos estaticos(JS, CSS, images, etc), requisicoes para arquivos CSS , HTML
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers(
				"/**.html",
				"/v2/api-docs",
				"/webjars/**",
				"/configuration/**",
				"/swagger-resources/**");
	}
	
	
//	public static void main(String[] args) {
//		System.out.println(new BCryptPasswordEncoder().encode("123456"));
//	}
	
//	* --> vai representar qualquer coisa
//	.formLogin() --> O Spring vai gerar um formulario de autenticacao
//	.csrf() --> Cross-site Request Forery - é uma das vulnerabilidades mais conhecidas e perigosas em aplicações
//	web.

}
