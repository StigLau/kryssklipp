package no.nrk.kryssklipp.domain;

import java.time.Duration;

public class ProgramDefinition {

    public String title;
    public String progId;

    public String snippetstartstring;
    public String snippetdurationstring;
    transient public Duration snippetStartsAt;
    transient public Duration snippetDuration;

    public ProgramDefinition(){

    }
    public ProgramDefinition(String title, String progId, String durrationBeforesnippetStart, String snippetDuration) {
        this.title = title;
        this.progId = progId;
        this.snippetstartstring = durrationBeforesnippetStart;
        this.snippetdurationstring = snippetDuration;
        this.snippetStartsAt = Duration.parse(durrationBeforesnippetStart);
        this.snippetDuration = Duration.parse(snippetDuration);

    }
}
