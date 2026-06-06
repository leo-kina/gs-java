# 🌐 SmartInfra — Plataforma Inteligente de Monitoramento de Infraestrutura

> **ODS 9 — Indústria, Inovação e Infraestrutura**  
> Disciplina: Domain Driven Design - Java · FIAP 2ESPZ · 2026  
> Prof.ª Damiana Costa

---

## 📌 Integrantes

| Nome | RM |
|------|----|
| Leonardo Eiji Kina | 562784 |
| Nicholas Braga de Souza | 561733 |
| Tomé Rossi Giani | 562422 |
| Vitor Ramos de Farias | 561958 |


---

## 🧩 Sobre o Projeto

O **SmartInfra** é uma plataforma de monitoramento inteligente de infraestrutura urbana desenvolvida em Java. O sistema simula uma rede de sensores distribuídos pela cidade, capaz de coletar leituras ambientais em tempo real, gerar alertas automáticos quando valores saem dos limites configurados, e permitir que operadores acompanhem e respondam a ocorrências.

O projeto foi construído sobre o ODS 9 pelo recorte direto que ele representa: infraestrutura digital inteligente, sensores conectados e automação de processos são os pilares que o ODS 9 busca promover para cidades e indústrias.

---

## 🎯 Funcionalidades

- Cadastro e gerenciamento de sensores de **qualidade do ar**, **nível hídrico** e **ambientais genéricos**
- Ciclos de monitoramento automáticos com leituras simuladas de todos os sensores ativos
- Sistema de **alertas com três níveis de prioridade** (baixo, médio, crítico)
- Reconhecimento e resolução de alertas por operadores autenticados
- Cálculo de **eficiência operacional** e custo por sensor
- Relatórios detalhados de status por equipamento
- Menu interativo via console com **Scanner**

---

## 🗂️ Estrutura do Projeto

```
src/
├── app/
│   └── Main.java                     ← Ponto de entrada; menu principal com Scanner
├── model/
│   ├── SensorAmbiental.java          ← Sensor genérico (herda EquipamentoMonitorado)
│   ├── SensorQualidadeAr.java        ← Sensor especializado (herda SensorAmbiental)
│   ├── SensorHidrico.java            ← Sensor hídrico (herda SensorAmbiental)
│   ├── Alerta.java                   ← Entidade de alerta com ciclo de vida
│   ├── Operador.java                 ← Operador humano do sistema
│   └── CentralMonitoramento.java     ← Hub central (herda ServicoOperacional)
├── service/
│   ├── GerenciadorSensores.java      ← Lógica de negócio dos sensores
│   └── GerenciadorAlertas.java       ← Lógica de negócio dos alertas
├── abstracts/
│   ├── EquipamentoMonitorado.java    ← Classe abstrata base dos equipamentos
│   └── ServicoOperacional.java       ← Classe abstrata base dos serviços
├── interfaces/
│   ├── Rastreavel.java               ← Contrato de rastreamento
│   ├── Notificavel.java              ← Contrato de notificação
│   └── Calculavel.java               ← Contrato de cálculo de métricas
└── util/
    ├── Validador.java                ← Validação de entradas (sobrecarga)
    └── Console.java                  ← Utilitário de formatação do console
```

---

## ▶️ Como Executar

### Pré-requisitos

- Java 17 ou superior
- Qualquer IDE (IntelliJ IDEA, Eclipse, VSCode com extensão Java) ou terminal

### Pelo terminal

```bash
# 1. Clone o repositório
git clone https://github.com/seu-usuario/smartinfra.git
cd smartinfra

# 2. Compile a partir da pasta raiz do projeto
javac -d out -sourcepath src src/app/Main.java

# 3. Execute
java -cp out app.Main
```

### Pela IDE (IntelliJ / Eclipse)

1. Abra a pasta `SmartInfra` como projeto Java
2. Certifique-se de que `src/` está marcado como **Sources Root**
3. Execute `app.Main`

---

## 🧪 Navegando pelo Sistema

Ao iniciar, o sistema já carrega **6 sensores e 3 operadores** de demonstração para facilitar os testes. A opção `[8] Demonstração Rápida` no menu principal executa automaticamente um ciclo completo mostrando todos os conceitos POO em funcionamento.

```
  ╔══════════════════════════════════════════════╗
  ║    🌐  SmartInfra — Plataforma Inteligente   ║
  ║       de Monitoramento de Infraestrutura      ║
  ║                                               ║
  ║    ODS 9 — Indústria, Inovação e             ║
  ║           Infraestrutura                      ║
  ╚══════════════════════════════════════════════╝

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
   MENU PRINCIPAL — SmartInfra
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
  [1] 📡  Gerenciar Sensores
  [2] 🔔  Gerenciar Alertas
  [3] 👤  Gerenciar Operadores
  [4] 🔄  Executar Ciclo de Monitoramento
  [5] 📊  Relatório de Eficiências
  [6] 📋  Relatório Completo da Central
  [7] 🧵  Status da Central
  [8] 🧪  Demonstração Rápida (modo demo)
  [0] 🚪  Sair
```

---

## 🏗️ Modelagem Orientada a Objetos

### Hierarquia de herança

```
EquipamentoMonitorado  (abstrata)
└── SensorAmbiental    (implementa Rastreavel, Notificavel, Calculavel)
    ├── SensorQualidadeAr
    └── SensorHidrico

ServicoOperacional     (abstrata)
└── CentralMonitoramento
```

### Interfaces implementadas

| Interface | Responsabilidade | Quem implementa |
|-----------|-----------------|-----------------|
| `Rastreavel` | Localização e rastreio | `SensorAmbiental` (e subclasses) |
| `Notificavel` | Emissão e gestão de alertas | `SensorAmbiental` (e subclasses) |
| `Calculavel` | Eficiência e custo operacional | `SensorAmbiental` (e subclasses) |

### Onde ocorre sobrescrita (`@Override`)

| Classe | Métodos sobrescritos |
|--------|----------------------|
| `SensorAmbiental` | `realizarLeitura()`, `gerarRelatorioStatus()`, `obterLocalizacao()`, `atualizarLocalizacao()`, `estaAtivo()`, `enviarNotificacao()`, `possuiAlertasPendentes()`, `getNivelPrioridade()`, `calcularEficiencia()`, `calcularCustoOperacional()`, `toString()` |
| `SensorQualidadeAr` | `realizarLeitura()`, `gerarRelatorioStatus()`, `calcularEficiencia()` |
| `SensorHidrico` | `realizarLeitura()`, `gerarRelatorioStatus()`, `calcularEficiencia()` |
| `CentralMonitoramento` | `iniciar()`, `encerrar()`, `getStatusDetalhado()`, `toString()` |

### Onde ocorre sobrecarga

`GerenciadorSensores.cadastrarSensor()` possui **4 versões** com assinaturas diferentes:
- `(String id, String nome, String localizacao)` → cria `SensorQualidadeAr`
- `(String id, String nome, String localizacao, String corpoHidrico, double cotaCritica)` → cria `SensorHidrico`
- `(String id, String nome, String fabricante, String localizacao, String unidade, double min, double max, double custo)` → cria `SensorAmbiental` genérico
- `(EquipamentoMonitorado sensor)` → registra sensor já instanciado

`Validador.validar()` possui **4 versões**: `(String)`, `(double, double, double)`, `(int, int, int)`, `(String, int, int)`.

---

## 🔗 Relação com o ODS 9

O ODS 9 busca *"construir infraestruturas resilientes, promover a industrialização inclusiva e sustentável e fomentar a inovação"*. O SmartInfra se alinha a esse objetivo de três formas concretas:

1. **Infraestrutura resiliente** — sensores hídricos monitoram rios e reservatórios, gerando alertas automáticos de enchente antes que o nível atinja a cota crítica
2. **Inovação digital** — a plataforma demonstra como sistemas de software orientados a objetos podem ser a base de soluções de cidades inteligentes (smart cities)
3. **Automação de processos** — o ciclo de monitoramento automático substitui inspeções manuais, reduzindo custos operacionais e tempo de resposta a incidentes
