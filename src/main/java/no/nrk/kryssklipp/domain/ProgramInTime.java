package no.nrk.kryssklipp.domain;

import java.time.Duration;

public class ProgramInTime {
    public String programdurationstring;
    public ProgramDefinition programDefinition;
    transient public Duration programStartAfterOrigo;
    transient public Duration programDuration;

    public String programstartstring;

    public ProgramInTime() {
    }

    public ProgramInTime(ProgramDefinition programDefinition, Duration programStart, Duration programDuration) {
        this.programDefinition = programDefinition;
        this.programstartstring = programStart.toString();
        this.programStartAfterOrigo = programStart;
        this.programdurationstring = programDuration.toString();
        this.programDuration = programDuration;
    }
}
