package de.hsrm.mi.web.projekt.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class GutesPasswortValidator implements ConstraintValidator<GutesPasswort,String> {
    protected String passwort;
    // @Override public void initialize(GutesPasswort annotationGutesPasswort){
    //     //
    //     this.passwort = annotationGutesPasswort.value();
    //     }

    @Override
    public boolean isValid(String pw, ConstraintValidatorContext context) {
        // Hier schreib etwas um passwort mit 17 oder siebzehn zu validieren
        try {
            String lowerPw = pw.toLowerCase();
            return lowerPw==null || lowerPw.contains("17") || lowerPw.contains("siebzehn") || lowerPw=="";
        } catch (Exception e) {
            // Log the exception or handle it appropriately
            e.printStackTrace(); // This is just for demonstration, replace with proper logging
            return false; // Return false to indicate validation failure
        }
    }
    
}
