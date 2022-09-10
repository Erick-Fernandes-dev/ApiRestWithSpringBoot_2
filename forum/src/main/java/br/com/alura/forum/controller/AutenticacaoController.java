package br.com.alura.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.com.alura.forum.controller.form.FormLogin;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {
	
	
	@Autowired
	private AuthenticationManager authManager;
	
	
	@PostMapping
	public ResponseEntity<?> autenticar(@RequestBody @Valid FormLogin form) {
		//gerando token
		UsernamePasswordAuthenticationToken dadosLogin = form.converter();
		
		
		try {
			
			Authentication authenticate = authManager.authenticate(dadosLogin);
			return ResponseEntity.ok().build();
		} catch (AuthenticationException e) {
			return ResponseEntity.badRequest().build();

		}
		
//		System.out.println(form.getEmail());
//		System.out.println(form.getSenha());
		
		
	}

}
