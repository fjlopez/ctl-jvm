package es.unizar.iaaa.ctl.xml;

public interface ContextProvider extends Context {

	public abstract void push();

	public abstract void pop();

}