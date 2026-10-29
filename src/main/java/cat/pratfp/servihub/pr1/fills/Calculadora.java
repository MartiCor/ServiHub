package cat.pratfp.servihub.pr1.fills;

/**
 * Procés fill de proves (el mateix de la sessió 4).
 * Rep l'operació pels arguments i el nom d'usuari per la variable d'entorn SERVIHUB_USUARI.
 * Acaba amb codi 1 si els arguments són incorrectes i amb codi 2 si l'operació no existeix.
 *
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public class Calculadora {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.err.println("Ús: Calculadora <suma|resta|mult> <a> <b>");
            System.exit(1);
        }
        String operacio = args[0];
        int a = Integer.parseInt(args[1]);
        int b = Integer.parseInt(args[2]);
        String usuari = System.getenv().getOrDefault("SERVIHUB_USUARI", "anònim");

        int resultat = switch (operacio) {
            case "suma" -> a + b;
            case "resta" -> a - b;
            case "mult" -> a * b;
            default -> {
                System.err.println("Operació desconeguda: " + operacio);
                System.exit(2);
                yield 0;
            }
        };
        System.out.println("[" + usuari + "] " + operacio + "(" + a + ", " + b + ") = " + resultat);
    }
}
