package cat.pratfp.servihub.pr1;

import java.io.IOException;
import java.io.InputStream;

/**
 * Fil que buida un flux d'un procés fill en segon pla (com el llegeixEnSegonPla de la sessió 2).
 * Serveix per llegir la sortida estàndard i la d'error alhora sense que el fill es bloquegi
 * perquè té la memòria intermèdia plena.
 * <p>
 * Ús: {@code FilLector out = FilLector.engega(p.getInputStream());} ... {@code p.waitFor(); out.join(); out.text();}
 * <p>
 * NO CAL MODIFICAR AQUESTA CLASSE (però fa servir el vostre {@link Llancador#llegeixTot}).
 */
public final class FilLector extends Thread {

    private final InputStream flux;
    private volatile String text = "";

    private FilLector(InputStream flux) {
        this.flux = flux;
        setDaemon(true);
    }

    /** Crea el fil lector i l'engega. */
    public static FilLector engega(InputStream flux) {
        FilLector f = new FilLector(flux);
        f.start();
        return f;
    }

    @Override
    public void run() {
        try {
            text = Llancador.llegeixTot(flux);
        } catch (IOException | RuntimeException e) {
            // RuntimeException: si llegeixTot encara és un forat buit, el fil no peta i el missatge queda al text.
            text = "error llegint: " + e.getMessage();
        }
    }

    /** Tot el que s'ha llegit. Crideu-lo després de join(). */
    public String text() {
        return text;
    }
}
