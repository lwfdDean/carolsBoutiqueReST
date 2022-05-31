package co.za.carolsBoutique.boutique.service;

import co.za.carolsBoutique.codeGenerator.CodeGenerator;

public class BoutiqueIdGenerator implements CodeGenerator {//^

    @Override
    public String generateId(String mainSource, String subSource, boolean alphaNumeric) {
        StringBuilder sb = new StringBuilder();
        if (alphaNumeric) {
            int num = (int) (Math.random() * 9000 + 1000);
            for (int i = 0; i < 4; i++) {
                if (i % 2 != 0) {
                    char a = mainSource.charAt((int) (Math.random() * mainSource.length()));
                    if (!Character.isLetter(a)) {
                        i--;
                        continue;
                    }
                    if (i == 2) {
                        sb.append(num);
                    }
                    sb.append(a);
                }
                if (i % 2 != 0) {
                    char a = subSource.charAt((int) (Math.random() * subSource.length()));
                    if (!Character.isLetter(a)) {
                        i--;
                        continue;
                    }
                    if (i == 2) {
                        sb.append(num);
                    }
                    sb.append(a);
                }
            }
        } else {
            for (int i = 0; i < 8; i++) {
                if (i % 2 != 0) {
                    char a = mainSource.charAt((int) (Math.random() * mainSource.length()));
                    if (!Character.isLetter(a)) {
                        i--;
                        continue;
                    }
                    sb.append(a);
                }
                if (i % 2 == 0) {
                    char a = subSource.charAt((int) (Math.random() * subSource.length()));
                    if (!Character.isLetter(a)) {
                        i--;
                        continue;
                    }
                    sb.append(a);
                }
            }
        }
        return sb.toString();
    }

    @Override
    public String generateId(String source, boolean alphaNumeric) {
        StringBuilder sb = new StringBuilder();
        if (alphaNumeric) {
            int num = (int) (Math.random() * 9000 + 1000);
            for (int i = 0; i < 4; i++) {
                char a = source.charAt((int) (Math.random() * source.length()));
                if (!Character.isLetter(a)) {
                    i--;
                    continue;
                }
                if (i == 2) {
                    sb.append(num);
                }
                sb.append(a);
            }
        } else {
            for (int i = 0; i < 8; i++) {
                char a = source.charAt((int) (Math.random() * source.length()));
                if (!Character.isLetter(a)) {
                    i--;
                    continue;
                }
                sb.append(a);
            }
        }
        return sb.toString();
    }

}
