package jircii.app;

public class SystemEnvironment {
	public static enum OperatingSystem {
		Unknown, Mac, Windows, Linux;
	}
	
	public static enum JreBitSystem {
		_32Bit, _64Bit;
	}
	
	public static JreBitSystem getJreBitSystem() {
		String bitStr = System.getProperty("os.arch");
		if (bitStr.indexOf("64") > -1) {
			return JreBitSystem._64Bit;
		}
		return JreBitSystem._32Bit;
	}
	
	public static OperatingSystem getOperatingSystem() {
		String osStr = System.getProperty("os.name").toLowerCase();
		if (osStr.indexOf("mac") > -1) {
			return OperatingSystem.Mac;
		} else if (osStr.indexOf("win") > -1) {
			return OperatingSystem.Windows;
		} else if (osStr.indexOf("linux") > -1) {
			return OperatingSystem.Linux;
		}
		return OperatingSystem.Unknown;
	}
}
