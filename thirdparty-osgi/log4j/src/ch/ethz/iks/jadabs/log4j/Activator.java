/*
 * Created on 09.06.2004
 */
package ch.ethz.iks.jadabs.log4j;

import java.io.File;
import java.net.URL;

import org.apache.log4j.PropertyConfigurator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * This Log4J Activator sets up the Log4J system as initial
 * logging mechanism.
 * 
 * Usage: specify at startup the -Dlog4j.configuration=../test/log4j.properties,
 * or from the current directory the log4j.properties is taken if it exists.
 * 
 * @author dullerm
 */
public class Activator implements BundleActivator {

    public void start(BundleContext context) throws Exception
	{
  
        String configFileName = System.getProperty("log4j.configuration");
        
        if (configFileName == null)
        {
        	configFileName = "log4j.properties";
        }
        
        URL configURL = new URL("file:" + configFileName);
        
        File file = new File(configURL.getFile());

        if (file.exists())
            PropertyConfigurator.configure(configURL);
        else
            System.out.println("could not initialize Log4J, " +
            		"missing log4j.properties");
        
    }

    public void stop(BundleContext context) throws Exception
	{
        // nothing to do
    }

}