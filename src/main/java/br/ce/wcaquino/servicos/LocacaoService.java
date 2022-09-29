package br.ce.wcaquino.servicos;

import static br.ce.wcaquino.utils.DataUtils.adicionarDias;

import java.util.Date;
import java.util.List;

import br.ce.wcaquino.entidades.Filme;
import br.ce.wcaquino.entidades.Locacao;
import br.ce.wcaquino.entidades.Usuario;
import br.ce.wcaquino.entidades.exceptions.FilmesSemEstoqueException;
import br.ce.wcaquino.entidades.exceptions.LocadoraException;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> listaFilmes) throws FilmesSemEstoqueException, LocadoraException {

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
		
		for (Filme filme : listaFilmes) {
			valorPrecoLocacaoTotal += filme.getPrecoLocacao();
		}
		locacao.setValor(valorPrecoLocacaoTotal);	

		// Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);
		// Salvando a locacao...
		// TODO adicionar método para salvar

		return locacao;
	}
}