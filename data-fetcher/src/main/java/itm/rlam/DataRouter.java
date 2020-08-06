package itm.rlam;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;

public class DataRouter extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:df?period=60s")
            .toD("https:{{random.users.data.url}}/?results={{random.users.data.size}}&inc=name,gender,email,picture&noinfo?httpMethod=GET")
            .unmarshal().json(JsonLibrary.Jackson, UsersData.class)
            .split().tokenize("\n", 1, true)
            .marshal().json()
            .convertBodyTo(String.class)
            .wireTap("direct:tap")
            .to("kafka:users?brokers={{kafka.bootstrap.address}}");

        from("direct:tap")
            .setBody(simple("Received message: ${body}"))
            .to("log:info");
    }
    
}