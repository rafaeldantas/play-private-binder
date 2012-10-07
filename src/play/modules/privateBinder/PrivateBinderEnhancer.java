package play.modules.privateBinder;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;
import play.Logger;
import play.classloading.ApplicationClasses.ApplicationClass;
import play.classloading.enhancers.Enhancer;

/**
 * 
 * @author rafaeldantas
 * 
 */
public class PrivateBinderEnhancer extends Enhancer {
	@Override
	public void enhanceThisClass(final ApplicationClass applicationClass) throws Exception {
		final CtClass ctClass = makeClass(applicationClass);

		boolean shouldCreate = hasAnnotation(ctClass, PrivateBinder.class.getName());

		CtField[] declaredFields = ctClass.getDeclaredFields();
		for (CtField ctField : declaredFields) {
			shouldCreate = shouldCreate || hasAnnotation(ctField, PrivateBinder.class.getName());
			if (ctField.getModifiers() == Modifier.PRIVATE && shouldCreate) {
				String setterName = "set" + ctField.getName().substring(0, 1).toUpperCase() + ctField.getName().substring(1);
				try {
					ctClass.getDeclaredMethod(setterName, new CtClass[] { ctField.getType() });
				} catch (NotFoundException e) {
					Logger.info("No setter found for %s#%s , creating add one", ctClass.getSimpleName(), ctField.getName());
					CtMethod m = CtNewMethod.make("public void " + setterName + "(" + ctField.getType().getName() + " _val) { this."
							+ ctField.getName() + " = _val; }", ctClass);
					ctClass.addMethod(m);
				}
			}
		}
		applicationClass.enhancedByteCode = ctClass.toBytecode();
		ctClass.defrost();
	}
}
