package service;

import model.CentralMonitoramento;
import model.SensorAmbiental;
import model.SensorHidrico;
import model.SensorQualidadeAr;
import abstracts.EquipamentoMonitorado;
import interfaces.Calculavel;
import interfaces.Rastreavel;

import java.util.List;


public class GerenciadorSensores {

    private CentralMonitoramento central;

    public GerenciadorSensores(CentralMonitoramento central) {
        this.central = central;
    }


    public SensorQualidadeAr cadastrarSensor(String id, String nome, String localizacao) {
        SensorQualidadeAr sensor = new SensorQualidadeAr(id, nome, localizacao);
        central.cadastrarSensor(sensor);
        return sensor;
    }


    public SensorHidrico cadastrarSensor(String id, String nome, String localizacao,
                                         String corpoHidrico, double cotaCritica) {
        SensorHidrico sensor = new SensorHidrico(id, nome, localizacao, corpoHidrico, cotaCritica);
        central.cadastrarSensor(sensor);
        return sensor;
    }



    public SensorAmbiental cadastrarSensor(String id, String nome, String fabricante,
                                           String localizacao, String unidade,
                                           double min, double max, double custo) {
        SensorAmbiental sensor = new SensorAmbiental(
                id, nome, fabricante, localizacao, unidade, min, max, custo);
        central.cadastrarSensor(sensor);
        return sensor;
    }


    public void cadastrarSensor(EquipamentoMonitorado sensor) {
        central.cadastrarSensor(sensor);
    }


    public boolean desativarSensor(String id) {
        EquipamentoMonitorado sensor = central.buscarSensorPorId(id);
        if (sensor == null) {
            System.out.println("⚠️  Sensor não encontrado: " + id);
            return false;
        }
        sensor.setEmFuncionamento(false);
        System.out.printf("🔴 Sensor [%s] desativado.%n", id);
        return true;
    }


    public boolean desativarSensor(EquipamentoMonitorado sensor) {
        if (sensor == null) return false;
        sensor.setEmFuncionamento(false);
        System.out.printf("🔴 Sensor [%s] desativado.%n", sensor.getId());
        return true;
    }


    public void atualizarLocalizacao(String idSensor, String novaLocalizacao) {
        EquipamentoMonitorado eq = central.buscarSensorPorId(idSensor);
        if (eq instanceof Rastreavel rastreavel) {
            rastreavel.atualizarLocalizacao(novaLocalizacao);
        } else {
            System.out.println("⚠️  Sensor não encontrado ou não é rastreável.");
        }
    }


    public void atualizarLocalizacao(Rastreavel sensor, String novaLocalizacao) {
        sensor.atualizarLocalizacao(novaLocalizacao);
    }


    public void exibirEficiencias() {
        System.out.println("\n📊 EFICIÊNCIAS DOS SENSORES:");
        List<EquipamentoMonitorado> sensores = central.getSensores();
        for (EquipamentoMonitorado eq : sensores) {
            if (eq instanceof Calculavel calc) {
                double eficiencia = calc.calcularEficiencia();
                double custo      = calc.calcularCustoOperacional();
                String barra      = gerarBarraProgresso(eficiencia);
                System.out.printf("  [%s] %-20s %s %.1f%% | Custo: R$ %.2f/mês%n",
                        eq.getId(), eq.getNome(), barra, eficiencia * 100, custo);
            }
        }
    }

    private String gerarBarraProgresso(double valor) {
        int preenchido = (int) (valor * 20);
        StringBuilder barra = new StringBuilder("[");
        for (int i = 0; i < 20; i++) {
            barra.append(i < preenchido ? "█" : "░");
        }
        barra.append("]");
        return barra.toString();
    }
}