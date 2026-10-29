# PR1 · ServiHub v1 · Els 20 forats del llançador

MP0490 Programació de serveis i processos · DAM2 · curs 2026-27 · PratFP

**Alumne/a:** _escriu aquí el teu nom i cognoms_

Aquest projecte **ja compila**, però li falten **20 línies de codi**. Cada línia que falta és un
**forat**. Quan els omplis tots, el programa funcionarà i les **25 proves** de `LlancadorTest` sortiran en verd.
Tot el que cal es va fer a classe a les sessions 2 i 4 del RA1.

## Com és un forat

Cada forat té un comentari `TODO FORAT xx` que diu què ha de fer la línia, i a sota una crida
`Forat.n(xx)` que has de **substituir** per la teva línia de codi:

```java
// TODO FORAT 13 · S'ha passat de temps: demana-li amablement que acabi.
Forat.n(13);
```

Si executes el programa amb un forat buit, surt el missatge `FORAT 13 per completar`.
Quan acabis, al codi no hi ha de quedar cap `Forat.n(...)`.

## Com començar

1. Descomprimeix `servihub-pr1.zip` i obre la carpeta amb **IntelliJ IDEA** (projecte Maven, Java 21).
2. Escriu el teu nom a la línia **Alumne/a** d'aquest README.
3. Crea un repositori **nou** al teu compte de GitHub amb el nom `0490-pr1-servihub` i puja-hi el projecte
   (primer commit: `PR1 punt de partida`).
4. Obre `src/test/java/.../LlancadorTest.java` i prem el triangle verd ▶: al principi totes les proves surten en vermell.
5. Busca els forats: *View › Tool Windows › TODO* o cerca `TODO FORAT`.
6. Omple un bloc, torna a executar les proves, fes **commit i push**, i passa al bloc següent.
7. Al final executa `App.java`: no hi ha d'haver cap línia `[pendent]`.

Des de la consola també pots fer `mvn test`.

## Els 20 forats

| Bloc | On | Forats | Què fa el bloc | Proves |
|---|---|---|---|---|
| A | `Llancador.executa` | 01 · 02 · 03 | Llançar un procés i esperar-ne el codi de retorn | A1-A3 |
| B | `Llancador.llegeixTot` | 04 | Llegir un flux línia a línia fins a l'EOF | B1-B2 |
| C | `Estat.segonsCodi` | 05 | Convertir el codi de retorn en un estat | C1-C2 |
| D | `Llancador.executaICaptura` | 06 · 07 · 08 | Capturar sortida i error per separat | D1-D3 |
| E | `Llancador.executaAmbEntorn` | 09 | Passar variables d'entorn al fill | E1-E2 |
| F | `Llancador.enviaPerEntrada` | 10 · 11 | Escriure a l'entrada del fill i enviar-li l'EOF | F1-F2 |
| G | `Llancador.executaAmbTempsMaxim` | 12 · 13 · 14 | Temps màxim, `destroy` i `destroyForcibly` | G1-G3 |
| H | `Llancador.executaEnParallel` | 15 · 16 | Llançar-ho tot i després esperar-ho tot | H1-H2 |
| I | `Llancador.executaAFitxers` | 17 · 18 | Directori de treball i sortida a un fitxer | I1-I3 |
| J | `Llancador.canonada` | 19 | Encadenar processos amb una canonada | J1-J2 |
| K | `Llancador.pidDeServiHub` | 20 | El PID del procés pare | K1 |

Les proves d'un bloc es posen en verd quan **tots** els forats d'aquell bloc estan bé.

## Normes

- Només pots canviar les 20 línies `Forat.n(...)`. **No modifiquis** `LlancadorTest` ni la resta del codi:
  el docent executarà les proves amb la seva còpia original.
- Cada forat és **una sola línia** de codi feta amb `ProcessBuilder`, `Process` o `ProcessHandle`.
  No escriguis resultats a mà perquè passin les proves.
- Si una prova es queda penjada 30 segons i falla per *timeout*, pensa en l'interbloqueig de la sessió 4.
- Fes com a mínim **un commit per bloc** amb un missatge que digui quins forats has fet
  (per exemple `Bloc D · forats 06-08`).

## Lliurament

A la tasca de l'aula d'Alexia Classroom només has d'enganxar **la URL del teu repositori**
(per exemple `https://github.com/el-teu-usuari/0490-pr1-servihub`).

- El repositori ha de ser **públic**. Si el vols privat, afegeix l'usuari `ilopez000` com a col·laborador.
- No hi pugis la carpeta `target` (ja està al `.gitignore`).
- Es corregeix l'**últim commit** fet abans de la data límit.
