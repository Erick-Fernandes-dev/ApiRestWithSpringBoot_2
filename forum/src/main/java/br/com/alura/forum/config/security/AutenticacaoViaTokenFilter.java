package br.com.alura.forum.config.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.alura.forum.modelo.Usuario;
import br.com.alura.forum.repository.UsuarioRepository;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {
	
	private TokenService tokenService;
	private UsuarioRepository repository;
	
	public AutenticacaoViaTokenFilter(TokenService tokenService, UsuarioRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
		
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String token = recuperartoken(request);
		
		boolean valido = tokenService.isTokenValido(token);
		
//		System.out.println(token);
//		System.out.println(valido);
		
		if (valido) {
			autenticacaoCliente(token);
		}
		
		filterChain.doFilter(request, response);
		
	}

	//Autenticando cliente via spring security
	private void autenticacaoCliente(String token) {
		
		Long idUsuario = tokenService.getIdUsuario(token);
		Usuario usuario = repository.findById(idUsuario).get();
		
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
	}

//	recuperando token no header
	private String recuperartoken(HttpServletRequest request) {
		 
//		Pegar o valor do meu header que é o Authorization
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
//		vai pegar da posicao 7 em diante
		return token.substring(7, token.length());
	}

}
