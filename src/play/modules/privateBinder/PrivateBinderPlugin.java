package play.modules.privateBinder;

import play.PlayPlugin;
import play.classloading.ApplicationClasses.ApplicationClass;

/**
 * 
 * @author rafaeldantas
 * 
 */
public class PrivateBinderPlugin extends PlayPlugin {

	@Override
	public void enhance(ApplicationClass applicationClass) throws Exception {
		new PrivateBinderEnhancer().enhanceThisClass(applicationClass);
	}
}
