package ru.belkevglaz.ypa.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.belkevglaz.ypa.exceptions.AlreadyExistsException;
import ru.belkevglaz.ypa.objects.Device;
import ru.belkevglaz.ypa.objects.Relay;
import ru.belkevglaz.ypa.objects.Sensor;
import ru.belkevglaz.ypa.services.RelayService;

@Path("/api/v1/relays")
@APIResponses(
		value = {
				@APIResponse(
						responseCode = "503",
						description = "Internal server error"
				)
		}
)
@Tag(name = "Relays Resource", description = "Relays management Rest API")
public class RelayResource {

	@Inject
	RelayService relayService;

	@GET
	@Path("/{id}")
	@Operation(
			summary = "Find Relays by id or serial number"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Success got relay.",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "404",
							description = "Relay not found",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response findByIdOrSerialNumber(@Parameter(description = "Unique sensor id or serial number") @PathParam("id") String id) {
		return Response.ok().entity(relayService.findByIdOrSerialNumber(id)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Register new relay"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "201",
							description = "relay registered",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Device.class))

					),
					@APIResponse(
							responseCode = "409",
							description = "relay with given id/SN already exists.",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					),
			}
	)
	public Response create(Relay relay) throws AlreadyExistsException {
		return Response.created(null).entity(relayService.register(relay)).build();
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Update sensor."
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Sensor successfully updated",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "404",
							description = "Given sensor not registered",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response update(Relay relay) {
//		relayService.update(sensor);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@Operation(
			summary = "Find and delete relay by id or serial number"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "relay successfully deleted",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "204",
							description = "Given relay not registered",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response unregister(@Parameter(description = "") @PathParam("id") String id) {
		boolean result = relayService.unregister(id);
		if (result) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}


}
