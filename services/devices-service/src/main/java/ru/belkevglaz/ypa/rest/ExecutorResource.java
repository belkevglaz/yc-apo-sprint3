package ru.belkevglaz.ypa.rest;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import ru.belkevglaz.ypa.objects.Command;

/**
 * @since 1.0
 */
@Slf4j
@Path("/api/v1/exec")
public class ExecutorResource {

	@POST
	public Response execCommand(Command command) {
		log.info("⏭ Executing command: [{}] for device id [{}]", command.getCommand(), command.getDeviceId());



		return Response.ok().entity(command).build();
	}

}
