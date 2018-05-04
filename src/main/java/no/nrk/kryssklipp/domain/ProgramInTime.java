package no.nrk.kryssklipp.domain;

import java.time.Duration;

public class ProgramInTime {
    public ProgramDefinition programDefinition;
    public Duration programStartAfterOrigo;
    public Duration programDuration;

    public ProgramInTime(ProgramDefinition programDefinition, Duration programStart, Duration programDuration) {
        this.programDefinition = programDefinition;
        this.programStartAfterOrigo = programStart;
        this.programDuration = programDuration;
    }
}
