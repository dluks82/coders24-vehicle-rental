package dev.dluks.rental.model.validator.document;

public class SanitizeDocument {

    public static String sanitizeDocument(String document){
        document = document.replaceAll("\\D", "");
        return document;
    };
}
