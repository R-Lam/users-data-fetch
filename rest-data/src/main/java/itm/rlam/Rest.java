package itm.rlam;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.SseElementType;
import org.reactivestreams.Publisher;

import io.smallrye.reactive.messaging.annotations.Channel;

@SuppressWarnings("deprecation")
@Path("/stream")
public class Rest {

    @Inject
    @Channel("data-stream") Publisher<String> data;

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @SseElementType("application/json")
    public Publisher<String> stream() {
        return data;
    }
    
}