package co.za.carolsBoutique.codeGenerator;

public interface CodeGenerator {
    String generateId(String mainSource, String subSource, boolean alphaNumeric);
    String generateId(String source, boolean alphaNumeric);
}
