package cat.pratfp.servihub.pr1.fills;

/**
 * Procés fill de proves que es fa el sord: quan el pare li demana que acabi amb destroy(), no en fa cas
 * i continua viu (a Linux i macOS). Només l'atura destroyForcibly().
 * A Windows, destroy() ja l'atura a la força.
 *
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public class Tossut {

    public static void main(String[] args) throws InterruptedException {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("tossut: m'han demanat que acabi, però no em ve de gust");
            try {
                Thread.sleep(60_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }));
        System.out.println("tossut: començo i no penso acabar");
        Thread.sleep(60_000);
    }
}
