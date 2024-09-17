package ru.belkevglaz.ypa.client;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.Data;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api/v1/exec")
@RegisterRestClient(configKey = "automation.device-exec-client")
public interface DeviceCommandExecClient {

	@POST
	Response exec(Command command);

	@Data
	class Command {

		private String deviceId;

		private String command;
	}

}
