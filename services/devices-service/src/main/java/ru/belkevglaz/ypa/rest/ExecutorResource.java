package ru.belkevglaz.ypa.rest;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.belkevglaz.ypa.objects.Command;
import ru.belkevglaz.ypa.objects.Sensor;

/**
 * @since 1.0
 */
@Slf4j
@Path("/api/v1/exec")
@Tag(name = "External devices executor resource", description = "External devices management API")
@APIResponses(
		value = {
				@APIResponse(
						responseCode = "503",
						description = "Internal server error"
				)
		}
)
public class ExecutorResource {

	@POST
	@Operation(
			summary = "Exec command or set state on external device"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Successfully executed",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "404",
							description = "Device not registered",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response execCommand(@Parameter(description = "Command entity to execute on external device") Command command) {
		log.info("⏭ Executing command: [{}] for device id [{}]", command.getCommand(), command.getDeviceId());
		return Response.ok().entity(command).build();
	}

}
