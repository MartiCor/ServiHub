package cat.pratfp.servihub.pr1.fills;

/**
 * Primer procés de la canonada (sessió 4): escriu els números de l'1 a N, un per línia.
 *
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public class Generador {

    public static void main(String[] args) {
        int n = args.length > 0 ? Integer.parseInt(args[0]) : 10;
        for (int i = 1; i <= n; i++) {
            System.out.println(i);
        }
    }
}
