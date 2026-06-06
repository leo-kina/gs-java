package util;


public class Console {

    public static final String LINHA = "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━";

    public static void titulo(String texto) {
        System.out.println("\n" + LINHA);
        System.out.printf("   %s%n", texto);
        System.out.println(LINHA);
    }

    public static void subtitulo(String texto) {
        System.out.println("\n  ▶ " + texto);
    }

    public static void sucesso(String msg) {
        System.out.println("  ✅ " + msg);
    }

    public static void erro(String msg) {
        System.out.println("  ❌ " + msg);
    }

    public static void aviso(String msg) {
        System.out.println("  ⚠️  " + msg);
    }

    public static void info(String msg) {
        System.out.println("  ℹ  " + msg);
    }

    public static void separador() {
        System.out.println("  " + "─".repeat(48));
    }

    public static void banner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════╗");
        System.out.println("  ║    🌐  SmartInfra — Plataforma Inteligente   ║");
        System.out.println("  ║       de Monitoramento de Infraestrutura      ║");
        System.out.println("  ║                                               ║");
        System.out.println("  ║    ODS 9 — Indústria, Inovação e             ║");
        System.out.println("  ║           Infraestrutura                      ║");
        System.out.println("  ╚══════════════════════════════════════════════╝");
        System.out.println();
    }
}