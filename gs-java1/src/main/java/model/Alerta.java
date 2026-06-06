package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Alerta {

    public enum Tipo { QUALIDADE_AR, NIVEL_HIDRICO, TEMPERATURA, RUIDO, GERAL }
    public enum Status { PENDENTE, RECONHECIDO, RESOLVIDO }

    private static int contadorGlobal = 0;

    private final int numero;
    private final String idSensor;
    private final Tipo tipo;
    private final String mensagem;
    private final int prioridade;
    private final LocalDateTime timestamp;
    private Status status;
    private String observacao;

    public Alerta(String idSensor, Tipo tipo, String mensagem, int prioridade) {
        this.numero     = ++contadorGlobal;
        this.idSensor   = idSensor;
        this.tipo       = tipo;
        this.mensagem   = mensagem;
        this.prioridade = prioridade;
        this.timestamp  = LocalDateTime.now();
        this.status     = Status.PENDENTE;
        this.observacao = "";
    }

    public void reconhecer(String obs) {
        this.status = Status.RECONHECIDO;
        this.observacao = obs;
        System.out.printf("👁  Model.Alerta #%d reconhecido: %s%n", numero, obs);
    }

    public void resolver(String obs) {
        this.status = Status.RESOLVIDO;
        this.observacao = obs;
        System.out.printf("✅ Model.Alerta #%d resolvido: %s%n", numero, obs);
    }

    public String getPrioridadeLabel() {
        return switch (prioridade) {
            case 1 -> "🟢 BAIXO";
            case 2 -> "🟡 MÉDIO";
            case 3 -> "🔴 CRÍTICO";
            default -> "⚪ ?";
        };
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return String.format(
                "  #%04d | %s | Sensor: %-8s | Tipo: %-15s | %s\n" +
                        "        Msg: %s\n" +
                        "        Status: %s%s",
                numero, timestamp.format(fmt), idSensor,
                tipo.name(), getPrioridadeLabel(),
                mensagem, status.name(),
                observacao.isEmpty() ? "" : " | Obs: " + observacao
        );
    }

    // Getters
    public int getNumero()         { return numero; }
    public String getIdSensor()    { return idSensor; }
    public Tipo getTipo()          { return tipo; }
    public String getMensagem()    { return mensagem; }
    public int getPrioridade()     { return prioridade; }
    public LocalDateTime getTs()   { return timestamp; }
    public Status getStatus()      { return status; }
}