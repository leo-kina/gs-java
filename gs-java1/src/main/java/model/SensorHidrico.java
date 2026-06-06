package model;


public class SensorHidrico extends SensorAmbiental {

    private double nivelAtualMetros;
    private double nivelCriticoMetros;
    private double nivelAlertaMetros;
    private double vazaoM3porSegundo;
    private String corpoHidrico;
    private String estadoHidrico;

    public SensorHidrico(String id, String nome, String localizacao,
                         String corpoHidrico, double nivelCriticoMetros) {
        super(id, nome, "HydroSense Brasil",
                localizacao, "m", 0.5, nivelCriticoMetros, 1200.0);
        this.corpoHidrico = corpoHidrico;
        this.nivelCriticoMetros = nivelCriticoMetros;
        this.nivelAlertaMetros = nivelCriticoMetros * 0.75;
        this.nivelAtualMetros = 1.5;
        this.vazaoM3porSegundo = 0;
        this.estadoHidrico = "Normal";
    }



    @Override
    public String realizarLeitura() {
        this.nivelAtualMetros = 0.5 + Math.random() * nivelCriticoMetros * 1.1;
        this.vazaoM3porSegundo = Math.random() * 500;
        atualizarEstadoHidrico();

        String base = super.realizarLeitura();
        return base + String.format(
                "\n   └─ Nível: %.2fm | Cota crítica: %.2fm | Vazão: %.1f m³/s | Estado: %s",
                nivelAtualMetros, nivelCriticoMetros, vazaoM3porSegundo, estadoHidrico);
    }


    @Override
    public String gerarRelatorioStatus() {
        return super.gerarRelatorioStatus() +
                String.format(
                        "  Corpo Hídrico : %s\n" +
                                "  Nível Atual   : %.2f m\n" +
                                "  Nível de Model.Alerta: %.2f m\n" +
                                "  Nível Crítico : %.2f m\n" +
                                "  Vazão Atual   : %.1f m³/s\n" +
                                "  Estado Hídrico: %s\n",
                        corpoHidrico, nivelAtualMetros,
                        nivelAlertaMetros, nivelCriticoMetros,
                        vazaoM3porSegundo, estadoHidrico);
    }


    @Override
    public double calcularEficiencia() {
        double base = super.calcularEficiencia();
        double penalidade = switch (estadoHidrico) {
            case "Normal"     -> 0.0;
            case "Atenção"    -> 0.05;
            case "Model.Alerta"     -> 0.15;
            case "Emergência" -> 0.35;
            default           -> 0.0;
        };
        return Math.max(0.1, base - penalidade);
    }



    private void atualizarEstadoHidrico() {
        double pct = nivelAtualMetros / nivelCriticoMetros;
        if (pct < 0.60)       estadoHidrico = "Normal";
        else if (pct < 0.75)  estadoHidrico = "Atenção";
        else if (pct < 0.90)  estadoHidrico = "Model.Alerta";
        else                  estadoHidrico = "Emergência";
    }

    public double getNivelAtualMetros()    { return nivelAtualMetros; }
    public double getNivelCriticoMetros()  { return nivelCriticoMetros; }
    public double getVazaoM3porSegundo()   { return vazaoM3porSegundo; }
    public String getCorpoHidrico()        { return corpoHidrico; }
    public String getEstadoHidrico()       { return estadoHidrico; }
}