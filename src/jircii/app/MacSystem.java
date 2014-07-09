package jircii.app;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/** Uses reflection and dynamic proxy to register application handlers with OSX */
public class MacSystem extends NativeSystem {
	private Object application;

	private Class<?> classApplication;
	private Class<?> classAboutHandler;
	private Class<?> classPreferencesHandler;
	private Class<?> classQuitHandler;

	private Method methodGetApplication;
	private Method methodSetAboutHandler;
	private Method methodSetPreferencesHandler;
	private Method methodSetQuitHandler;
	private Method methodRequestUserAttention;

	public MacSystem() {
		System.setProperty("apple.laf.useScreenMenuBar", "true");
		System.setProperty("com.apple.macos.smallTabs", "true");
		System.setProperty("com.apple.mrj.application.apple.menu.about.name", "jIRCii");
		initReflection();
	}
	
	private void initReflection() throws IllegalStateException {
		try {
			classApplication = Class.forName("com.apple.eawt.Application");
			classAboutHandler = Class.forName("com.apple.eawt.AboutHandler");
			classPreferencesHandler = Class.forName("com.apple.eawt.PreferencesHandler");
			classQuitHandler = Class.forName("com.apple.eawt.QuitHandler");
			
			methodGetApplication = classApplication.getMethod("getApplication");
			methodSetAboutHandler = classApplication.getMethod("setAboutHandler", classAboutHandler);
			methodSetPreferencesHandler = classApplication.getMethod("setPreferencesHandler", classPreferencesHandler);
			methodSetQuitHandler = classApplication.getMethod("setQuitHandler", classQuitHandler);
			methodRequestUserAttention = classApplication.getMethod("requestUserAttention", new Class[] { boolean.class });
			
			application = methodGetApplication.invoke(null);
		} catch (Throwable t) {
			throw new IllegalStateException("Unable to initialize Mac System", t);
		}
	}

	@Override
	public void registerCapabilities(final SystemCapabilities capabilities) throws IllegalStateException {
		Class<?>[] proxyInterfaces = new Class[] { classAboutHandler, classPreferencesHandler, classQuitHandler };
		InvocationHandler proxyInvoker = new InvocationHandler() {
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if ("handleAbout".equals(method.getName())) {
					capabilities.showAbout();
				} else if ("handlePreferences".equals(method.getName())) {
					capabilities.showPreferences(null);
				} else if ("handleQuitRequestWith".equals(method.getName())) {
					capabilities.quit(new MacSystemQuitHandler(args[1]));
				} else {
					return method.invoke(proxy, args);
				}
				return null;
			}
		};

		try {
			Object proxy = Proxy.newProxyInstance(classApplication.getClassLoader(), proxyInterfaces, proxyInvoker);
			methodSetAboutHandler.invoke(application, proxy);
			methodSetPreferencesHandler.invoke(application, proxy);
			methodSetQuitHandler.invoke(application, proxy);
		} catch (Throwable t) {
			throw new IllegalStateException("Unable to register Proxy Application Handlers with Mac System", t);
		}
	}

	@Override
	public void requestAttention(boolean critical) throws IllegalStateException {
		try {
			methodRequestUserAttention.invoke(application, new Object[] { critical });
		} catch (Throwable t) {
			throw new IllegalStateException("Unable to request attention in Mac System", t);
		}
	}

	private static final class MacSystemQuitHandler implements SystemQuitHandler {
		private final Object quitHandler;
		private boolean invoked = false;

		private MacSystemQuitHandler(Object quitHandler) {
			this.quitHandler = quitHandler;
		}

		@Override
		public final void performQuit() {
			invoke("performQuit");
		}

		@Override
		public final void cancelQuit() {
			invoke("cancelQuit");
		}

		private synchronized final void invoke(String method) {
			if (invoked) {
				throw new IllegalStateException("This QuitHandler has previously been invoked");
			}
			try {
				quitHandler.getClass().getMethod(method).invoke(quitHandler);
				invoked = true;
			} catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}
}
