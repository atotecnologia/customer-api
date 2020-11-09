package br.com.composable.customerapi;

import com.google.inject.Guice;
import com.google.inject.Injector;

import br.com.composable.customerapi.infra.config.AppModule;

public class App {
	
	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new AppModule());
		injector.getInstance(CustomerApiApplication.class).initialize();
	}

}
