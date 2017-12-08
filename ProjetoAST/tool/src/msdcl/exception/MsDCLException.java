package msdcl.exception;

import org.eclipse.jdt.core.ICompilationUnit;

public class MsDCLException extends Exception {
	private static final long serialVersionUID = 1L;
	private Exception e;
	private ICompilationUnit unit;
	
	public MsDCLException(Exception e, ICompilationUnit unit) {
		this.e = e;
		this.unit = unit;
	}
	
	@Override
	public String toString() {
		return "MsDCLException: " + this.getLocalizedMessage() + "\n" + e.getLocalizedMessage(); 
	}
	
	@Override
	public String getLocalizedMessage() {
		return "Problem detected when visiting " + this.unit.toString();
	}
	
	
	@Override
	public StackTraceElement[] getStackTrace() {
		return this.e.getStackTrace();
	}
	
}
