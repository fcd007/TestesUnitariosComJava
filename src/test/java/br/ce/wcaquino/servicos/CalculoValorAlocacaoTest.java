package br.ce.wcaquino.servicos;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.entidades.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.entidades.exceptions.LocadoraException;

@RunWith(Parameterized.class)
public class CalculoValorAlocacaoTest {

	private LocacaoService locacaoService;

	@Parameter
	public List<Filme> filmes;

	@Parameter(value = 1)
	public Double valorAlocacao;

	private static Filme filme1 = new Filme("De volta para o futuro 1", 2, 4.0);
	private static Filme filme2 = new Filme("De volta para o futuro 2", 2, 4.0);
	private static Filme filme3 = new Filme("De volta para o futuro 3", 2, 4.0);
	private static Filme filme4 = new Filme("De volta para o futuro 3", 2, 4.0);
	private static Filme filme5 = new Filme("De volta para o futuro 3", 2, 4.0);
	private static Filme filme6 = new Filme("De volta para o futuro 3", 2, 4.0);
	private static Filme filme7 = new Filme("De volta para o futuro 3", 2, 4.0);

	@Before
	public void setup() {
		locacaoService = new LocacaoService();
	}

	@Parameters
	public static Collection<Object[]> getParametros() {
		return Arrays.asList(new Object[][] { { Arrays.asList(filme1, filme2, filme3), 11.0 },
				{ Arrays.asList(filme1, filme2, filme3, filme4), 13.0 },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5), 14.0 },
				{ Arrays.asList(filme1, filme2, filme3, filme4, filme5, filme6), 14.0 }, });
	}

	/**
	 * Data Driven Test
	 * 
	 * @throws FilmesSemEstoqueException
	 * @throws LocadoraException
	 */

	@Test
	public void calcularValorAlocacaoComBaseNoDesconto() throws FilmesSemEstoqueException, LocadoraException {
		// cenario 4+4+3+2+1
		Usuario usuario = new Usuario("Pedro Lessa");

		// Acao
		Locacao resultado = locacaoService.alugarFilme(usuario, filmes);

		// Verificacao
		assertThat(resultado.getValor(), is(valorAlocacao));
	}

}
