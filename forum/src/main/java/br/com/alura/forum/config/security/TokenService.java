package br.com.alura.forum.config.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.alura.forum.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
	
	@Value("${forum.jwt.expiration}")//server para injetar parametros do applicartion.properties
	private String expiration;
	
	@Value("${forum.jwt.secret}")
	private String secret;

	//Metodo para gerar token
	public String gerarToken(Authentication authentication) {
		
		Usuario logado = (Usuario) authentication.getPrincipal();
		Date hoje = new Date();
		
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		return Jwts.builder()
				.setIssuer("API do Fórum da Alura")//assunto do token
				.setSubject(logado.getId().toString())//quem e o usuario que vai gerar o token
				.setIssuedAt(hoje)//data que foi gerado o token
				.setExpiration(dataExpiracao)//tempo de expiracao do token
				.signWith(SignatureAlgorithm.HS256, secret)//gerar criptografia de senha
				.compact();//converter tudo em uma string;
				
	}

	//validar se o token é válido ou não
	public boolean isTokenValido(String token) {
		
		try {
			//vai descriptografar e verificar se esta ok, ou seja, ele pega o token e valida
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
			
		} catch (Exception e) {
			return false;
		}
		
		
	}

	public Long getIdUsuario(String token) {
//		Vai pegar o corpo da minha api de um determinado id
		Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
		return Long.parseLong(claims.getSubject());
	}

}
