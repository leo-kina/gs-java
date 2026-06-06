package service;

import interfaces.Notificavel;
import model.Alerta;
import model.CentralMonitoramento;
import model.Operador;
import abstracts.EquipamentoMonitorado;

import java.util.List;


public class GerenciadorAlertas {

    private CentralMonitoramento central;

    public GerenciadorAlertas(CentralMonitoramento central) {
        this.central = central;
    }


    public Alerta gerarAlerta(String idSensor, Alerta.Tipo tipo,
                              String mensagem, int prioridade) {
        Alerta alerta = new Alerta(idSensor, tipo, mensagem, prioridade);
        central.registrarAlerta(alerta);


        EquipamentoMonitorado sensor = central.buscarSensorPorId(idSensor);
        if (sensor instanceof Notificavel notif) {
            notif.enviarNotificacao(mensagem);
        }
        return alerta;
    }


    public void reconhecerAlerta(int numeroAlerta, String observacao, String matriculaOp) {
        Alerta alerta = encontrarAlerta(numeroAlerta);
        if (alerta == null) {
            System.out.println("⚠️  Model.Alerta #" + numeroAlerta + " não encontrado.");
            return;
        }
        Operador op = central.buscarOperadorPorMatricula(matriculaOp);
        String obs = observacao + (op != null ? " [por: " + op.getNome() + "]" : "");
        alerta.reconhecer(obs);
    }


    public void resolverAlerta(int numeroAlerta, String observacao, String matriculaOp) {
        Alerta alerta = encontrarAlerta(numeroAlerta);
        if (alerta == null) {
            System.out.println("⚠️  Model.Alerta #" + numeroAlerta + " não encontrado.");
            return;
        }
        Operador op = central.buscarOperadorPorMatricula(matriculaOp);
        if (op != null && op.isEmServico()) {
            op.atenderAlerta(alerta, observacao);
        } else {
            alerta.resolver(observacao + " [operador sem turno ativo]");
        }
    }



    public void exibirAlertasPendentes() {
        List<Alerta> pendentes = central.getAlertasPendentes();
        System.out.println("\n⏳ ALERTAS PENDENTES (" + pendentes.size() + "):");
        if (pendentes.isEmpty()) {
            System.out.println("  ✅ Nenhum alerta pendente.");
        } else {
            pendentes.forEach(a -> System.out.println(a.toString()));
        }
    }


    public void exibirAlertasCriticos() {
        List<Alerta> criticos = central.getAlertasCriticos();
        System.out.println("\n🚨 ALERTAS CRÍTICOS (" + criticos.size() + "):");
        if (criticos.isEmpty()) {
            System.out.println("  ✅ Nenhum alerta crítico.");
        } else {
            criticos.forEach(a -> System.out.println(a.toString()));
        }
    }


    private Alerta encontrarAlerta(int numero) {
        return central.getAlertas().stream()
                .filter(a -> a.getNumero() == numero)
                .findFirst()
                .orElse(null);
    }
}