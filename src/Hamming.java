import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Hamming {
	
	static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		
		System.out.println("Envie sua mensagem em bits");
		System.out.println("Exemplo: 100100101");
		String mensagem = input.next();
		char[] mensagemInvertida = mensagem.toCharArray();
		char[] mensagemCerta = new char[mensagem.length()];
		for(int i=0, j = mensagem.length()-1;i<mensagem.length();i++, j--) {
			mensagemCerta[j] = mensagemInvertida[i];
		}
		
		int posicaoBinaria = 1, cnt = 0, cntPosicaoBinaria = 0;
		int[] bit1 = new int[50];
		
		for(int i=0;i<mensagem.length();i++) {
			if((i+1)==posicaoBinaria) {
				posicaoBinaria*=2;
				cntPosicaoBinaria++;
			}
		}
		posicaoBinaria = 1;
		char[] mensagemTransmissao = new char[mensagem.length()+cntPosicaoBinaria];
		for(int i=0, j=0;i<mensagem.length()+cntPosicaoBinaria;i++) {
			if((i+1)==posicaoBinaria) {
				mensagemTransmissao[i] = ' ';
				posicaoBinaria*=2;
			}else{
				mensagemTransmissao[i] = mensagemCerta[j];
				j++;
			}
		}
		
		for(int i=0;i<mensagem.length()+cntPosicaoBinaria;i++) {
			if(mensagemTransmissao[i]=='1') {
				bit1[cnt] = i+1;
				cnt++;
			}
		}
		
		int[][] matriz = new int[cnt][cntPosicaoBinaria];
		int resto;
		
		for(int i=0;i<cnt;i++) {
			int manoseila=cntPosicaoBinaria-1;
			while(bit1[i] !=1 && bit1[i] !=0) {
				resto = bit1[i] % 2;
				bit1[i] /= 2;
				matriz[i][manoseila] = resto;
				manoseila--;
			}
			matriz[i][manoseila] = bit1[i];
			manoseila--;
			if(manoseila>=0) {
				for(int j=manoseila;j<0;j--) {
					matriz[i][j] = 0;
				}
			}
			
		}
		int[] xor = new int[cntPosicaoBinaria];
		for(int i=0;i<cntPosicaoBinaria;i++) {
			int sum = 0;
			for(int j=0;j<cnt;j++) {
				if(matriz[j][i]==1) {
					sum++;
				}
			}
			if(sum%2==0) {
				xor[i]=0;
			}else {
				xor[i]=1;
			}
		}
		int cntBool = cntPosicaoBinaria-1;
		for(int i=0;i<mensagem.length()+cntPosicaoBinaria;i++) {
			if(mensagemTransmissao[i]==' ') {
				if(xor[cntBool]==1) {
					mensagemTransmissao[i]='1';
				}else {
					mensagemTransmissao[i]='0';
				}
				cntBool--;
			}
		}
		System.out.print("Mensagem transmitida: ");
		for(int i=mensagem.length()+cntPosicaoBinaria-1;i>=0;i--) {
			System.out.print(mensagemTransmissao[i]);
		}
		System.out.println();
		System.out.println("Mensagem recebida");
		String mensagemRecebida = input.next();
		char[] mensagemRInvertida = mensagemRecebida.toCharArray();
		char[] mensagemRCerta = new char[mensagemRecebida.length()];
		for(int i=0, j = mensagemRecebida.length()-1;i<mensagemRecebida.length();i++, j--) {
			mensagemRCerta[j] = mensagemRInvertida[i];
		}

		int cntRecebido = 0;
		int[] bit1Recebido = new int[50];
		for(int i=0;i<mensagemRecebida.length();i++) {
			if(mensagemRCerta[i]=='1') {
				bit1Recebido[cntRecebido] = i+1;
				cntRecebido++;
			}
		}
		
		int[][] matrizRecebido = new int[cntRecebido][cntPosicaoBinaria];
		int restoRecebido;
		
		for(int i=0;i<cntRecebido;i++) {
			int manoseila=cntPosicaoBinaria-1;
			while(bit1Recebido[i] !=1 && bit1Recebido[i] !=0) {
				restoRecebido = bit1Recebido[i] % 2;
				bit1Recebido[i] /= 2;
				matrizRecebido[i][manoseila] = restoRecebido;
				manoseila--;
			}
			matrizRecebido[i][manoseila] = bit1Recebido[i];
			manoseila--;
			if(manoseila>=0) {
				for(int j=manoseila;j<0;j--) {
					matrizRecebido[i][j] = 0;
				}
			}
			
		}
		int[] xorRecebido = new int[cntPosicaoBinaria];
		for(int i=0;i<cntPosicaoBinaria;i++) {
			int sum = 0;
			for(int j=0;j<cntRecebido;j++) {
				if(matrizRecebido[j][i]==1) {
					sum++;
				}
			}
			if(sum%2==0) {
				xorRecebido[i]=0;
			}else {
				xorRecebido[i]=1;
			}
		}
		int[] xorCerto = new int[cntPosicaoBinaria];
		for(int i=0, j=cntPosicaoBinaria-1;i<cntPosicaoBinaria;i++, j--) {
			xorCerto[j] = xorRecebido[i];
		}
		int soma = 0;
		for(int i=0;i<cntPosicaoBinaria;i++) {
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
