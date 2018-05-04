package no.nrk.kryssklipp;

import org.apache.camel.CamelContext;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws Exception {
        String propertyfile = "config/kryssklipp-prod.properties";
        logger.info("Starting Radioarkiv Programflyt with propertyfile {}", propertyfile);
        CamelContext context = new DefaultCamelContext();
        PropertiesComponent propComponent = new PropertiesComponent("classpath:" + propertyfile);
        context.addComponent("properties", propComponent);

        Properties props = new Properties();
        props.load(Main.class.getClassLoader().getResourceAsStream(propertyfile));

        context.addRoutes(new KryssklippRoute(props));
        context.start();
    }
}
