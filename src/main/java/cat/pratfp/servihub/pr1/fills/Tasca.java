package cat.pratfp.servihub.pr1.fills;

/**
 * Procés fill de proves (el mateix de la sessió 2).
 * Rep un nom i uns segons: escriu per la sortida estàndard, dorm i acaba amb codi 0.
 * Si el nom comença per "mal", escriu un missatge a la sortida d'error i acaba amb codi 2.
 *
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public class Tasca {

    public static void main(String[] args) throws InterruptedException {
        String nom = args.length > 0 ? args[0] : "tasca";
        int segons = args.length > 1 ? Integer.parseInt(args[1]) : 1;

        System.out.println(nom + ": començo (PID " + ProcessHandle.current().pid() + ")");
        if (nom.startsWith("mal")) {
            System.err.println(nom + ": alguna cosa ha anat malament");
            System.exit(2);
        }
        for (int i = 1; i <= segons; i++) {
            Thread.sleep(1000);
            System.out.println(nom + ": pas " + i + " de " + segons);
        }
        System.out.println(nom + ": acabo");
    }
}
