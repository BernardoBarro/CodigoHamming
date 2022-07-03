import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Hamming {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Envie sua mensagem em bits");
		System.out.println("Exemplo: 100100101");
		String mensagemString = input.next();
		char[] mensagemInvertida = mensagemString.toCharArray();
		char[] mensagem = new char[mensagemString.length()];
		for(int i=0, j = mensagemString.length()-1;i<mensagemString.length();i++, j--) {
			mensagem[j] = mensagemInvertida[i];
		}
		
		int posicaoBinaria = 1, contadorPosicoes = 0, contadorPosicaoBinaria = 0;
		int[] numeroDaPosicao = new int[50];
		
		for(int i=0;i<mensagemString.length();i++) {
			if((i+1)==posicaoBinaria) {
				posicaoBinaria*=2;
				contadorPosicaoBinaria++;
			}
		}
		posicaoBinaria = 1;
		char[] mensagemTransmissao = new char[mensagemString.length()+contadorPosicaoBinaria];
		for(int i=0, j=0;i<mensagemString.length()+contadorPosicaoBinaria;i++) {
			if((i+1)==posicaoBinaria) {
				mensagemTransmissao[i] = ' ';
				posicaoBinaria*=2;
			}else{
				mensagemTransmissao[i] = mensagem[j];
				j++;
			}
		}
		
		for(int i=0;i<mensagemString.length()+contadorPosicaoBinaria;i++) {
			if(mensagemTransmissao[i]=='1') {
				numeroDaPosicao[contadorPosicoes] = i+1;
				contadorPosicoes++;
			}
		}
		
		int[][] tabelaHamming = new int[contadorPosicoes][contadorPosicaoBinaria];
		int resto;
		
		for(int i=0;i<contadorPosicoes;i++) {
			int indicador=contadorPosicaoBinaria-1;
			while(numeroDaPosicao[i] !=1 && numeroDaPosicao[i] !=0) {
				resto = numeroDaPosicao[i] % 2;
				numeroDaPosicao[i] /= 2;
				tabelaHamming[i][indicador] = resto;
				indicador--;
			}
			tabelaHamming[i][indicador] = numeroDaPosicao[i];
			indicador--;
			if(indicador>=0) {
				for(int j=indicador;j<0;j--) {
					tabelaHamming[i][j] = 0;
				}
			}
			
		}
		int[] xor = new int[contadorPosicaoBinaria];
		for(int i=0;i<contadorPosicaoBinaria;i++) {
			int sum = 0;
			for(int j=0;j<contadorPosicoes;j++) {
				if(tabelaHamming[j][i]==1) {
					sum++;
				}
			}
			if(sum%2==0) {
				xor[i]=0;
			}else {
				xor[i]=1;
			}
		}
		int indicadorDoXor = contadorPosicaoBinaria-1;
		for(int i=0;i<mensagemString.length()+contadorPosicaoBinaria;i++) {
			if(mensagemTransmissao[i]==' ') {
				if(xor[indicadorDoXor]==1) {
					mensagemTransmissao[i]='1';
				}else {
					mensagemTransmissao[i]='0';
				}
				indicadorDoXor--;
			}
		}
		System.out.print("Mensagem transmitida: ");
		for(int i=mensagemString.length()+contadorPosicaoBinaria-1;i>=0;i--) {
			System.out.print(mensagemTransmissao[i]);
		}
		System.out.println();
		System.out.println("Mensagem recebida");
		String mensagemRecebidaString = input.next();
		char[] mensagemRInvertida = mensagemRecebidaString.toCharArray();
		char[] mensagemRecebida = new char[mensagemRecebidaString.length()];
		for(int i=0, j = mensagemRecebidaString.length()-1;i<mensagemRecebidaString.length();i++, j--) {
			mensagemRecebida[j] = mensagemRInvertida[i];
		}

		int contadorPosicoesRecebido = 0;
		int[] numeroDaPosicaoRecebido = new int[50];
		for(int i=0;i<mensagemRecebidaString.length();i++) {
			if(mensagemRecebida[i]=='1') {
				numeroDaPosicaoRecebido[contadorPosicoesRecebido] = i+1;
				contadorPosicoesRecebido++;
			}
		}
		
		int[][] tabelaHammingRecebido = new int[contadorPosicoesRecebido][contadorPosicaoBinaria];
		int restoRecebido;
		
		for(int i=0;i<contadorPosicoesRecebido;i++) {
			int indicador=contadorPosicaoBinaria-1;
			while(numeroDaPosicaoRecebido[i] !=1 && numeroDaPosicaoRecebido[i] !=0) {
				restoRecebido = numeroDaPosicaoRecebido[i] % 2;
				numeroDaPosicaoRecebido[i] /= 2;
				tabelaHammingRecebido[i][indicador] = restoRecebido;
				indicador--;
			}
			tabelaHammingRecebido[i][indicador] = numeroDaPosicaoRecebido[i];
			indicador--;
			if(indicador>=0) {
				for(int j=indicador;j<0;j--) {
					tabelaHammingRecebido[i][j] = 0;
				}
			}
			
		}
		int[] xorRecebido = new int[contadorPosicaoBinaria];
		for(int i=0;i<contadorPosicaoBinaria;i++) {
			int sum = 0;
			for(int j=0;j<contadorPosicoesRecebido;j++) {
				if(tabelaHammingRecebido[j][i]==1) {
					sum++;
				}
			}
			if(sum%2==0) {
				xorRecebido[i]=0;
			}else {
				xorRecebido[i]=1;
			}
		}
		int[] xorCerto = new int[contadorPosicaoBinaria];
		for(int i=0, j=contadorPosicaoBinaria-1;i<contadorPosicaoBinaria;i++, j--) {
			xorCerto[j] = xorRecebido[i];
		}
		int soma = 0;
		for(int i=0;i<contadorPosicaoBinaria;i++) {
			if(xorCerto[i]==1) {
				soma+=Math.pow(2, i);
			}
		}
		if(soma==0) {
			System.out.println("Mensagem recebida sem erros!");
		}else {
			System.out.println("Erro no bit " + soma + "!!!");
		}
		System.out.println();
	}
}
