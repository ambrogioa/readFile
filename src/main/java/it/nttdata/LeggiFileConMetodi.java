package it.nttdata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LeggiFileConMetodi {

    public LeggiFileConMetodi() {
    }

    private static Map<String, Runnable> keywordActions = new HashMap<>();

    public static Map<String, Runnable> creaMappa() {
        /*        keywordActions.put("UI_CLI_SEND_LOGIN_CODE >> {u'code': u'778899000001'}",
                List.of(
                        () -> System.out.println("Trovata parola chiave 'UI_CLI_SEND_LOGIN_CODE >> {u'code': u'778899000001'}"),
                        () -> System.out.println("Pippo")
                ));*/
        keywordActions.put("UI_CLI_SEND_LOGIN_CODE >> {u'code': u'778899000001'}",
                () -> System.out.println("on_DEVICE_PROCESS_MSR_CODE({'code': '8005662138278'})"));
        keywordActions.put("on_DEVICE_PROCESS_MSR_CODE({'code': '8005662138278'})",
                () -> System.out.println("on_DEVICE_PROCESS_MSR_CODE({'code': '8005662138278'})"));
        keywordActions.put("UI_CLI_SEND_TOTAL_KEY_PRESSED",
                () -> System.out.println("Trovata parola chiave 'UI_CLI_SEND_TOTAL_KEY_PRESSED'"));
        keywordActions.put("UI_CLI_CONTANTI_PAYMENT",
                () -> System.out.println("Trovata parola chiave 'UI_CLI_CONTANTI_PAYMENT'"));
        keywordActions.put("UI_CLI_PAYMENT_MODE_SELECTED >> {u'paydata': {u'amount': {u'code': u''}, u'preset': 500}}",
                () -> System.out.println("Trovata parola chiave UI_CLI_PAYMENT_MODE_SELECTED >> {u'paydata': {u'amount': {u'code': u''}, u'preset': 500}}"));

        return keywordActions;
    }

    public String parseString(String returnedLogStrin){
        String keyString_1 = "u'code': u'";
        String regex = "'code': u'(\\d+)'";
        String extractCode = null;

        if (returnedLogStrin.contains(keyString_1)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(returnedLogStrin);
            if(matcher.find()){extractCode = matcher.group(1);}

        }
        return extractCode;
    }

    public void readFile() {

        String percorsoFile = "src/test/resources/log/mainOrkpos-2024-03-04_138.tra";

        FileInputStream inputStream = null;
        Scanner scanner = null;
        try {

            inputStream = new FileInputStream(percorsoFile);
            scanner = new Scanner(inputStream, "UTF-8");

            System.out.println("Contenuto del file:");

            while (scanner.hasNextLine()) {
                String riga = scanner.nextLine();
                String rigaParsata = null;

                for (String parolaChiave : creaMappa().keySet()) {
                    if (riga.contains(parolaChiave)) {
                        creaMappa().get(parolaChiave).run();
                        System.out.println("Riga trovata: " + riga);
                        rigaParsata = parseString(riga);
                        System.out.println("Riga parsata: " + rigaParsata);
                    }
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.err.println("Errore: File non trovato.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        LeggiFileConMetodi leggiFileConMetodi = new LeggiFileConMetodi();
        leggiFileConMetodi.readFile();

    }
}
