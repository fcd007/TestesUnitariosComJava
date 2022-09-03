package br.ce.wcaquino.servicos;

import org.junit.Assert;
import org.junit.Test;

import br.ce.wcaquino.entidades.Usuario;

public class AssertTest {
	

	@Test
	public void test() {
		Assert.assertTrue(true); //recebe um valor booleano, evite usar negaÃ§Ãµes para validaÃ§Ãµes
		Assert.assertFalse(false); //recebe um valor booleano, evite usar negaÃ§Ãµes para validaÃ§Ãµes
		//usando equals
		Assert.assertEquals(1, 1); //para cada tipo de valor temos um assertEquals
		//trabalhar com float e double é diferente
		Assert.assertEquals(0.51234, 0.512, 0.01); //devemos usar um delta de comparação
		Assert.assertEquals(Math.PI, 3.14, 0.01); //devemos usar um delta de comparação
		
		//aplicando autobox e anbox
		int i = 5;
		Integer i2 = 5;
		//devemos utilizar o mesmo tipo de dado para comparação, se preciso devemos converter usando a maneira correta
		Assert.assertEquals(i, i2.intValue()); //estamos convertendo um objeto Integer para tipo primitivo int
		Assert.assertEquals(Integer.valueOf(i), i2); //estamos convertendo um tipo primitivo int para um objeto Integer
		
		//as Strings são comparadas via equals
		Assert.assertEquals("Bola", "Bola");
		Assert.assertNotEquals("Bola", "Vela");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola"));
		Assert.assertTrue("bola".startsWith("bo"));
		
		//igualdade de objetos 
		Usuario usuario1 = new Usuario("João Carlos");
		Usuario usuario2 = new Usuario("João Carlos");
		Usuario usuario3 = usuario2; // os dois objetos apontam para a mesma instância do objeto
		Assert.assertEquals(usuario1, usuario2);
		
		//comparando se os dois objetos são da mesma instância
		Assert.assertSame(usuario2, usuario3);
		Assert.assertNotSame(usuario2, usuario1);
		
		//verificar se o objeto está nulo
		Assert.assertTrue(usuario3 == null);
		Assert.assertNull(usuario3);
		
		//Observação, para cada tipo de teste temos um tipo de verificação inverso
		//Podemos adicionar uma mensagem ou String para o teste
		Assert.assertEquals("Objeto nulo", 1, 2);
	}
}
