package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.isMesmaData;
import static br.ce.wcaquino.utils.DataUtils.obterDataComDiferencaDias;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.entidades.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.entidades.exceptions.LocadoraException;

public class LocacaoServiceTest {

	@Rule
	public ErrorCollector error = new ErrorCollector();

	@SuppressWarnings("deprecation")
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void teste() throws Exception {
		// cenario
		LocacaoService service = new LocacaoService();
		Filme filme1 = new Filme("Matrix", 1, 7.0);
		Usuario usuario1 = new Usuario("Erick Wendel");

		// Acao
		Locacao locacao;

		locacao = service.alugarFilme(usuario1, filme1);
		// Verificação
		error.checkThat(locacao.getValor(), is(equalTo(7.0)));
		error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
		error.checkThat(isMesmaData(locacao.getDataRetorno(), obterDataComDiferencaDias(1)), is(true));
	}

	@Test(expected = FilmesSemEstoqueException.class)
	public void testLocacaoFilmeSemEstoque() throws Exception {
		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario = new Usuario("Joao Carlos");
		Filme filme = new Filme("Filme3", 0, 4.0);
		// acao
		service.alugarFilme(usuario, filme);
	}

	@Test
	public void testLocacaoUsuarioVazio() throws FilmesSemEstoqueException {
		// cenario
		LocacaoService service = new LocacaoService();
		Filme filme = new Filme("Filme4", 1, 4.8);
		// acao
		try {
			service.alugarFilme(null, filme);
			Assert.fail();
		} catch (LocadoraException e) {
			assertThat(e.getMessage(), is("Usuario vazio"));
		}
	}

	@Test
	public void testLocacaoFilmeVazio() throws FilmesSemEstoqueException, LocadoraException {
		// cenario
		LocacaoService service = new LocacaoService();
		Usuario usuario1 = new Usuario("Erick Wendel");

		exception.expect(LocadoraException.class);
		exception.expectMessage("Filme Vazio");
		
		// acao
		service.alugarFilme(usuario1, null);
	}
}