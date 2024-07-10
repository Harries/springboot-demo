package com.et.pf4j;

import org.pf4j.JarPluginManager;
import org.pf4j.PluginManager;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

/*	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}*/
	public static void main(String[] args) {


		// create the plugin manager
		PluginManager pluginManager = new JarPluginManager(); // or "new ZipPluginManager() / new DefaultPluginManager()"

		// start and load all plugins of application
		//pluginManager.loadPlugins();

		pluginManager.loadPlugin(Paths.get("D:\\IdeaProjects\\ETFramework\\pf4j\\pf4j-plugin-01\\target\\pf4j-plugin-01-1.0-SNAPSHOT.jar"));
		pluginManager.startPlugins();
		/*
        // retrieves manually the extensions for the Greeting.class extension point
        List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
        System.out.println("greetings.size() = " + greetings.size());
        */
		// retrieve all extensions for "Greeting" extension point
		List<Greeting> greetings = pluginManager.getExtensions(Greeting.class);
		for (Greeting greeting : greetings) {
			System.out.println(">>> " + greeting.getGreeting());
		}

		// stop and unload all plugins
		pluginManager.stopPlugins();
		//pluginManager.unloadPlugins();


	}
}
