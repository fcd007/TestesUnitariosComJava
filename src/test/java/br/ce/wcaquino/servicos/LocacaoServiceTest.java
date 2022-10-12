package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.entidades.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.entidades.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoServiceTest {

	private LocacaoService locacaoService;

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@SuppressWarnings("deprecation")
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setup() {
		locacaoService = new LocacaoService();
	}

	@Test
	public void naoDeveAlugarFilmeSemEstoque() throws Exception {
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		// cenario
		Filme filme1 = new Filme("Matrix", 1, 5.0);
		Filme filme2 = new Filme("Titanic", 1, 4.0);
		Usuario usuario1 = new Usuario("Erick Wendel");
		// criando uma lista vazia de filmes e depois adicionando filmes na lista
		List<Filme> listaFilmes = new ArrayList<Filme>();
		listaFilmes.add(filme1);
		listaFilmes.add(filme2);
		// Acao
		Locacao locacao;
		locacao = locacaoService.alugarFilme(usuario1, listaFilmes);
		// Verificação
		error.checkThat(locacao.getValor(), is(equalTo(9.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));

	}

	@Test(expected = FilmesSemEstoqueException.class)
	public void lancarExceptionAlugarFilmeSemEstoque() throws Exception {
		// cenario
		Usuario usuario = new Usuario("Joao Carlos");
		Filme filme = new Filme("Avatar", 0, 4.0);
		List<Filme> listaFilmes2 = new ArrayList<Filme>();
		listaFilmes2.add(filme);
		// acao
		locacaoService.alugarFilme(usuario, listaFilmes2);
	}

	@Test
	public void naoAlugarFilmeSemUsuario() throws FilmesSemEstoqueException {
		// cenario
		Filme filme1 = new Filme("Uma mente brilhante", 1, 2.0);
		Filme filme2 = new Filme("Interestelar", 1, 3.0);
		List<Filme> listaFilmes2 = new ArrayList<Filme>();
		listaFilmes2.add(filme1);
		listaFilmes2.add(filme2);
		// acao
		try {
			locacaoService.alugarFilme(null, listaFilmes2);
			Assert.fail();
		} catch (LocadoraException e) {
			Assert.assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}

	@Test
	public void lancarExceptionAlugaFilmeSemFilme() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		Usuario usuario1 = new Usuario("Judson Santiago");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Lista de filmes está vazia");

		// acao
		locacaoService.alugarFilme(usuario1, null);
	}

	@Test
	public void deveDevolverNaSegundaAoAlugarSabado() throws FilmesSemEstoqueException, LocadoraException {
		// verificar condicionamente se irá executar o teste ou não
		Assume.assumeTrue(DataUtils.verificarDiaSemana(new Date(), Calendar.SATURDAY));
		// cenario
		Usuario usuario = new Usuario("Claudeilton Dantas");
		List<Filme> filmes = Arrays.asList(new Filme("Hobit", 1, 5.0));

		// acao
		Locacao retorno = locacaoService.alugarFilme(usuario, filmes);

		// verificacao
		// verificar se a devolução é realizada em um dia da semana
		Boolean isSegundaFeira = DataUtils.verificarDiaSemana(retorno.getDataRetorno(), Calendar.MONDAY); // verifica se
																											// é uma
																											// segunda-feira
		Assert.assertTrue(isSegundaFeira);
	}
}