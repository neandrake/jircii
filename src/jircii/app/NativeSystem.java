package jircii.app;

import jircii.app.SystemEnvironment.JreBitSystem;
import jircii.app.SystemEnvironment.OperatingSystem;

public abstract class NativeSystem {
	protected final OperatingSystem os;
	protected final JreBitSystem bits;
	
	public NativeSystem() {
		this.os = SystemEnvironment.getOperatingSystem();
		this.bits = SystemEnvironment.getJreBitSystem();
	}
	
	public final boolean isMac() {
		return os == OperatingSystem.Mac;
	}
	
	public final boolean isWindows() {
		return os == OperatingSystem.Windows;
	}
	
	public final boolean isLinux() {
		return os == OperatingSystem.Linux;
	}
	
	public final boolean is32BitJre() {
		return bits == JreBitSystem._32Bit;
	}
	
	public final boolean is64BitJre() {
		return bits == JreBitSystem._64Bit;
	}
	
	public abstract void registerCapabilities(SystemCapabilities capabilities);
	public abstract void requestAttention(boolean critical);
	
	
	public static NativeSystem getNativeSystem() {
		OperatingSystem os = SystemEnvironment.getOperatingSystem();
		switch (os) {
		case Mac: return new MacSystem();
		case Windows: return new WindowsSystem();
		case Linux: return new LinuxSystem();
		default: return null;
		}
	}
}
