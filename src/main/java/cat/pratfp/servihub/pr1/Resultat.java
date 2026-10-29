package cat.pratfp.servihub.pr1;

/**
 * El que en sabem, d'un procés fill, quan ja ha acabat.
 * NO CAL MODIFICAR AQUESTA CLASSE.
 *
 * @param nom     nom de la feina
 * @param pid     identificador del procés fill (-1 si no ha arrencat)
 * @param codi    codi de retorn del fill (-1 si no ha arrencat)
 * @param estat   com ha acabat: correcta, fallida, avortada o no arrencada
 * @param durada  mil·lisegons des que es llança fins que el pare en veu el final
 * @param sortida text de la sortida estàndard del fill ("" si no s'ha capturat)
 * @param error   text de la sortida d'error del fill ("" si no s'ha capturat)
 */
public record Resultat(String nom, long pid, int codi, Estat estat, long durada, String sortida, String error) {

    /** Resultat d'una feina que ni tan sols s'ha pogut llançar (IOException a start()). */
    public static Resultat noArrencada(String nom, String motiu) {
        return new Resultat(nom, -1, -1, Estat.NO_ARRENCADA, 0, "", motiu);
    }
}
