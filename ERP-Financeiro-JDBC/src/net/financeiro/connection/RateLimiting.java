package net.financeiro.connection;

public class RateLimiting {
    private int tentativas = 0;
    private final int tempoLimite = 300000, maximoTentativas = 5;

    private long tempoPrimeiroLogin = System.currentTimeMillis();

    public boolean permitir(){
        long tempoLoginAtual = System.currentTimeMillis();
        if(tempoLoginAtual - tempoPrimeiroLogin > tempoLimite){
            tempoPrimeiroLogin = tempoLoginAtual;
            tentativas = 0;
        }
        if(tentativas < maximoTentativas){
            tentativas++;
            return true;
        }
        return false;
    }
}
