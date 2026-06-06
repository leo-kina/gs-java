package interfaces;


public interface Notificavel {


    void enviarNotificacao(String mensagem);

    boolean possuiAlertasPendentes();

    int getNivelPrioridade();
}