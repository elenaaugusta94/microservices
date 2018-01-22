package testeReflexion;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class TesteReflection {
	public static void main(String... args)
			throws NoSuchFieldException, IllegalAccessException, FileNotFoundException, NoSuchMethodException,
			SecurityException, InstantiationException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {

		File file = new File("/home/elena/Documentos/microservices/microservices/MsProduct/target/classes/controller/");

		try {
			URL classURL = file.toURI().toURL();
			URL[] classUrls = { classURL };
	        URLClassLoader ucl = new URLClassLoader(classUrls);
	        Class c = ucl.loadClass("ProductController");
	        Constructor cct = c.getConstructor();
	        Object obj = cct.newInstance();
			Method [] methods = c.getDeclaredMethods();
			for(Method m : methods) {
				System.out.println(m.getName());
			}
			// Field f = urlClass.getDeclaredField("ProductService");
			// System.out.println(f.getName());
			// String value = (String) f.get(urlClass);
			// System.out.println(value);
			// Load in the class; MyClass.class should be located in
			// the directory file:/c:/myclasses/com/mycompany

		} catch (MalformedURLException e) {
		}

	}
}
