package br.ce.wcaquino.servicos;

import br.ce.wcaquino.entidades.exceptions.NaoPodeDividirPorZeroException;

public class Calculadora {

	public int somar(int numero1, int numero2) {
		return numero1 + numero2;
	}

	public int sutrair(int numero1, int numero2) {
		return numero1 - numero2;
	}

	public int dividir(int numero1, int numero2) throws NaoPodeDividirPorZeroException {
		if (numero2 == 0) {
			throw new NaoPodeDividirPorZeroException();
		}
		return numero1 / numero2;
	}

}
