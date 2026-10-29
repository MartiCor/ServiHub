package cat.pratfp.servihub.pr1.fills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Procés fill de proves (el mateix de la sessió 4).
 * Llegeix línies de l'entrada estàndard fins a l'EOF i les retorna en majúscules.
 * Si el pare no tanca l'entrada, aquest procés es queda esperant per sempre.
 *
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public class Majuscules {

    public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        int comptador = 0;
        String linia;
        while ((linia = entrada.readLine()) != null) {
            System.out.println(linia.toUpperCase());
            comptador++;
        }
        System.out.println("(Majuscules: linies rebudes = " + comptador + ")");
    }
}
