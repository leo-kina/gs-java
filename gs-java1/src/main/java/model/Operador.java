package model;


public class Operador {

    public enum Cargo { TECNICO, ENGENHEIRO, SUPERVISOR, ADMINISTRADOR }

    private String matricula;
    private String nome;
    private Cargo cargo;
    private int totalOcorrenciasAtendidas;
    private boolean emServico;

    public Operador(String matricula, String nome, Cargo cargo) {
        this.matricula = matricula;
        this.nome = nome;
        this.cargo = cargo;
        this.totalOcorrenciasAtendidas = 0;
        this.emServico = false;
    }

    public void iniciarTurno() {
        this.emServico = true;
        System.out.printf("👤 Model.Operador %s [%s] iniciou turno.%n", nome, cargo.name());
    }

    public void encerrarTurno() {
        this.emServico = false;
        System.out.printf("👤 Model.Operador %s encerrou turno. Ocorrências atendidas: %d%n",
                nome, totalOcorrenciasAtendidas);
    }

    public void atenderAlerta(Alerta alerta, String observacao) {
        if (!emServico) {
            System.out.println("⚠️  Model.Operador " + nome + " não está em serviço!");
            return;
        }
        alerta.resolver(observacao + " [por: " + nome + "]");
        this.totalOcorrenciasAtendidas++;
    }


    public String getMatricula()   { return matricula; }
    public String getNome()        { return nome; }
    public Cargo getCargo()        { return cargo; }
    public int getTotalAtendidas() { return totalOcorrenciasAtendidas; }
    public boolean isEmServico()   { return emServico; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Cargo: %s | Em serviço: %s | Atendimentos: %d",
                matricula, nome, cargo.name(),
                emServico ? "Sim" : "Não",
                totalOcorrenciasAtendidas);
    }
}