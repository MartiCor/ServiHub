package cat.pratfp.servihub.pr1;

/**
 * Una feina que ServiHub ha d'executar: un nom per identificar-la i l'ordre que llança el procés.
 * NO CAL MODIFICAR AQUESTA CLASSE.
 *
 * @param nom   nom curt de la feina (surt a l'informe)
 * @param ordre programa i arguments, com els rep {@link ProcessBuilder}
 */
public record Feina(String nom, String... ordre) {
}
