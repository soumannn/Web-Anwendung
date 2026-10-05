package de.hsrm.mi.web.projekt.validators;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GutesPasswortValidator.class)
@Documented
public @interface GutesPasswort {
    String message() default "{gutespasswort.fehler}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};

}

