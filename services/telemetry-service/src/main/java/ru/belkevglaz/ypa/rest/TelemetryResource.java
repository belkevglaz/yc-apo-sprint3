package ru.belkevglaz.ypa.rest;

import jakarta.annotation.Resource;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.belkevglaz.ypa.services.TelemetryService;

/**
 * Rest контроллер для данных с модулей и датчиков.
 */
@Resource
@Path("/api/v1/telemetry")
@Tag(name = "Endpoint to handles telemetry from external devices", description = "Telemetry handles API")
@APIResponses(
		value = {
				@APIResponse(
						responseCode = "503",
						description = "Internal server error"
				)
		}
)
public class TelemetryResource {

	@Inject
	TelemetryService service;

	@POST
	@Path("/raw")
	@Consumes(MediaType.WILDCARD)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Receives telemetry data from sensors in json format"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Telemetry data saved.",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ru.belkevglaz.ypa.objects.Telemetry.class))

					),
					@APIResponse(
							responseCode = "404",
							description = "Device with given id not found.",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					)
			}
	)
	public Response receiveRaw(@Parameter(description = "Telemetry data in raw format.") String rawData) {
		try {
			return Response.ok(service.publishRawTelemetry(rawData)).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.WILDCARD)
	@Operation(
			summary = "Receives telemetry data from sensors in json format"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Telemetry data saved.",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ru.belkevglaz.ypa.objects.Telemetry.class))

					),
					@APIResponse(
							responseCode = "404",
							description = "Device with given id not found.",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					)
			}
	)
	public Response receive(@Parameter(description = "Telemetry entity data") ru.belkevglaz.ypa.objects.Telemetry data) {
		try {
			return Response.ok(service.publishTelemetry(data)).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}