package no.nrk.kryssklipp.domain;

import no.nrk.kryssklipp.jsonpresentation.Program;

import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Clock {
    public Instant clockStart;

    List<ProgramInTime> programs = new ArrayList<>();

    public Clock(Instant clockStart) {
        this.clockStart = clockStart;
    }

    public void addProgram(ProgramDefinition programDefinition, String programStart, String programDuration) {
        programs.add(new ProgramInTime(programDefinition, Duration.parse(programStart), Duration.parse(programDuration)));
    }

    public List<ProgramInTime> getPrograms() {
        return programs;
    }


    public List<Program> renderPrograms() {
        List<Program> programList = new ArrayList<>();
        for (ProgramInTime program : programs) {


            Instant from = clockStart.plus(program.programStartAfterOrigo);
            Instant until = clockStart.plus(program.programStartAfterOrigo).plus(program.programDuration);
            programList.add(new Program(
                    program.programDefinition.title,
                    program.programDefinition.progId,
                    program.programDefinition.snippetStartsAt,
                    program.programDefinition.snippetDuration,
                    from,
                    until)
            );

        }
        return programList;
    }
    public static Instant parseTime(String timestampString) {
        try {
            try {
                return Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(timestampString));
            } catch (DateTimeParseException e) {
                return Instant.from(DateTimeFormatter.ISO_ZONED_DATE_TIME.parse(timestampString));
            }
        }catch (Exception e) {
            throw new RuntimeException("Could not parse time of " + timestampString, e);
        }
    }
}
