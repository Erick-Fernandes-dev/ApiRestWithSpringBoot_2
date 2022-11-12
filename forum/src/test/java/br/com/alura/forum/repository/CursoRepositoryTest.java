package br.com.alura.forum.repository;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.alura.forum.modelo.Curso;

@RunWith(SpringRunner.class)
@DataJpaTest//especificação para fazer testes de repository
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)//configuração de teste de algum banco de dados
@ActiveProfiles("test")//vai rodar um determinado .properties, ou seja, vai rodar o meu application-test
public class CursoRepositoryTest {
	
	@Autowired
	private CursoRepository repository;
	
	@Autowired
	private TestEntityManager em;

	@Test
	public void deveriaCarregarUmCursoAoBuscarPeloNome() {
		
		String nomeCurso = "HTML 5";
		
		Curso html5 = new Curso();
		
		html5.setNome(nomeCurso);
		html5.setCategoria("Programação");
		
		em.persist(html5);//persistir/salvar no banco
		
		Curso curso = repository.findByNome(nomeCurso);
		
		Assert.assertNotNull(curso);//não pode ser nulo
		Assert.assertEquals(nomeCurso, curso.getNome());//se é igual
		
	}
	
	@Test
	public void naoDeveriaCarregarUmCursoAoBuscarCujoONomeNaoEstejaCadastrado() {
		
		String nomeCurso = "JPA";
		Curso curso = repository.findByNome(nomeCurso);
		Assert.assertNull(curso);//pode ser nulo
		
	}
//replace = AutoConfigureTestDatabase.Replace.NONE --> não substituir as configurações de um banco em um banco de dados em memoria
//não substitua o meu banco de dados pelo H2
	
// TestEntityManager - Alternativa ao EntityManager para uso em testes JPA. Fornece um subconjunto de métodos EntityManager que são 
//	úteis para testes, bem como métodos auxiliares para tarefas de teste comuns, como persistir/liberar/localizar.
}

















