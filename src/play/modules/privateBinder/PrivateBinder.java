package play.modules.privateBinder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks classes (in this case all private fields without setter will have
 * setters generated) or private fields that should have setters injected into
 * the bytecode.
 * 
 * @author rafaeldantas
 * 
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PrivateBinder {
}
