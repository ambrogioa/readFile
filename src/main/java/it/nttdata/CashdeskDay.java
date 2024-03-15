package it.nttdata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CashdeskDay {

    private static Map<String, String> keywordActions = new HashMap<>();

    public CashdeskDay() {
    }

    public Map<String, String> creaMappa() {
        keywordActions.put("UI_CLI_SEND_LOGIN_CODE >> {u'code': u'778899000001'}", "loginCashier");
        keywordActions.put("on_DEVICE_PROCESS_MSR_CODE({'code': '8005662138278'})", "scanProduct");
        keywordActions.put("UI_CLI_SEND_TOTAL_KEY_PRESSED", "clickTotal");
        keywordActions.put("UI_CLI_CONTANTI_PAYMENT", "clickContanti");
        keywordActions.put("UI_CLI_PAYMENT_MODE_SELECTED >> {u'paydata': {u'amount': {u'code': u''}, u'preset': 500}}", "banknote5");

        return keywordActions;
    }

    public String parseString(String returnedLogString) {
        List<String> keyStringList = new ArrayList<>();
        keyStringList.add(">> {u'code':");
        keyStringList.add("({'code': '");
        keyStringList.add("u'amount':");

        List<String> regexList = new ArrayList<>();
        regexList.add("'code': u'(\\d+)'");
        regexList.add("'(\\d+)'");
        regexList.add("'preset': (\\d+)");
        String extractCode = "!NESSUNA CORRISPONDENZA!";

        for (String lineFounded : keyStringList) {
            if (returnedLogString.contains(lineFounded)) {
                for (String applyRegex : regexList) {
                    Pattern pattern = Pattern.compile(applyRegex);
                    Matcher matcher = pattern.matcher(returnedLogString);
                    if (matcher.find()) {
                        extractCode = matcher.group(1);
                    }
                }
            }
        }
        return extractCode;
    }

    public void printLine(String value, String code) {
        System.out.println("!!!PAROLA CHIAVE TROVATA!!!" + value + "!!!CODIE ESTRATTO!!!" + code);
    }

    public void actionExecutor(String associatedKey, String codeValue) {
        switch (associatedKey) {
            case "loginCashier":
                printLine(associatedKey, codeValue);
                break;
            case "scanProduct":
                printLine(associatedKey, codeValue);
                break;
            case "clickTotal":
                printLine(associatedKey, codeValue);
                break;
            case "clickContanti":
                printLine(associatedKey, codeValue);
                break;
            case "banknote5":
                printLine(associatedKey, codeValue);
                break;

        }
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
                        //creaMappa().get(parolaChiave).run();
                        //System.out.println("---CHIAVE ASSOCIATA---" + creaMappa().get(parolaChiave));
                        //System.out.println("Riga trovata: " + riga);
                        rigaParsata = parseString(riga);
                        //System.out.println("Riga parsata: " + rigaParsata);
                        actionExecutor(creaMappa().get(parolaChiave), rigaParsata);
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

        CashdeskDay cashdeskDay = new CashdeskDay();
        cashdeskDay.readFile();

    }
}
