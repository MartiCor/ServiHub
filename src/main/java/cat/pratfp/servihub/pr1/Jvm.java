package cat.pratfp.servihub.pr1;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Utilitat per llançar classes d'aquest projecte com a processos fills (la de la sessió 2, millorada).
 * <ul>
 *   <li>Fa servir el mateix executable java que està executant el programa.</li>
 *   <li>Passa al fill la carpeta de classes compilades amb ruta absoluta: funciona igual des d'IntelliJ,
 *       des de Maven i des dels tests, a Windows, Linux i macOS.</li>
 *   <li>Obliga el fill a escriure en UTF-8, així els accents arriben bé al pare.</li>
 * </ul>
 * NO CAL MODIFICAR AQUESTA CLASSE.
 */
public final class Jvm {

    private Jvm() {}

    /** Ruta de l'executable java que està executant aquest programa. */
    public static String executable() {
        return ProcessHandle.current().info().command().orElse("java");
    }

    /** L'ordre completa per executar una classe del projecte amb els arguments donats. */
    public static String[] ordre(Class<?> classe, String... args) {
        List<String> ordre = new ArrayList<>();
        ordre.add(executable());
        ordre.add("-Dstdout.encoding=UTF-8");
        ordre.add("-Dstderr.encoding=UTF-8");
        ordre.add("-cp");
        ordre.add(carpetaDeClasses(classe));
        ordre.add(classe.getName());
        ordre.addAll(List.of(args));
        return ordre.toArray(new String[0]);
    }

    /** Carpeta (o JAR) d'on s'ha carregat la classe, amb ruta absoluta. */
    private static String carpetaDeClasses(Class<?> classe) {
        try {
            return Path.of(classe.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .toAbsolutePath().toString();
        } catch (URISyntaxException e) {
            throw new IllegalStateException("No trobo les classes del projecte", e);
        }
    }
}
