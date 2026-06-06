package model;

import abstracts.EquipamentoMonitorado;
import abstracts.ServicoOperacional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CentralMonitoramento extends ServicoOperacional {

    private String cidade;
    private String endereco;
    private List<EquipamentoMonitorado> sensores;
    private List<Alerta> alertas;
    private List<Operador> operadores;
    private int ciclosDeMonitoramento;

    public CentralMonitoramento(String codigo, String cidade, String endereco) {
        super(codigo, "Central de Monitoramento SmartInfra — " + cidade, 3);
        this.cidade = cidade;
        this.endereco = endereco;
        this.sensores = new ArrayList<>();
        this.alertas = new ArrayList<>();
        this.operadores = new ArrayList<>();
        this.ciclosDeMonitoramento = 0;
    }


    @Override
    public void iniciar() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("   🌐 SmartInfra — Central Online!");
        System.out.printf("   Cidade   : %s%n", cidade);
        System.out.printf("   Endereço : %s%n", endereco);
        System.out.printf("   Sensores : %d cadastrados%n", sensores.size());
        System.out.printf("   Operadores: %d cadastrados%n", operadores.size());
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    @Override
    public void encerrar() {
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("   🔴 SmartInfra — Central Encerrada.");
        System.out.printf("   Ciclos realizados : %d%n", ciclosDeMonitoramento);
        System.out.printf("   Total de alertas  : %d%n", alertas.size());
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    @Override
    public String getStatusDetalhado() {
        long alertasPendentes = alertas.stream()
                .filter(a -> a.getStatus() == Alerta.Status.PENDENTE).count();
        long alertasCriticos = alertas.stream()
                .filter(a -> a.getPrioridade() == 3 && a.getStatus() == Alerta.Status.PENDENTE).count();

        return String.format(
                "\n━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n" +
                        "  CENTRAL: %s\n" +
                        "  Cidade : %s\n" +
                        "  Status : %s\n" +
                        "  Sensores cadastrados : %d\n" +
                        "  Alertas totais       : %d\n" +
                        "  Alertas pendentes    : %d\n" +
                        "  Alertas críticos     : %d\n" +
                        "  Ciclos de monit.     : %d\n" +
                        "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━",
                getCodigoServico(), cidade,
                isAtivo() ? "🟢 ONLINE" : "🔴 OFFLINE",
                sensores.size(), alertas.size(),
                alertasPendentes, alertasCriticos,
                ciclosDeMonitoramento
        );
    }


    public void cadastrarSensor(EquipamentoMonitorado sensor) {
        sensores.add(sensor);
        System.out.printf("✅ Sensor cadastrado: %s%n", sensor.toString());
    }

    public EquipamentoMonitorado buscarSensorPorId(String id) {
        return sensores.stream()
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }

    public void removerSensor(String id) {
        boolean removido = sensores.removeIf(s -> s.getId().equalsIgnoreCase(id));
        System.out.printf(removido
                ? "🗑  Sensor [%s] removido.%n"
                : "⚠️  Sensor [%s] não encontrado.%n", id);
    }

    public void executarCicloMonitoramento() {
        ciclosDeMonitoramento++;
        System.out.printf("%n🔄 ── Ciclo de Monitoramento #%d ──%n", ciclosDeMonitoramento);
        for (EquipamentoMonitorado sensor : sensores) {
            if (sensor.isEmFuncionamento()) {
                String leitura = sensor.realizarLeitura();
                System.out.println(leitura);
            }
        }
        System.out.println();
    }

    public void registrarAlerta(Alerta alerta) {
        alertas.add(alerta);
    }

    public List<Alerta> getAlertasPendentes() {
        return alertas.stream()
                .filter(a -> a.getStatus() == Alerta.Status.PENDENTE)
                .collect(Collectors.toList());
    }

    public List<Alerta> getAlertasCriticos() {
        return alertas.stream()
                .filter(a -> a.getPrioridade() == 3)
                .collect(Collectors.toList());
    }

    public void cadastrarOperador(Operador op) {
        operadores.add(op);
        System.out.printf("👤 Model.Operador cadastrado: %s%n", op.toString());
    }

    public Operador buscarOperadorPorMatricula(String matricula) {
        return operadores.stream()
                .filter(o -> o.getMatricula().equalsIgnoreCase(matricula))
                .findFirst()
                .orElse(null);
    }

    public void exibirRelatorioCompleto() {
        System.out.println(getStatusDetalhado());
        System.out.println("\n📋 SENSORES:");
        if (sensores.isEmpty()) {
            System.out.println("  Nenhum sensor cadastrado.");
        } else {
            sensores.forEach(s -> System.out.println(s.gerarRelatorioStatus()));
        }
    }

    public void exibirListaAlertas() {
        System.out.println("\n🔔 LISTA DE ALERTAS:");
        if (alertas.isEmpty()) {
            System.out.println("  Nenhum alerta registrado.");
            return;
        }
        alertas.forEach(a -> System.out.println(a.toString()));
    }

    public void exibirListaSensores() {
        System.out.println("\n📡 SENSORES CADASTRADOS:");
        if (sensores.isEmpty()) {
            System.out.println("  Nenhum sensor cadastrado.");
            return;
        }
        for (int i = 0; i < sensores.size(); i++) {
            System.out.printf("  [%d] %s%n", i + 1, sensores.get(i).toString());
        }
    }

    public void exibirListaOperadores() {
        System.out.println("\n👥 OPERADORES CADASTRADOS:");
        if (operadores.isEmpty()) {
            System.out.println("  Nenhum operador cadastrado.");
            return;
        }
        operadores.forEach(op -> System.out.println("  " + op.toString()));
    }

    public String getCidade()            { return cidade; }
    public List<EquipamentoMonitorado> getSensores() { return sensores; }
    public List<Alerta> getAlertas()     { return alertas; }
    public List<Operador> getOperadores(){ return operadores; }
    public int getCiclos()               { return ciclosDeMonitoramento; }
}