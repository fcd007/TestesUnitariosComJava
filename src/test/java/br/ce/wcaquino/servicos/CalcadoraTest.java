package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.exceptions.NaoPodeDividirPorZeroException;

public class CalcadoraTest {

	private Calculadora calculadora;

	@Test
	public void somarDoisNumeros() {
		// cenario
		int numero1 = 2;
		int numero2 = 3;
		calculadora = new Calculadora();

		// acao
		int somarDoisNumeros = calculadora.somar(numero1, numero2);

		// verificao
		Assert.assertEquals(5, somarDoisNumeros);
	}

	@Test
	public void subtracaoEntreDoisNumeros() {
		// cenario
		int numero1 = 22;
		int numero2 = 9;
		calculadora = new Calculadora();

		// acao
		int resultadoSubtracaoDeDoisNumeros = calculadora.sutrair(numero1, numero2);

		// verificacao
		Assert.assertEquals(13, resultadoSubtracaoDeDoisNumeros);
	}

	@Test
	public void dividirDoisNumeros() throws NaoPodeDividirPorZeroException {
		// cenario
		int numero1 = 20;
		int numero2 = 2;
		calculadora = new Calculadora();

		// acao
		int resultadoDaDivisaoDoisNumeros = calculadora.dividir(numero1, numero2);

		// verificacao
		Assert.assertEquals(10, resultadoDaDivisaoDoisNumeros);
	}

	@Test(expected = NaoPodeDividirPorZeroException.class)
	public void lancarExcecaodivisaoPorZero() throws NaoPodeDividirPorZeroException {
		// acao
		int numero1 = 10;
		int numero2 = 0;
		calculadora = new Calculadora();

		// cenario
		calculadora.dividir(numero1, numero2);

		// verificacao
		Assert.assertEquals(numero1, numero2);
	}

}
