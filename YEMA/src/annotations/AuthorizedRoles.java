package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


//Permite usar esta anotacion sobre clases
@Target(ElementType.TYPE)
//Permite leer esta anotacion mientras el programa se esta ejecutando
@Retention(RetentionPolicy.RUNTIME)

// se usa asi -> @AuthorizedRoles({"admin","jugador"})
public @interface AuthorizedRoles {
    String[] roles();
}