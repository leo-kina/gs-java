package interfaces;


public interface Rastreavel {


    String obterLocalizacao();

    void atualizarLocalizacao(String novaLocalizacao);

    boolean estaAtivo();
}