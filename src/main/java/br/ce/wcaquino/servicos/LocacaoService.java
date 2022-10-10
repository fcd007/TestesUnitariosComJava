package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.entidades.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.entidades.exceptions.LocadoraException;
import br.ce.wcaquino.utils.DataUtils;

public class LocacaoService {

	private final Double DESCONTO_75_PORCENTO = 0.75;
	private final Double DESCONTO_50_PORCENTO = 0.50;
	private final Double DESCONTO_25_PORCENTO = 0.25;

	public Locacao alugarFilme(Usuario usuario, List<Filme> listaFilmes)
			throws FilmesSemEstoqueException, LocadoraException {

		if (usuario == null) {
			throw new LocadoraException("Usuario vazio");
		}

		if (listaFilmes == null || listaFilmes.isEmpty()) {
			throw new LocadoraException("Lista de filmes está vazia");
		}

		for (Filme filme : listaFilmes) {
			if (filme.getEstoque() == 0) {
				throw new FilmesSemEstoqueException();
			}
		}

		Locacao locacao = new Locacao();
		Double valorPrecoLocacaoTotal = 0.0;

		locacao.setFilmes(listaFilmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());

		int indice = 0;
		for (Filme filme : listaFilmes) {
			switch (indice) {
			case 2:
				valorPrecoLocacaoTotal += (filme.getPrecoLocacao() * DESCONTO_75_PORCENTO);
				break;
			case 3:
				valorPrecoLocacaoTotal += (filme.getPrecoLocacao() * DESCONTO_50_PORCENTO);
				break;
			case 4:
				valorPrecoLocacaoTotal += (filme.getPrecoLocacao() * DESCONTO_25_PORCENTO);
				break;
			default:
				valorPrecoLocacaoTotal += filme.getPrecoLocacao();
				break;
			}

			indice++;
		}

		locacao.setValor(valorPrecoLocacaoTotal);

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);

		if (DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY)) {
			dataEntrega = adicionarDias(dataEntrega, 1);
		}

		locacao.setDataRetorno(dataEntrega);

		return locacao;
	}
}