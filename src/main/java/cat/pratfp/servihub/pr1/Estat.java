package cat.pratfp.servihub.pr1;

/** Com ha acabat una feina de ServiHub. */
public enum Estat {
    /** El procés ha acabat sol amb codi 0. */
    CORRECTA,
    /** El procés ha acabat sol amb un codi diferent de 0. */
    FALLIDA,
    /** El pare ha hagut de matar el procés perquè superava el temps màxim. */
    AVORTADA,
    /** El procés no s'ha pogut ni llançar (start() ha llançat IOException). */
    NO_ARRENCADA;

    /**
     * BLOC C · Tradueix el codi de retorn d'un procés que ha acabat sol a un estat.
     *
     * @param codi el valor que retorna waitFor() o exitValue()
     * @return CORRECTA si el codi és 0; FALLIDA en qualsevol altre cas
     */
    public static Estat segonsCodi(int codi) {
        // TODO FORAT 05 · Codi 0 vol dir CORRECTA; qualsevol altre codi, FALLIDA.
        return Forat.n(5);
    }
}
