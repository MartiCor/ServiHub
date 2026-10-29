package cat.pratfp.servihub.pr1.fills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Segon procés de la canonada (sessió 4): deixa passar només els números parells.
 *
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public class Filtre {

    public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        String linia;
        while ((linia = entrada.readLine()) != null) {
            if (Integer.parseInt(linia.trim()) % 2 == 0) {
                System.out.println(linia);
            }
        }
    }
}
