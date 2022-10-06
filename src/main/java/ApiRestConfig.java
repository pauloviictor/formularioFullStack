import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("rest")
public class ApiRestConfig extends ResourceConfig {
    public ApiRestConfig(){
        packages("Controller");
    }
}
