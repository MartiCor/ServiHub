package cat.pratfp.servihub.pr1;

import cat.pratfp.servihub.pr1.fills.Calculadora;
import cat.pratfp.servihub.pr1.fills.Filtre;
import cat.pratfp.servihub.pr1.fills.Generador;
import cat.pratfp.servihub.pr1.fills.Majuscules;
import cat.pratfp.servihub.pr1.fills.Sumador;
import cat.pratfp.servihub.pr1.fills.Tasca;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ServiHub v1 · programa principal.
 * Fa servir tots els mètodes del {@link Llancador} i acaba amb l'informe de les feines.
 * Si un forat encara no està fet, escriu «[pendent] FORAT xx per completar» i continua amb el pas següent.
 * <p>
 * NO CAL MODIFICAR AQUESTA CLASSE: quan tots els forats estiguin fets, l'informe ha de sortir complet.
 */
public class App {

    /** Un pas de la demostració que pot llançar qualsevol excepció. */
    private interface Pas {
        void fes() throws Exception;
    }

    private static final List<Resultat> resultats = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("=== ServiHub v1 · llançador multiprocés ===");

        pas("Bloc K · qui som", () -> System.out.println("ServiHub s'executa amb el PID " + Llancador.pidDeServiHub()));

        pas("Bloc A · llançar una feina i esperar-la", () -> System.out.println("Codi de retorn de «hola»: "
                + Llancador.executa(Jvm.ordre(Tasca.class, "hola", "1"))));

        pas("Bloc H · tres feines en paral·lel", () -> resultats.addAll(Llancador.executaEnParallel(List.of(
                new Feina("copia", Jvm.ordre(Tasca.class, "copia", "2")),
                new Feina("informe", Jvm.ordre(Tasca.class, "informe", "3")),
                new Feina("malament", Jvm.ordre(Tasca.class, "malament", "1"))))));

        pas("Bloc G · una feina que es penja", () ->
                resultats.add(Llancador.executaAmbTempsMaxim("penjada", 2, Jvm.ordre(Tasca.class, "penjada", "30"))));

        pas("Blocs B, C i D · capturar sortida i error", () -> {
            Resultat r = Llancador.executaICaptura("captura", Jvm.ordre(Tasca.class, "captura", "1"));
            System.out.println("Sortida capturada:\n" + r.sortida());
            resultats.add(r);
            resultats.add(Llancador.executaICaptura("fantasma", "programa-que-no-existeix"));
        });

        pas("Bloc I · sortida a fitxers", () -> {
            Path logs = Path.of("logs").toAbsolutePath();
            resultats.add(Llancador.executaAFitxers("nocturna", logs, Jvm.ordre(Tasca.class, "nocturna", "1")));
            System.out.println("Fitxers desats a " + logs);
        });

        pas("Bloc E · arguments i entorn", () -> System.out.println(Llancador.executaAmbEntorn(
                Map.of("SERVIHUB_USUARI", "dam2"), Jvm.ordre(Calculadora.class, "suma", "7", "5"))));

        pas("Bloc F · entrada estàndard", () -> System.out.println(Llancador.enviaPerEntrada(
                List.of("hola, fill", "som el procés pare"), Jvm.ordre(Majuscules.class))));

        pas("Bloc J · canonada Generador | Filtre | Sumador", () -> System.out.println(Llancador.canonada(List.of(
                Jvm.ordre(Generador.class, "20"), Jvm.ordre(Filtre.class), Jvm.ordre(Sumador.class)))));

        imprimeixInforme();
    }

    /** Executa un pas de la demostració; si el forat encara no està fet, ho diu i continua. */
    private static void pas(String titol, Pas pas) {
        System.out.println();
        System.out.println("--- " + titol + " ---");
        try {
            pas.fes();
        } catch (UnsupportedOperationException e) {
            System.out.println("[pendent] " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[error] " + e);
        }
    }

    /** Taula final amb totes les feines executades. */
    private static void imprimeixInforme() {
        System.out.println();
        System.out.println("=== INFORME FINAL ===");
        System.out.printf("%-10s %8s %10s %6s  %s%n", "FEINA", "PID", "DURADA", "CODI", "ESTAT");
        for (Resultat r : resultats) {
            System.out.printf("%-10s %8d %8d ms %6d  %s%n", r.nom(), r.pid(), r.durada(), r.codi(), r.estat());
        }
        long correctes = resultats.stream().filter(r -> r.estat() == Estat.CORRECTA).count();
        System.out.println(correctes + " de " + resultats.size() + " feines correctes");
    }
}
