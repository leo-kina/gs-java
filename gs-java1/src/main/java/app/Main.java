package app;

import model.*;
import service.*;
import util.*;

import java.util.Scanner;


public class Main {


    private static CentralMonitoramento central;
    private static GerenciadorSensores gerSensores;
    private static GerenciadorAlertas gerAlertas;
    private static Scanner scanner;

    public static void main(String[] args) {

        Console.banner();
        scanner = new Scanner(System.in);


        central = new CentralMonitoramento("CTRL-SP-001", "São Paulo", "Av. Paulista, 1000");
        gerSensores = new GerenciadorSensores(central);
        gerAlertas = new GerenciadorAlertas(central);


        popularDadosIniciais();


        central.ativar();


        boolean rodando = true;
        while (rodando) {
            exibirMenuPrincipal();
            int opcao = lerInteiro("  Opção: ", 0, 9);
            rodando = processarMenuPrincipal(opcao);
        }

        central.desativar();
        scanner.close();
        System.out.println("\n  Obrigado por usar o SmartInfra. Até logo!\n");
    }



    private static void exibirMenuPrincipal() {
        Console.titulo("MENU PRINCIPAL — SmartInfra");
        System.out.println("  [1] 📡  Gerenciar Sensores");
        System.out.println("  [2] 🔔  Gerenciar Alertas");
        System.out.println("  [3] 👤  Gerenciar Operadores");
        System.out.println("  [4] 🔄  Executar Ciclo de Monitoramento");
        System.out.println("  [5] 📊  Relatório de Eficiências");
        System.out.println("  [6] 📋  Relatório Completo da Central");
        System.out.println("  [7] 🧵  Status da Central");
        System.out.println("  [8] 🧪  Demonstração Rápida (modo demo)");
        System.out.println("  [0] 🚪  Sair");
        System.out.println(Console.LINHA);
    }

    private static boolean processarMenuPrincipal(int opcao) {
        return switch (opcao) {
            case 1 -> { menuSensores();  yield true; }
            case 2 -> { menuAlertas();   yield true; }
            case 3 -> { menuOperadores();yield true; }
            case 4 -> { central.executarCicloMonitoramento(); pausar(); yield true; }
            case 5 -> { gerSensores.exibirEficiencias(); pausar(); yield true; }
            case 6 -> { central.exibirRelatorioCompleto(); pausar(); yield true; }
            case 7 -> { System.out.println(central.getStatusDetalhado()); pausar(); yield true; }
            case 8 -> { executarDemo(); yield true; }
            case 0 -> false;
            default -> true;
        };
    }



    private static void menuSensores() {
        boolean voltar = false;
        while (!voltar) {
            Console.titulo("GERENCIAR SENSORES");
            System.out.println("  [1] Listar sensores cadastrados");
            System.out.println("  [2] Cadastrar Sensor de Qualidade do Ar");
            System.out.println("  [3] Cadastrar Sensor Hídrico");
            System.out.println("  [4] Cadastrar Sensor Ambiental Genérico");
            System.out.println("  [5] Realizar leitura de um sensor");
            System.out.println("  [6] Ver relatório de sensor específico");
            System.out.println("  [7] Atualizar localização de sensor");
            System.out.println("  [8] Desativar sensor");
            System.out.println("  [9] Resetar sensor");
            System.out.println("  [0] Voltar");
            Console.separador();

            int op = lerInteiro("  Opção: ", 0, 9);
            switch (op) {
                case 0 -> voltar = true;
                case 1 -> { central.exibirListaSensores(); pausar(); }
                case 2 -> cadastrarSensorAr();
                case 3 -> cadastrarSensorHidrico();
                case 4 -> cadastrarSensorGenerico();
                case 5 -> realizarLeituraSensor();
                case 6 -> verRelatorioSensor();
                case 7 -> atualizarLocalizacaoSensor();
                case 8 -> desativarSensor();
                case 9 -> resetarSensor();
            }
        }
    }

    private static void cadastrarSensorAr() {
        Console.subtitulo("Novo Sensor de Qualidade do Ar");
        String id = lerString("  ID do sensor (ex: AR001): ");
        String nome = lerString("  Nome descritivo: ");
        String local = lerString("  Localização: ");
        gerSensores.cadastrarSensor(id, nome, local);
        pausar();
    }

    private static void cadastrarSensorHidrico() {
        Console.subtitulo("Novo Sensor Hídrico");
        String id = lerString("  ID do sensor (ex: HD001): ");
        String nome = lerString("  Nome descritivo: ");
        String local = lerString("  Localização: ");
        String corpoHidrico = lerString("  Nome do corpo hídrico (rio/represa): ");
        double cotaCritica = lerDouble("  Cota crítica em metros: ", 1.0, 50.0);
        gerSensores.cadastrarSensor(id, nome, local, corpoHidrico, cotaCritica);
        pausar();
    }

    private static void cadastrarSensorGenerico() {
        Console.subtitulo("Novo Sensor Ambiental Genérico");
        String id = lerString("  ID do sensor: ");
        String nome = lerString("  Nome descritivo: ");
        String fab = lerString("  Fabricante: ");
        String local = lerString("  Localização: ");
        String unidade = lerString("  Unidade de medida (ex: °C, dB, ppm): ");
        double min = lerDouble("  Valor mínimo aceitável: ", -999, 999);
        double max = lerDouble("  Valor máximo aceitável: ", -999, 999);
        double custo = lerDouble("  Custo mensal de operação (R$): ", 0, 99999);
        gerSensores.cadastrarSensor(id, nome, fab, local, unidade, min, max, custo);
        pausar();
    }

    private static void realizarLeituraSensor() {
        central.exibirListaSensores();
        String id = lerString("  ID do sensor para leitura: ");
        var sensor = central.buscarSensorPorId(id);
        if (sensor == null) { Console.erro("Sensor não encontrado."); }
        else                { System.out.println(sensor.realizarLeitura()); }
        pausar();
    }

    private static void verRelatorioSensor() {
        central.exibirListaSensores();
        String id = lerString("  ID do sensor: ");
        var sensor = central.buscarSensorPorId(id);
        if (sensor == null) { Console.erro("Sensor não encontrado."); }
        else                { System.out.println(sensor.gerarRelatorioStatus()); }
        pausar();
    }

    private static void atualizarLocalizacaoSensor() {
        central.exibirListaSensores();
        String id = lerString("  ID do sensor: ");
        String novaLoc = lerString("  Nova localização: ");
        gerSensores.atualizarLocalizacao(id, novaLoc);
        pausar();
    }

    private static void desativarSensor() {
        central.exibirListaSensores();
        String id = lerString("  ID do sensor a desativar: ");
        gerSensores.desativarSensor(id);
        pausar();
    }

    private static void resetarSensor() {
        central.exibirListaSensores();
        String id = lerString("  ID do sensor a resetar: ");
        var sensor = central.buscarSensorPorId(id);
        if (sensor == null) { Console.erro("Sensor não encontrado."); }
        else                { sensor.resetar(); }
        pausar();
    }


    private static void menuAlertas() {
        boolean voltar = false;
        while (!voltar) {
            Console.titulo("GERENCIAR ALERTAS");
            System.out.println("  [1] Ver todos os alertas");
            System.out.println("  [2] Ver alertas pendentes");
            System.out.println("  [3] Ver alertas críticos");
            System.out.println("  [4] Gerar alerta manual");
            System.out.println("  [5] Reconhecer alerta");
            System.out.println("  [6] Resolver alerta");
            System.out.println("  [0] Voltar");
            Console.separador();

            int op = lerInteiro("  Opção: ", 0, 6);
            switch (op) {
                case 0 -> voltar = true;
                case 1 -> { central.exibirListaAlertas(); pausar(); }
                case 2 -> { gerAlertas.exibirAlertasPendentes(); pausar(); }
                case 3 -> { gerAlertas.exibirAlertasCriticos(); pausar(); }
                case 4 -> gerarAlertaManual();
                case 5 -> reconhecerAlerta();
                case 6 -> resolverAlerta();
            }
        }
    }

    private static void gerarAlertaManual() {
        Console.subtitulo("Gerar Model.Alerta Manual");
        central.exibirListaSensores();
        String idSensor = lerString("  ID do sensor: ");
        System.out.println("  Tipos: 1-QUALIDADE_AR  2-NIVEL_HIDRICO  3-TEMPERATURA  4-RUIDO  5-GERAL");
        int tipoIdx = lerInteiro("  Tipo de alerta: ", 1, 5);
        String mensagem = lerString("  Mensagem do alerta: ");
        int prioridade = lerInteiro("  Prioridade (1=baixo 2=médio 3=crítico): ", 1, 3);

        Alerta.Tipo tipo = Alerta.Tipo.values()[tipoIdx - 1];
        gerAlertas.gerarAlerta(idSensor, tipo, mensagem, prioridade);
        pausar();
    }

    private static void reconhecerAlerta() {
        gerAlertas.exibirAlertasPendentes();
        int num = lerInteiro("  Número do alerta a reconhecer: ", 1, 9999);
        String obs = lerString("  Observação: ");
        String mat = lerString("  Sua matrícula: ");
        gerAlertas.reconhecerAlerta(num, obs, mat);
        pausar();
    }

    private static void resolverAlerta() {
        gerAlertas.exibirAlertasPendentes();
        int num = lerInteiro("  Número do alerta a resolver: ", 1, 9999);
        String obs = lerString("  Observação de resolução: ");
        String mat = lerString("  Sua matrícula: ");
        gerAlertas.resolverAlerta(num, obs, mat);
        pausar();
    }


    private static void menuOperadores() {
        boolean voltar = false;
        while (!voltar) {
            Console.titulo("GERENCIAR OPERADORES");
            System.out.println("  [1] Listar operadores");
            System.out.println("  [2] Cadastrar operador");
            System.out.println("  [3] Iniciar turno de operador");
            System.out.println("  [4] Encerrar turno de operador");
            System.out.println("  [0] Voltar");
            Console.separador();

            int op = lerInteiro("  Opção: ", 0, 4);
            switch (op) {
                case 0 -> voltar = true;
                case 1 -> { central.exibirListaOperadores(); pausar(); }
                case 2 -> cadastrarOperador();
                case 3 -> iniciarTurno();
                case 4 -> encerrarTurno();
            }
        }
    }

    private static void cadastrarOperador() {
        Console.subtitulo("Cadastrar Model.Operador");
        String mat = lerString("  Matrícula: ");
        String nome = lerString("  Nome completo: ");
        System.out.println("  Cargos: 1-TECNICO  2-ENGENHEIRO  3-SUPERVISOR  4-ADMINISTRADOR");
        int cargoIdx = lerInteiro("  Cargo: ", 1, 4);
        Operador.Cargo cargo = Operador.Cargo.values()[cargoIdx - 1];
        Operador op = new Operador(mat, nome, cargo);
        central.cadastrarOperador(op);
        pausar();
    }

    private static void iniciarTurno() {
        central.exibirListaOperadores();
        String mat = lerString("  Matrícula do operador: ");
        Operador op = central.buscarOperadorPorMatricula(mat);
        if (op == null) { Console.erro("Model.Operador não encontrado."); }
        else            { op.iniciarTurno(); }
        pausar();
    }

    private static void encerrarTurno() {
        central.exibirListaOperadores();
        String mat = lerString("  Matrícula do operador: ");
        Operador op = central.buscarOperadorPorMatricula(mat);
        if (op == null) { Console.erro("Model.Operador não encontrado."); }
        else            { op.encerrarTurno(); }
        pausar();
    }


    private static void executarDemo() {
        Console.titulo("🧪 MODO DEMONSTRAÇÃO — Mostrando todos os conceitos POO");

        Console.subtitulo("1. POLIMORFISMO — Ciclo de monitoramento (todos os sensores)");
        central.executarCicloMonitoramento();

        Console.subtitulo("2. EFICIÊNCIAS — interface interfaces.Calculavel");
        gerSensores.exibirEficiencias();

        Console.subtitulo("3. ALERTAS PENDENTES — interface interfaces.Notificavel");
        gerAlertas.exibirAlertasPendentes();

        Console.subtitulo("4. STATUS DA CENTRAL — método sobrescrito");
        System.out.println(central.getStatusDetalhado());

        Console.subtitulo("5. RELATÓRIO de sensor de qualidade do ar (sobrescrita @Override)");
        var ar = central.buscarSensorPorId("AR001");
        if (ar != null) System.out.println(ar.gerarRelatorioStatus());

        Console.subtitulo("6. RELATÓRIO de sensor hídrico (sobrescrita @Override)");
        var hid = central.buscarSensorPorId("HD001");
        if (hid != null) System.out.println(hid.gerarRelatorioStatus());

        pausar();
    }


    private static void popularDadosIniciais() {
        gerSensores.cadastrarSensor("AR001", "Sensor Qualidade Ar - Centro", "Praça da Sé, SP");
        gerSensores.cadastrarSensor("AR002", "Sensor Qualidade Ar - Marginal", "Marginal Tietê, SP");
        gerSensores.cadastrarSensor("HD001", "Sensor Hídrico - Rio Tietê", "Ponte das Bandeiras", "Rio Tietê", 8.5);
        gerSensores.cadastrarSensor("HD002", "Sensor Hídrico - Represa Guarapiranga", "Interlagos", "Represa Guarapiranga", 12.0);
        gerSensores.cadastrarSensor("TM001", "Sensor Termométrico - USP", "SmartTemp Ind.", "Campus USP Butantã", "°C", 10.0, 42.0, 600.0);
        gerSensores.cadastrarSensor("RD001", "Sensor Ruído - Aeroporto", "NoiseWatch SA", "Aeroporto de Congonhas", "dB", 40.0, 85.0, 950.0);

        central.cadastrarOperador(new Operador("OP001", "Carlos Henrique", Operador.Cargo.ENGENHEIRO));
        central.cadastrarOperador(new Operador("OP002", "Ana Beatriz", Operador.Cargo.SUPERVISOR));
        central.cadastrarOperador(new Operador("OP003", "Lucas Ferreira", Operador.Cargo.TECNICO));

        Operador opDemo = central.buscarOperadorPorMatricula("OP001");
        if (opDemo != null) opDemo.iniciarTurno();

        System.out.println();
    }


    private static String lerString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private static int lerInteiro(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int v = Integer.parseInt(scanner.nextLine().trim());
                if (Validador.validar(v, min, max)) return v;
                System.out.printf("  ⚠️  Digite um número entre %d e %d.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Entrada inválida. Digite um número inteiro.");
            }
        }
    }

    private static double lerDouble(String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double v = Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
                if (Validador.validar(v, min, max)) return v;
                System.out.printf("  ⚠️  Digite um valor entre %.1f e %.1f.%n", min, max);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Entrada inválida. Digite um número.");
            }
        }
    }

    private static void pausar() {
        System.out.print("\n  Pressione ENTER para continuar...");
        scanner.nextLine();
    }
}