package abstracts;

public abstract class ServicoOperacional {

    private String codigoServico;
    private String descricao;
    private boolean ativo;
    private int prioridade;

    public ServicoOperacional(String codigoServico, String descricao, int prioridade) {
        this.codigoServico = codigoServico;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.ativo = false;
    }


    public abstract void iniciar();
    public abstract void encerrar();
    public abstract String getStatusDetalhado();

    public void ativar() {
        this.ativo = true;
        iniciar();
    }

    public void desativar() {
        this.ativo = false;
        encerrar();
    }

    public String getNomePrioridade() {
        return switch (prioridade) {
            case 1 -> "BAIXA";
            case 2 -> "MÉDIA";
            case 3 -> "ALTA";
            default -> "INDEFINIDA";
        };
    }


    public String getCodigoServico() { return codigoServico; }
    public String getDescricao()     { return descricao; }
    public boolean isAtivo()         { return ativo; }
    public int getPrioridade()       { return prioridade; }

    public void setPrioridade(int p) { this.prioridade = p; }
    public void setDescricao(String d) { this.descricao = d; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Prioridade: %s | %s",
                codigoServico, descricao, getNomePrioridade(),
                ativo ? "ATIVO" : "INATIVO");
    }
}