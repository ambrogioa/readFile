package it.nttdata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LeggiFileConMetodi_Draft {

    // Mappa per associare parole chiave a insiemi di azioni
    private static Map<String, Runnable> keywordActions = new HashMap<>();

    public static void main(String[] args) {
        String percorsoFile = "src/test/resources/log/mainOrkpos-2024-03-04_138.tra"; // Modifica il percorso del tuo file

        keywordActions.put("UI_CLI_SEND_LOGIN_CODE", () -> System.out.println("Trovata parola chiave 'UI_CLI_SEND_LOGIN_CODE"));
        keywordActions.put("on_DEVICE_PROCESS_MSR_CODE", () -> System.out.println("on_DEVICE_PROCESS_MSR_CODE"));
        keywordActions.put("UI_CLI_SEND_TOTAL_KEY_PRESSED", () -> System.out.println("Trovata parola chiave 'UI_CLI_SEND_TOTAL_KEY_PRESSED'"));
        keywordActions.put("UI_CLI_CONTANTI_PAYMENT", () -> System.out.println("Trovata parola chiave 'UI_CLI_CONTANTI_PAYMENT'"));
        keywordActions.put("UI_CLI_PAYMENT_MODE_SELECTED", () -> System.out.println("Trovata parola chiave 'UI_CLI_PAYMENT_MODE_SELECTED'"));

        FileInputStream inputStream = null;
        try {

            // Creo un oggetto File
            //File file = new File(percorsoFile);
            inputStream = new FileInputStream(percorsoFile);

            // Creo un oggetto Scanner per leggere il file
            //Scanner scanner = new Scanner(file);
            Scanner scanner = new Scanner(inputStream, "UTF-8");

            System.out.println("Contenuto del file:");

            // Leggo il file riga per riga
            while (scanner.hasNextLine()) {
                String riga = scanner.nextLine();

                // Cerca parole chiave nella riga
                for (String parolaChiave : keywordActions.keySet()) {
                    if (riga.contains(parolaChiave)) {
                        // Esegui l'azione associata alla parola chiave
                        keywordActions.get(parolaChiave).run();
                        System.out.println("Riga trovata: " + riga); // Stampa l'intera riga
                    }
                }
            }

            // Chiudo lo scanner dopo aver letto tutto il file
            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Errore: File non trovato.");
            e.printStackTrace();
        }
    }
}
