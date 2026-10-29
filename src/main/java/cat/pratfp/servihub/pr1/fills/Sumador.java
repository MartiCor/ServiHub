package cat.pratfp.servihub.pr1.fills;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Darrer procés de la canonada (sessió 4): compta i suma tots els números que li arriben.
 *
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public class Sumador {

    public static void main(String[] args) throws IOException {
        BufferedReader entrada = new BufferedReader(new InputStreamReader(System.in));
        int quants = 0;
        long suma = 0;
        String linia;
        while ((linia = entrada.readLine()) != null) {
            quants++;
            suma += Long.parseLong(linia.trim());
        }
        System.out.println("He rebut " + quants + " numeros i sumen " + suma);
    }
}
