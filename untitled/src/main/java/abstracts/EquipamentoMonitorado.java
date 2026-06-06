package abstracts;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class EquipamentoMonitorado {

    private String id;
    private String nome;
    private String fabricante;
    private LocalDateTime dataInstalacao;
    private boolean emFuncionamento;
    private int totalAlertas;

    public EquipamentoMonitorado(String id, String nome, String fabricante) {
        this.id = id;
        this.nome = nome;
        this.fabricante = fabricante;
        this.dataInstalacao = LocalDateTime.now();
        this.emFuncionamento = true;
        this.totalAlertas = 0;
    }



    public abstract String realizarLeitura();


    public abstract String gerarRelatorioStatus();


    public void registrarAlerta() {
        this.totalAlertas++;
    }


    public void resetar() {
        this.emFuncionamento = true;
        this.totalAlertas = 0;
        System.out.println("[SISTEMA] Equipamento " + nome + " resetado com sucesso.");
    }


    public String getCabecalhoInfo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return String.format(
                "╔══════════════════════════════════════╗\n" +
                        "  ID        : %s\n" +
                        "  Nome      : %s\n" +
                        "  Fabricante: %s\n" +
                        "  Instalado : %s\n" +
                        "  Status    : %s\n" +
                        "  Alertas   : %d\n" +
                        "╚══════════════════════════════════════╝",
                id, nome, fabricante,
                dataInstalacao.format(fmt),
                emFuncionamento ? "✅ Operacional" : "❌ Inativo",
                totalAlertas
        );
    }

    public String getId()                        { return id; }
    public String getNome()                      { return nome; }
    public String getFabricante()                { return fabricante; }
    public LocalDateTime getDataInstalacao()     { return dataInstalacao; }
    public boolean isEmFuncionamento()           { return emFuncionamento; }
    public int getTotalAlertas()                 { return totalAlertas; }

    public void setNome(String nome)             { this.nome = nome; }
    public void setEmFuncionamento(boolean v)    { this.emFuncionamento = v; }
    public void setTotalAlertas(int v)           { this.totalAlertas = v; }

    @Override
    public String toString() {
        return String.format("[%s] %s (%s) — %s",
                id, nome, fabricante,
                emFuncionamento ? "Operacional" : "Inativo");
    }
}