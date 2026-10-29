package cat.pratfp.servihub.pr1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * ServiHub v1 · el llançador multiprocés.
 * <p>
 * Cada mètode llança un o més processos fills amb {@link ProcessBuilder} i en controla el resultat.
 * Hi ha <b>20 forats</b> marcats amb {@code TODO FORAT xx}: substituïu cada {@code Forat.n(xx)} per la línia de codi correcta.
 */
public final class Llancador {

    private Llancador() {}

    // =====================================================================
    // BLOC A · Llançar un procés i esperar-lo (forats 01, 02 i 03) · proves A1-A3
    // =====================================================================

    /**
     * Llança un procés, n'espera el final i en retorna el codi de retorn.
     * El fill escriu directament a la consola del pare.
     *
     * @param ordre programa i arguments
     * @return el codi de retorn del fill
     * @throws IOException si el programa no es pot llançar (per exemple, si no existeix)
     */
    public static int executa(String... ordre) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(ordre);
        // TODO FORAT 01 · Fes que el fill escrigui directament a la consola del pare (sessió 2, Ex2LlancaIEspera).
        Forat.n(1);
        // TODO FORAT 02 · Llança el procés fill i desa'l a la variable p.
        Process p = Forat.n(2);
        // TODO FORAT 03 · Espera que el fill acabi i retorna el seu codi de retorn.
        return Forat.n(3);
    }

    // =====================================================================
    // BLOC B · Llegir la sortida d'un procés (forat 04) · proves B1-B2
    // =====================================================================

    /**
     * Llegeix un flux de text sencer, línia a línia, fins al final (EOF).
     * Les línies es retornen unides amb "\n" i sense salt de línia al final. Si el flux és buit, retorna "".
     *
     * @param flux el flux que cal llegir (per exemple, p.getInputStream())
     * @return tot el text llegit
     */
    public static String llegeixTot(InputStream flux) throws IOException {
        StringJoiner text = new StringJoiner("\n");
        try (BufferedReader r = new BufferedReader(new InputStreamReader(flux, StandardCharsets.UTF_8))) {
            String linia;
            // TODO FORAT 04 · Llegeix la línia següent del lector r. Quan arriba l'EOF, ha de valdre null.
            while ((linia = Forat.n(4)) != null) {
                text.add(linia);
            }
        }
        return text.toString();
    }

    // =====================================================================
    // BLOC C · Codi de retorn (forat 05 a Estat.java) · proves C1-C2
    // =====================================================================

    // El forat 05 és a la classe Estat, mètode segonsCodi.

    // =====================================================================
    // BLOC D · Capturar la sortida i l'error per separat (forats 06, 07 i 08) · proves D1-D3
    // =====================================================================

    /**
     * Llança un procés, en captura la sortida estàndard i la d'error per separat i n'espera el final.
     * Si el programa no es pot llançar, retorna {@link Resultat#noArrencada}.
     *
     * @param nom   nom de la feina
     * @param ordre programa i arguments
     * @return el resultat complet del procés
     */
    public static Resultat executaICaptura(String nom, String... ordre) throws InterruptedException {
        long inici = System.currentTimeMillis();
        Process p;
        try {
            p = new ProcessBuilder(ordre).start();
        } catch (IOException e) {
            // TODO FORAT 06 · El programa no s'ha pogut llançar: retorna un Resultat de feina no arrencada amb el missatge de l'excepció.
            return Forat.n(6);
        }
        FilLector out = FilLector.engega(p.getInputStream());
        // TODO FORAT 07 · Engega un segon FilLector que buidi la sortida d'ERROR del fill.
        FilLector err = Forat.n(7);
        int codi = p.waitFor();
        out.join();
        err.join();
        // TODO FORAT 08 · Desa a pid l'identificador del procés fill (no el del pare).
        long pid = Forat.n(8);
        long durada = System.currentTimeMillis() - inici;
        return new Resultat(nom, pid, codi, Estat.segonsCodi(codi), durada, out.text(), err.text());
    }

    // =====================================================================
    // BLOC E · Passar variables d'entorn al fill (forat 09) · proves E1-E2
    // =====================================================================

    /**
     * Llança un procés afegint-li variables d'entorn i en retorna la sortida estàndard.
     *
     * @param entorn variables que s'afegeixen a l'entorn del fill
     * @param ordre  programa i arguments
     * @return la sortida estàndard del fill
     */
    public static String executaAmbEntorn(Map<String, String> entorn, String... ordre)
            throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder(ordre);
        // TODO FORAT 09 · Afegeix TOTES les variables del mapa entorn a l'entorn del fill (abans de start()).
        Forat.n(9);
        Process p = pb.start();
        String sortida = llegeixTot(p.getInputStream());
        p.waitFor();
        return sortida;
    }

    // =====================================================================
    // BLOC F · Escriure a l'entrada estàndard del fill (forats 10 i 11) · proves F1-F2
    // =====================================================================

    /**
     * Envia línies a l'entrada estàndard d'un procés i en retorna la sortida estàndard.
     *
     * @param linies línies que s'envien al fill, una per línia
     * @param ordre  programa i arguments
     * @return la sortida estàndard del fill
     */
    public static String enviaPerEntrada(List<String> linies, String... ordre)
            throws IOException, InterruptedException {
        Process p = new ProcessBuilder(ordre).start();
        // TODO FORAT 10 · Posa-hi el flux pel qual el pare ESCRIU a l'entrada estàndard del fill.
        PrintWriter capAlFill = new PrintWriter(new OutputStreamWriter(Forat.n(10), StandardCharsets.UTF_8), true);
        for (String linia : linies) {
            capAlFill.println(linia);
        }
        // TODO FORAT 11 · Ja no hi ha més línies: fes que el fill rebi l'EOF (si no, es queda esperant per sempre).
        Forat.n(11);
        String sortida = llegeixTot(p.getInputStream());
        p.waitFor();
        return sortida;
    }

    // =====================================================================
    // BLOC G · Temps màxim (forats 12, 13 i 14) · proves G1-G3
    // =====================================================================

    /**
     * Llança un procés i li dona un temps màxim per acabar.
     * Si no acaba a temps, es demana que acabi; si en 2 segons encara és viu, es mata a la força.
     *
     * @param nom    nom de la feina
     * @param segons temps màxim d'execució
     * @param ordre  programa i arguments
     * @return el resultat del procés (sortida i error buits)
     */
    public static Resultat executaAmbTempsMaxim(String nom, long segons, String... ordre)
            throws IOException, InterruptedException {
        long inici = System.currentTimeMillis();
        Process p = new ProcessBuilder(ordre).inheritIO().start();
        // TODO FORAT 12 · Espera el fill com a màxim «segons» segons. haAcabat ha de dir si ha acabat dins del temps.
        boolean haAcabat = Forat.n(12);
        Estat estat;
        if (haAcabat) {
            estat = Estat.segonsCodi(p.exitValue());
        } else {
            // TODO FORAT 13 · S'ha passat de temps: demana-li amablement que acabi.
            Forat.n(13);
            if (!p.waitFor(2, TimeUnit.SECONDS)) {
                // TODO FORAT 14 · Al cap de 2 segons encara és viu: mata'l a la força.
                Forat.n(14);
                p.waitFor();
            }
            estat = Estat.AVORTADA;
        }
        long durada = System.currentTimeMillis() - inici;
        return new Resultat(nom, p.pid(), p.exitValue(), estat, durada, "", "");
    }

    // =====================================================================
    // BLOC H · Diverses feines en paral·lel (forats 15 i 16) · proves H1-H2
    // =====================================================================

    /**
     * Executa diverses feines EN PARAL·LEL: primer les llança totes i després les espera totes.
     * Els resultats es retornen en el mateix ordre que les feines.
     *
     * @param feines les feines que cal executar
     * @return un resultat per feina, en el mateix ordre
     */
    public static List<Resultat> executaEnParallel(List<Feina> feines) throws IOException, InterruptedException {
        long inici = System.currentTimeMillis();
        List<Process> fills = new ArrayList<>();
        for (Feina f : feines) {
            // TODO FORAT 15 · Llança la feina f (amb inheritIO) i afegeix el procés a la llista fills. Aquí NO l'esperis.
            Forat.n(15);
        }
        List<Resultat> resultats = new ArrayList<>();
        for (int i = 0; i < feines.size(); i++) {
            Process p = fills.get(i);
            // TODO FORAT 16 · Ara sí: espera que aquest fill acabi i desa'n el codi de retorn.
            int codi = Forat.n(16);
            long durada = System.currentTimeMillis() - inici;
            resultats.add(new Resultat(feines.get(i).nom(), p.pid(), codi, Estat.segonsCodi(codi), durada, "", ""));
        }
        return resultats;
    }

    // =====================================================================
    // BLOC I · Sortida i error a fitxers (forats 17 i 18) · proves I1-I2
    // =====================================================================

    /**
     * Llança un procés i en desa la sortida estàndard a {@code <carpeta>/<nom>.out} i l'error a
     * {@code <carpeta>/<nom>.err}. Si la carpeta no existeix, es crea. El directori de treball del fill
     * és aquesta mateixa carpeta.
     *
     * @param nom     nom de la feina (i dels fitxers)
     * @param carpeta carpeta on es desen els fitxers
     * @param ordre   programa i arguments
     * @return el resultat del procés (sortida i error buits: són als fitxers)
     */
    public static Resultat executaAFitxers(String nom, Path carpeta, String... ordre)
            throws IOException, InterruptedException {
        long inici = System.currentTimeMillis();
        Files.createDirectories(carpeta);
        ProcessBuilder pb = new ProcessBuilder(ordre);
        // TODO FORAT 17 · Fes que el directori de treball del fill sigui la carpeta.
        Forat.n(17);
        // TODO FORAT 18 · Redirigeix la sortida ESTÀNDARD del fill al fitxer <carpeta>/<nom>.out (l'error ja està fet a sota).
        Forat.n(18);
        pb.redirectError(carpeta.resolve(nom + ".err").toFile());
        Process p = pb.start();
        int codi = p.waitFor();
        long durada = System.currentTimeMillis() - inici;
        return new Resultat(nom, p.pid(), codi, Estat.segonsCodi(codi), durada, "", "");
    }

    // =====================================================================
    // BLOC J · Canonada entre processos (forat 19) · proves J1-J2
    // =====================================================================

    /**
     * Encadena processos amb una canonada (com A | B | C a la consola): la sortida de cada etapa
     * és l'entrada de la següent. Retorna la sortida de la DARRERA etapa.
     *
     * @param etapes l'ordre de cada etapa, en ordre
     * @return la sortida de la darrera etapa
     */
    public static String canonada(List<String[]> etapes) throws IOException, InterruptedException {
        List<ProcessBuilder> builders = new ArrayList<>();
        for (String[] ordre : etapes) {
            builders.add(new ProcessBuilder(ordre));
        }
        // TODO FORAT 19 · Llança totes les etapes de cop, connectades entre elles (sessió 4, Ex4Canonada).
        List<Process> processos = Forat.n(19);
        Process ultim = processos.get(processos.size() - 1);
        String sortida = llegeixTot(ultim.getInputStream());
        for (Process p : processos) {
            p.waitFor();
        }
        return sortida;
    }

    // =====================================================================
    // BLOC K · El procés pare (forat 20) · prova K1
    // =====================================================================

    /**
     * Retorna el PID del procés que està executant ServiHub (el pare de tots els fills).
     *
     * @return el PID del procés actual
     */
    public static long pidDeServiHub() {
        // TODO FORAT 20 · Retorna el PID del procés actual (sessió 2, Ex1ProcessosDelSistema).
        return Forat.n(20);
    }
}
