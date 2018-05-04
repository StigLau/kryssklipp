package no.nrk.kryssklipp.jsonpresentation;

import java.time.Duration;
import java.time.Instant;

public class Program {
    public String title;
    public String progid;
    public String snippetstart;
    public String snippetduration;
    public String from;
    public String until;

    public Program(String title, String progid, Duration snippetstart, Duration snippetduration, Instant from, Instant until) {
        this.title = title;
        this.progid = progid;
        this.snippetstart = snippetstart.toString();
        this.snippetduration = snippetduration.toString();
        this.from = from.toString();
        this.until = until.toString();
    }
}
