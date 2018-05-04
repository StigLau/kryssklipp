import com.fasterxml.jackson.databind.ObjectMapper;
import no.nrk.kryssklipp.domain.Clock;
import no.nrk.kryssklipp.domain.ProgramDefinition;
import no.nrk.kryssklipp.jsonpresentation.Program;
import org.junit.Test;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class TestPeppaGris {

    @Test
    public void testPeppaGris() throws IOException {

        ProgramDefinition ponniskolen = new ProgramDefinition("Ponniskolen", "MSUI16004312", "PT21M15S", "PT22M15S");
        ProgramDefinition peppaGris = new ProgramDefinition("Peppa Gris - bursdag i park", "MSUI25005616", "PT0S", "PT2M" );
        ProgramDefinition pompelOgPilt = new ProgramDefinition("Pompel Og Pilt", "FBUA01000569", "PT0S", "PT5M");

        Clock clock = new Clock(Clock.parseTime("2017-10-23T10:00:00+02:00"));
        clock.addProgram(ponniskolen, "PT0S", "PT1M");
        clock.addProgram(peppaGris, "PT1M", "PT5M");
        clock.addProgram(pompelOgPilt, "PT6M", "PT4M");


        List<Program> calculatedPrograms = clock.renderPrograms();

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("car.json"), calculatedPrograms);


        //assertEquals("{}", );
    }
}

