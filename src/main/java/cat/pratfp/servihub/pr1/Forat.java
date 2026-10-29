package cat.pratfp.servihub.pr1;

/**
 * Marca un forat que encara no està fet.
 * <p>
 * Al codi de la pràctica, cada forat és una crida {@code Forat.n(numero)}. El projecte compila, però quan
 * el programa arriba a un forat buit llança una excepció que diu quin és. Substituïu cada {@code Forat.n(...)}
 * per la vostra línia de codi. Quan els 20 estiguin fets, aquesta classe ja no la farà servir ningú.
 * <p>
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public final class Forat {

    private Forat() {}

    /**
     * Forat buit: llança sempre una excepció amb el número del forat.
     *
     * @param numero número del forat (1-20)
     * @param <T>    qualsevol tipus, perquè el forat pugui anar on calgui un valor
     * @return no retorna mai
     */
    public static <T> T n(int numero) {
        throw new UnsupportedOperationException(String.format("FORAT %02d per completar", numero));
    }
}
