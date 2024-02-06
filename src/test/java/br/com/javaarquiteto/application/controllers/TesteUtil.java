package br.com.javaarquiteto.application.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;

import br.com.javaarquiteto.domain.enums.EstadoEnum;

public class TesteUtil {
	
	

	

	private static final List<String> BAIRROS = List.of("Santa Maria","Rondonopolis","Micaricia","Patopolis");

	public static String getValorRandomico(int tamanho) {
		  StringBuilder sb = new StringBuilder(tamanho);
	        Random random = new Random();
	        int posicao = tamanho - 1;

	        for (int i = 0; i < tamanho; i++) {	            
	            sb.append(random.nextInt(posicao));
	        }

	        return sb.toString();
	}

	public static LocalDate getDataNascimentoRamdomica(int anoInicial , int anoFinal ) {
		Random random = new Random();

		// Gera um ano aleatório entre anoInicial e anoFinal
		int ano = random.nextInt(anoFinal - anoInicial + 1) + anoInicial;

		// Gera um mês aleatório
		int mes = random.nextInt(11) + 1; // meses são de 1 a 12

		// Gera um dia aleatório, considerando o mês e o ano
		 int dia = random.nextInt(Month.of(mes).length(isAnoBissexto(ano))) + 1;

		return LocalDate.of(ano, mes, dia);
	}
	
    public static boolean isAnoBissexto(int ano) {
        return ano % 4 == 0 && (ano % 100 != 0 || ano % 400 == 0);
    }

	public static String getBairro() {		  

	        Random random = new Random();
	        int indiceAleatorio = random.nextInt(BAIRROS.size());

	        return BAIRROS.get(indiceAleatorio);
	}

	public static String getUFRamdomica() {
	    Random random = new Random();
        int indiceAleatorio = random.nextInt(EstadoEnum.values().length);
        EstadoEnum[] enumValues = EstadoEnum.values();

        return enumValues[indiceAleatorio].name();
	}

	public static byte[] getFakeFoto() throws IOException {
		String caminhoImagem = "src/test/resources/patopato.png";
		
		return lerImagemComoBytes(caminhoImagem);	
		
	}

	private static byte[] lerImagemComoBytes(String caminhoImagem) throws IOException {
		Path path = Paths.get(caminhoImagem);   
        return Files.readAllBytes(path);
	}

}
