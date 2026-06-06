package model;

import abstracts.EquipamentoMonitorado;
import interfaces.Notificavel;
import interfaces.Rastreavel;
import interfaces.Calculavel;

public class SensorAmbiental extends EquipamentoMonitorado
        implements Rastreavel, Notificavel, Calculavel {

    private String localizacao;
    private double leituraAtual;
    private double limiteMaximo;
    private double limiteMinimo;
    private String unidadeMedida;
    private boolean alertaPendente;
    private int nivelPrioridade;
    private double custoMensalBRL;


    public SensorAmbiental(String id, String nome, String fabricante,
                           String localizacao, String unidadeMedida,
                           double limiteMinimo, double limiteMaximo,
                           double custoMensalBRL) {
        super(id, nome, fabricante);
        this.localizacao = localizacao;
        this.unidadeMedida = unidadeMedida;
        this.limiteMinimo = limiteMinimo;
        this.limiteMaximo = limiteMaximo;
        this.custoMensalBRL = custoMensalBRL;
        this.leituraAtual = 0.0;
        this.alertaPendente = false;
        this.nivelPrioridade = 1;
    }



    @Override
    public String realizarLeitura() {
        double amplitude = limiteMaximo - limiteMinimo;
        this.leituraAtual = limiteMinimo + Math.random() * amplitude;

        verificarLimites();

        return String.format("📡 Sensor [%s] | Local: %s | Leitura: %.2f %s | Status: %s",
                getId(), localizacao, leituraAtual, unidadeMedida,
                alertaPendente ? "⚠️  ALERTA" : "✅ Normal");
    }

    @Override
    public String gerarRelatorioStatus() {
        return String.format(
                "\n%s\n" +
                        "  Localização  : %s\n" +
                        "  Última Leitura: %.2f %s\n" +
                        "  Limite Mín.  : %.2f %s\n" +
                        "  Limite Máx.  : %.2f %s\n" +
                        "  Eficiência   : %.1f%%\n",
                getCabecalhoInfo(),
                localizacao, leituraAtual, unidadeMedida,
                limiteMinimo, unidadeMedida,
                limiteMaximo, unidadeMedida,
                calcularEficiencia() * 100
        );
    }


    @Override
    public String obterLocalizacao() {
        return localizacao;
    }

    @Override
    public void atualizarLocalizacao(String novaLocalizacao) {
        System.out.printf("📍 Sensor [%s] relocado: %s → %s%n",
                getId(), this.localizacao, novaLocalizacao);
        this.localizacao = novaLocalizacao;
    }

    @Override
    public boolean estaAtivo() {
        return isEmFuncionamento();
    }


    @Override
    public void enviarNotificacao(String mensagem) {
        registrarAlerta();
        System.out.printf("🔔 [ALERTA %s] Sensor %s (%s): %s%n",
                getNomePrioridade(), getId(), getNome(), mensagem);
    }

    @Override
    public boolean possuiAlertasPendentes() {
        return alertaPendente;
    }

    @Override
    public int getNivelPrioridade() {
        return nivelPrioridade;
    }


    @Override
    public double calcularEficiencia() {
        if (!isEmFuncionamento()) return 0.0;
        double base = 1.0 - (getTotalAlertas() * 0.05);
        return Math.max(0.1, Math.min(1.0, base));
    }

    @Override
    public double calcularCustoOperacional() {
        double fatorAlertas = 1.0 + (getTotalAlertas() / 10) * 0.20;
        return custoMensalBRL * fatorAlertas;
    }

    private void verificarLimites() {
        if (leituraAtual > limiteMaximo) {
            alertaPendente = true;
            nivelPrioridade = 3;
            enviarNotificacao(String.format(
                    "Leitura ACIMA do limite! %.2f %s (máx: %.2f)",
                    leituraAtual, unidadeMedida, limiteMaximo));
        } else if (leituraAtual < limiteMinimo) {
            alertaPendente = true;
            nivelPrioridade = 2;
            enviarNotificacao(String.format(
                    "Leitura ABAIXO do limite! %.2f %s (mín: %.2f)",
                    leituraAtual, unidadeMedida, limiteMinimo));
        } else {
            alertaPendente = false;
            nivelPrioridade = 1;
        }
    }

    private String getNomePrioridade() {
        return switch (nivelPrioridade) {
            case 1 -> "BAIXO";
            case 2 -> "MÉDIO";
            case 3 -> "CRÍTICO";
            default -> "?";
        };
    }

    public double getLeituraAtual()  { return leituraAtual; }
    public String getLocalizacao()   { return localizacao; }
    public double getLimiteMaximo()  { return limiteMaximo; }
    public double getLimiteMinimo()  { return limiteMinimo; }
    public String getUnidadeMedida() { return unidadeMedida; }

    public void setLimiteMaximo(double v) { this.limiteMaximo = v; }
    public void setLimiteMinimo(double v) { this.limiteMinimo = v; }
}