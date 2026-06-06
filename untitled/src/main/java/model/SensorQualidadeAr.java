package model;


public class SensorQualidadeAr extends SensorAmbiental {

    private double nivelPM25;
    private double nivelCO2;
    private double nivelO3;
    private String classificacaoIQAR;

    public SensorQualidadeAr(String id, String nome, String localizacao) {
        super(id, nome, "SmartAir Sensors Ltda.",
                localizacao, "IQA", 0, 100, 850.0);
        this.nivelPM25 = 0;
        this.nivelCO2  = 400;
        this.nivelO3   = 0;
        this.classificacaoIQAR = "Não mensurado";
    }



    @Override
    public String realizarLeitura() {
        this.nivelPM25 = Math.random() * 150;
        this.nivelCO2  = 380 + Math.random() * 620;
        this.nivelO3   = Math.random() * 200;

        atualizarClassificacaoIQAR();

        String base = super.realizarLeitura();
        return base + String.format(
                "\n   └─ PM2.5: %.1f µg/m³ | CO₂: %.0f ppm | O₃: %.1f µg/m³ | IQAR: %s",
                nivelPM25, nivelCO2, nivelO3, classificacaoIQAR);
    }



    @Override
    public String gerarRelatorioStatus() {
        return super.gerarRelatorioStatus() +
                String.format(
                        "  PM2.5        : %.1f µg/m³\n" +
                                "  CO₂          : %.0f ppm\n" +
                                "  Ozônio (O₃)  : %.1f µg/m³\n" +
                                "  Class. IQAR  : %s\n",
                        nivelPM25, nivelCO2, nivelO3, classificacaoIQAR);
    }


    @Override
    public double calcularEficiencia() {
        double base = super.calcularEficiencia();
        double penalidade = switch (classificacaoIQAR) {
            case "Boa"       -> 0.0;
            case "Moderada"  -> 0.05;
            case "Ruim"      -> 0.15;
            case "Péssima"   -> 0.30;
            default          -> 0.0;
        };
        return Math.max(0.1, base - penalidade);
    }



    private void atualizarClassificacaoIQAR() {
        if (nivelPM25 <= 25)       classificacaoIQAR = "Boa";
        else if (nivelPM25 <= 50)  classificacaoIQAR = "Moderada";
        else if (nivelPM25 <= 100) classificacaoIQAR = "Ruim";
        else                       classificacaoIQAR = "Péssima";
    }


    public double getNivelPM25()           { return nivelPM25; }
    public double getNivelCO2()            { return nivelCO2; }
    public double getNivelO3()             { return nivelO3; }
    public String getClassificacaoIQAR()   { return classificacaoIQAR; }
}