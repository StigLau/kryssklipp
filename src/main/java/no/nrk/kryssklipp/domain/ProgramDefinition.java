package no.nrk.kryssklipp.domain;

import java.time.Duration;

public class ProgramDefinition {

    public String title;
    public String progId;

    public Duration snippetStartsAt;
    public Duration snippetDuration;

    public ProgramDefinition(String title, String progId, String durrationBeforesnippetStartS, String snippetDuration) {
        this.title = title;
        this.progId = progId;
        this.snippetStartsAt = Duration.parse(durrationBeforesnippetStartS);
        this.snippetDuration = Duration.parse(snippetDuration);

    }
}
