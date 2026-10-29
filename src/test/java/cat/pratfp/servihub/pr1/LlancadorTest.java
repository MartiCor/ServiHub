package cat.pratfp.servihub.pr1;

import cat.pratfp.servihub.pr1.fills.Calculadora;
import cat.pratfp.servihub.pr1.fills.Filtre;
import cat.pratfp.servihub.pr1.fills.Generador;
import cat.pratfp.servihub.pr1.fills.Majuscules;
import cat.pratfp.servihub.pr1.fills.OnSoc;
import cat.pratfp.servihub.pr1.fills.Sumador;
import cat.pratfp.servihub.pr1.fills.Tasca;
import cat.pratfp.servihub.pr1.fills.Tossut;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Proves de la PR1. Les proves estan agrupades per blocs (A-K) i cada bloc diu quins forats comprova.
 * Un bloc es posa en verd quan TOTS els seus forats estan bé.
 * NO S'HAN DE MODIFICAR: el docent les tornarà a executar amb aquesta mateixa versió.
 */
@DisplayName("PR1 · ServiHub v1 · 20 forats")
@TestMethodOrder(MethodOrderer.DisplayName.class)
@Timeout(value = 30, unit = TimeUnit.SECONDS, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
class LlancadorTest {

    // ---------- BLOC A · executa · forats 01, 02, 03 ----------

    @Test
    @DisplayName("A1 · forats 01-03 · executa retorna 0 si el fill acaba bé")
    void executaRetornaZero() throws Exception {
        assertEquals(0, Llancador.executa(Jvm.ordre(Tasca.class, "bona", "0")));
    }

    @Test
    @DisplayName("A2 · forats 01-03 · executa retorna el codi d'error del fill")
    void executaRetornaCodiError() throws Exception {
        assertEquals(2, Llancador.executa(Jvm.ordre(Tasca.class, "malament", "0")));
    }

    @Test
    @DisplayName("A3 · forats 01-03 · executa llança IOException si el programa no existeix")
    void executaProgramaInexistent() {
        assertThrows(IOException.class, () -> Llancador.executa("programa-que-no-existeix-pr1"));
    }

    // ---------- BLOC B · llegeixTot · forat 04 ----------

    @Test
    @DisplayName("B1 · forat 04 · llegeixTot uneix les línies amb \\n")
    void llegeixTotUneixLinies() throws Exception {
        var flux = new ByteArrayInputStream("una\r\ndues\ntres\n".getBytes(StandardCharsets.UTF_8));
        assertEquals("una\ndues\ntres", Llancador.llegeixTot(flux));
    }

    @Test
    @DisplayName("B2 · forat 04 · llegeixTot d'un flux buit retorna \"\"")
    void llegeixTotBuit() throws Exception {
        assertEquals("", Llancador.llegeixTot(new ByteArrayInputStream(new byte[0])));
    }

    // ---------- BLOC C · Estat.segonsCodi · forat 05 ----------

    @Test
    @DisplayName("C1 · forat 05 · codi 0 és CORRECTA")
    void codiZeroCorrecta() {
        assertEquals(Estat.CORRECTA, Estat.segonsCodi(0));
    }

    @Test
    @DisplayName("C2 · forat 05 · qualsevol altre codi és FALLIDA")
    void altresCodisFallida() {
        assertEquals(Estat.FALLIDA, Estat.segonsCodi(1));
        assertEquals(Estat.FALLIDA, Estat.segonsCodi(2));
        assertEquals(Estat.FALLIDA, Estat.segonsCodi(143));
    }

    // ---------- BLOC D · executaICaptura · forats 06, 07, 08 ----------

    @Test
    @DisplayName("D1 · forats 06-08 · captura la sortida d'un fill que acaba bé")
    void capturaFillCorrecte() throws Exception {
        Resultat r = Llancador.executaICaptura("bona", Jvm.ordre(Tasca.class, "bona", "1"));
        assertEquals("bona", r.nom());
        assertEquals(Estat.CORRECTA, r.estat());
        assertEquals(0, r.codi());
        assertTrue(r.pid() > 0, "el PID ha de ser el del fill");
        assertNotEquals(ProcessHandle.current().pid(), r.pid(), "el PID ha de ser el del fill, no el del pare");
        assertTrue(r.sortida().contains("bona: pas 1 de 1"), "falta la sortida estàndard: " + r.sortida());
        assertTrue(r.sortida().endsWith("bona: acabo"), "la sortida ha d'acabar amb «bona: acabo»");
        assertEquals("", r.error());
        assertTrue(r.durada() >= 900, "la tasca dorm 1 s: la durada no pot ser de " + r.durada() + " ms");
    }

    @Test
    @DisplayName("D2 · forats 06-08 · separa la sortida d'error d'un fill que falla")
    void capturaFillQueFalla() throws Exception {
        Resultat r = Llancador.executaICaptura("malament", Jvm.ordre(Tasca.class, "malament", "1"));
        assertEquals(Estat.FALLIDA, r.estat());
        assertEquals(2, r.codi());
        assertEquals("malament: alguna cosa ha anat malament", r.error());
        assertFalse(r.sortida().contains("alguna cosa"), "l'error no ha d'anar a la sortida estàndard");
    }

    @Test
    @DisplayName("D3 · forats 06-08 · un programa que no existeix és NO_ARRENCADA")
    void capturaProgramaInexistent() throws Exception {
        Resultat r = Llancador.executaICaptura("fantasma", "programa-que-no-existeix-pr1");
        assertEquals(Estat.NO_ARRENCADA, r.estat());
        assertEquals(-1, r.codi());
        assertEquals("fantasma", r.nom());
    }

    // ---------- BLOC E · executaAmbEntorn · forat 09 ----------

    @Test
    @DisplayName("E1 · forat 09 · passa arguments i una variable d'entorn")
    void entornISuma() throws Exception {
        String sortida = Llancador.executaAmbEntorn(Map.of("SERVIHUB_USUARI", "prova"),
                Jvm.ordre(Calculadora.class, "suma", "7", "5"));
        assertEquals("[prova] suma(7, 5) = 12", sortida);
    }

    @Test
    @DisplayName("E2 · forat 09 · cada crida té el seu entorn")
    void entornIMultiplica() throws Exception {
        String sortida = Llancador.executaAmbEntorn(Map.of("SERVIHUB_USUARI", "dam2"),
                Jvm.ordre(Calculadora.class, "mult", "6", "7"));
        assertEquals("[dam2] mult(6, 7) = 42", sortida);
    }

    // ---------- BLOC F · enviaPerEntrada · forats 10, 11 ----------

    @Test
    @DisplayName("F1 · forats 10-11 · envia línies al fill i en llegeix la resposta")
    void entradaEstandard() throws Exception {
        String sortida = Llancador.enviaPerEntrada(List.of("hola", "servihub"), Jvm.ordre(Majuscules.class));
        assertEquals("HOLA\nSERVIHUB\n(Majuscules: linies rebudes = 2)", sortida);
    }

    @Test
    @DisplayName("F2 · forats 10-11 · sense línies, el fill rep l'EOF i acaba")
    void entradaBuida() throws Exception {
        String sortida = Llancador.enviaPerEntrada(List.of(), Jvm.ordre(Majuscules.class));
        assertEquals("(Majuscules: linies rebudes = 0)", sortida);
    }

    // ---------- BLOC G · executaAmbTempsMaxim · forats 12, 13, 14 ----------

    @Test
    @DisplayName("G1 · forats 12-14 · una feina que acaba a temps no s'avorta")
    void tempsMaximSuficient() throws Exception {
        Resultat r = Llancador.executaAmbTempsMaxim("rapida", 10, Jvm.ordre(Tasca.class, "rapida", "0"));
        assertEquals(Estat.CORRECTA, r.estat());
        assertEquals(0, r.codi());
    }

    @Test
    @DisplayName("G2 · forats 12-14 · una feina massa lenta s'avorta i no queda viva")
    void tempsMaximSuperat() throws Exception {
        Resultat r = Llancador.executaAmbTempsMaxim("lenta", 1, Jvm.ordre(Tasca.class, "lenta", "20"));
        assertEquals(Estat.AVORTADA, r.estat());
        assertTrue(r.durada() < 6000, "no s'ha respectat el temps màxim: " + r.durada() + " ms");
        boolean viu = ProcessHandle.of(r.pid()).map(ProcessHandle::isAlive).orElse(false);
        assertFalse(viu, "el procés avortat continua viu");
    }

    @Test
    @DisplayName("G3 · forats 12-14 · si destroy() no n'hi ha prou, destroyForcibly()")
    void tempsMaximTossut() throws Exception {
        Resultat r = Llancador.executaAmbTempsMaxim("tossut", 1, Jvm.ordre(Tossut.class));
        assertEquals(Estat.AVORTADA, r.estat());
        assertTrue(r.durada() < 10000, "el procés tossut s'havia d'aturar a la força: " + r.durada() + " ms");
        boolean viu = ProcessHandle.of(r.pid()).map(ProcessHandle::isAlive).orElse(false);
        assertFalse(viu, "el procés tossut continua viu");
    }

    // ---------- BLOC H · executaEnParallel · forats 15, 16 ----------

    @Test
    @DisplayName("H1 · forats 15-16 · tres feines de 2 s acaben en menys de 5 s")
    void parallelEsMesRapid() throws Exception {
        long t0 = System.currentTimeMillis();
        List<Resultat> rs = Llancador.executaEnParallel(List.of(
                new Feina("A", Jvm.ordre(Tasca.class, "A", "2")),
                new Feina("B", Jvm.ordre(Tasca.class, "B", "2")),
                new Feina("C", Jvm.ordre(Tasca.class, "C", "2"))));
        long total = System.currentTimeMillis() - t0;
        assertEquals(3, rs.size());
        assertTrue(total < 5000, "han trigat " + total + " ms: s'han executat una darrere l'altra?");
        assertTrue(rs.stream().allMatch(r -> r.estat() == Estat.CORRECTA));
    }

    @Test
    @DisplayName("H2 · forats 15-16 · resultats en ordre, PID diferents i errors detectats")
    void parallelOrdreIPids() throws Exception {
        List<Resultat> rs = Llancador.executaEnParallel(List.of(
                new Feina("u", Jvm.ordre(Tasca.class, "u", "1")),
                new Feina("malament", Jvm.ordre(Tasca.class, "malament", "0")),
                new Feina("tres", Jvm.ordre(Tasca.class, "tres", "1"))));
        assertEquals(List.of("u", "malament", "tres"), rs.stream().map(Resultat::nom).toList());
        assertEquals(3, rs.stream().mapToLong(Resultat::pid).distinct().count(), "cada feina és un procés diferent");
        assertEquals(Estat.FALLIDA, rs.get(1).estat());
        assertEquals(2, rs.get(1).codi());
        assertEquals(Estat.CORRECTA, rs.get(2).estat());
    }

    // ---------- BLOC I · executaAFitxers · forats 17, 18 ----------

    @Test
    @DisplayName("I1 · forats 17-18 · desa la sortida a <nom>.out i crea la carpeta")
    void sortidaAFitxer(@TempDir Path tmp) throws Exception {
        Path carpeta = tmp.resolve("logs").resolve("avui");
        Resultat r = Llancador.executaAFitxers("nocturna", carpeta, Jvm.ordre(Tasca.class, "nocturna", "0"));
        assertEquals(Estat.CORRECTA, r.estat());
        Path out = carpeta.resolve("nocturna.out");
        Path err = carpeta.resolve("nocturna.err");
        assertTrue(Files.exists(out), "no s'ha creat " + out);
        assertTrue(Files.exists(err), "no s'ha creat " + err);
        assertTrue(Files.readString(out).contains("nocturna: acabo"));
        assertEquals("", Files.readString(err));
    }

    @Test
    @DisplayName("I2 · forats 17-18 · desa l'error a <nom>.err")
    void errorAFitxer(@TempDir Path tmp) throws Exception {
        Resultat r = Llancador.executaAFitxers("malament", tmp, Jvm.ordre(Tasca.class, "malament", "0"));
        assertEquals(Estat.FALLIDA, r.estat());
        assertTrue(Files.readString(tmp.resolve("malament.err")).contains("alguna cosa ha anat malament"));
        assertFalse(Files.readString(tmp.resolve("malament.out")).contains("alguna cosa"));
    }

    @Test
    @DisplayName("I3 · forats 17-18 · el fill treballa dins de la carpeta")
    void directoriDeTreball(@TempDir Path tmp) throws Exception {
        Resultat r = Llancador.executaAFitxers("onsoc", tmp, Jvm.ordre(OnSoc.class));
        assertEquals(Estat.CORRECTA, r.estat());
        Path onEsAlFill = Path.of(Files.readString(tmp.resolve("onsoc.out")).trim());
        assertEquals(tmp.toRealPath(), onEsAlFill.toRealPath(), "el directori de treball del fill ha de ser la carpeta");
    }

    // ---------- BLOC J · canonada · forat 19 ----------

    @Test
    @DisplayName("J1 · forat 19 · Generador 20 | Filtre | Sumador")
    void canonadaDeTres() throws Exception {
        String sortida = Llancador.canonada(List.of(
                Jvm.ordre(Generador.class, "20"), Jvm.ordre(Filtre.class), Jvm.ordre(Sumador.class)));
        assertEquals("He rebut 10 numeros i sumen 110", sortida);
    }

    @Test
    @DisplayName("J2 · forat 19 · Generador 5 | Sumador")
    void canonadaDeDos() throws Exception {
        String sortida = Llancador.canonada(List.of(Jvm.ordre(Generador.class, "5"), Jvm.ordre(Sumador.class)));
        assertEquals("He rebut 5 numeros i sumen 15", sortida);
    }

    // ---------- BLOC K · pidDeServiHub · forat 20 ----------

    @Test
    @DisplayName("K1 · forat 20 · pidDeServiHub és el PID del procés actual")
    void pidDeServiHub() {
        assertEquals(ProcessHandle.current().pid(), Llancador.pidDeServiHub());
    }
}
