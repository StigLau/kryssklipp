package no.nrk.kryssklipp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import no.nrk.kryssklipp.domain.Clock;
import no.nrk.kryssklipp.domain.ProgramDefinition;
import no.nrk.kryssklipp.domain.ProgramInTime;
import no.nrk.kryssklipp.jsonpresentation.Program;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

import java.io.*;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.time.Instant;
import java.util.List;
import java.util.Properties;

public class KryssklippRoute extends RouteBuilder {

    static Clock clock;
    {{
        ProgramDefinition ponniskolen = new ProgramDefinition("Ponniskolen", "MSUI16004312", "PT21M15S", "PT22M15S");
        ProgramDefinition peppaGris = new ProgramDefinition("Peppa Gris - bursdag i park", "MSUI25005616", "PT0S", "PT2M" );
        ProgramDefinition pompelOgPilt = new ProgramDefinition("Pompel Og Pilt", "FBUA01000569", "PT0S", "PT5M");

        clock = new Clock(Instant.now());
        //Clock clock = new Clock(Clock.parseTime("2017-10-23T10:00:00+02:00"));
        clock.addProgram(ponniskolen, "PT0S", "PT1M");
        clock.addProgram(peppaGris, "PT1M", "PT5M");
        clock.addProgram(pompelOgPilt, "PT6M", "PT4M");
    }
    }

    public KryssklippRoute(Properties props) {


    }

    @Override
    public void configure() throws Exception {

        restConfiguration()
                .port("{{nrk.kryss.klipp.port}}");

        rest("/kryss")
                .get("/klipp/{PROGID}/{FILID}")
                .produces("text/plain")
                .route()
                .process(exchange -> {
                    String progid = exchange.getIn().getHeader("PROGID", String.class);
                    String filerefernce = exchange.getIn().getHeader("FILID", String.class);
                    if (filerefernce.toLowerCase().contains(".ts")) {
                        String trings = "https://nrkedge-od.nrk.no/od/nrkhd-osl-rr.netwerk.no/world/49570/0/hls/" + progid + "/" + filerefernce;
                        System.out.println("Fetching " + trings);
                        InputStream stream = new URL(trings).openStream();
                        exchange.getIn().setBody(stream);
                    } else if (filerefernce.toLowerCase().contains(".m3u8")) {
                        System.out.println("Fetching " + filerefernce);
                        File is = new File("files/" + progid + "/" + filerefernce);
                        System.out.println("Found url " + is.getAbsolutePath());
                        Path asPath = Paths.get(is.toURI());
                        System.out.println("As Path " + asPath);
                        exchange.getIn().setBody(Files.newInputStream(asPath));
                    }
                })
        ;

        rest("/mitt").get("/program")
                .produces("application/json")
                .route()
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Create some json");
                    }
                });

        rest("/epg").get("{TIME}").produces("application/json")
                .route()
                .process(exchange -> {

                    String timeString = exchange.getIn().getHeader("TIME", String.class);
                    Instant time;
                    try {
                        System.out.println("Setting start time to " + timeString);
                        time = Instant.parse(timeString);
                    } catch (Exception exception) {
                        System.out.println("Could not parse time; " + exception.getMessage());
                        time = Instant.now();
                    }



                    List<Program> calculatedPrograms = clock.renderPrograms();

                    ObjectMapper mapper = new ObjectMapper();

                    OutputStream os = new ByteArrayOutputStream();
                    mapper.writeValue(os, calculatedPrograms);

                    exchange.getIn().setBody(os);
                })
                .convertBodyTo(String.class)
                //.unmarshal().json()
                //.marshal().json(true)
        ;

        rest("/store").post("/save").consumes("application/json")
                .route()
                //.unmarshal().json(JsonLibrary.Jackson, ProgramInTime.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {
                        String incoming = exchange.getIn().getBody(String.class);
                        ObjectMapper mapper = new ObjectMapper();
                        Surround asd = mapper.readValue(incoming, Surround.class);
                        System.out.println("");

                    }
                });
        rest("/store").get("/fetch").produces("application/json")
                .route()
                .process(exchange -> {
                    ObjectMapper mapper = new ObjectMapper();

                    OutputStream os = new ByteArrayOutputStream();
                    Surround surr = new Surround();
                    surr.progroms = KryssklippRoute.clock.getPrograms().toArray(new ProgramInTime[]{});
                    mapper.writeValue(os, surr);

                    exchange.getIn().setBody(os);
                })
                .convertBodyTo(String.class);

    }
}

class Surround {
    public ProgramInTime[] progroms;
}
