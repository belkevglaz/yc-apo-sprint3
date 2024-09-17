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
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import ru.belkevglaz.ypa.objects.Telemetry;
import ru.belkevglaz.ypa.services.KafkaService;

/**
 * Rest контроллер для данных с модулей и датчиков.
 */
@Resource
@Path("/api/v1/telemetry")
public class TelemetryResource {

	@Inject
	KafkaService service;

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
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Telemetry.class))

					),
					@APIResponse(
							responseCode = "404",
							description = "Device with given id not found.",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					),
					@APIResponse(
							responseCode = "503",
							description = "Couldn't save telemetry data. Internal server error",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					)
			}
	)
	public Response receiveRaw( String rawData) {
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
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Telemetry.class))

					),
					@APIResponse(
							responseCode = "404",
							description = "Device with given id not found.",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					),
					@APIResponse(
							responseCode = "503",
							description = "Couldn't save telemetry data. Internal server error",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					)
			}
	)
	public Response receive(Telemetry data) {
		try {
			return Response.ok(service.publishTelemetry(data)).build();
		} catch (Exception e) {
			return Response.serverError().entity(e.getMessage()).build();
		}
	}

}