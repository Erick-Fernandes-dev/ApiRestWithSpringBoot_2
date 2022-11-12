package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import br.com.alura.forum.config.security.TokenService;
import br.com.alura.forum.controller.dto.TokenDto;
import br.com.alura.forum.controller.form.FormLogin;

@RestController
@RequestMapping("/auth")
// 				  Carregando vários profiles
@Profile(value = {"prod", "test"})
public class AutenticacaoController {
	
	
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private TokenService tokenService;
	
	
	@PostMapping
	public ResponseEntity<TokenDto> autenticar(@RequestBody @Valid FormLogin form) {
		//gerando token
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		
		try {
			
			Authentication authentication = authManager.authenticate(dadosLogin);
			String token = tokenService.gerarToken(authentication);
			
			System.out.println(token);
			
			return ResponseEntity.ok(new TokenDto(token, "Bearer"));
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();

		}
		
//		System.out.println(form.getEmail());
//		System.out.println(form.getSenha());
		
		
	}
	
// Bearer --> token de acesso temporario	

}
