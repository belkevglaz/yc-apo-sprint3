package ru.belkevglaz.ypa.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
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
import ru.belkevglaz.ypa.objects.Module;
import ru.belkevglaz.ypa.objects.Sensor;
import ru.belkevglaz.ypa.services.ModuleService;

import java.util.List;

/**
 */
@Path("/api/v1/modules")
@Tag(name = "Modules Resource", description = "Modules management Rest API")
@APIResponses(
		value = {
				@APIResponse(
						responseCode = "503",
						description = "Internal server error"
				)
		}
)
public class ModuleResource {

	@Inject
	ModuleService moduleService;

	@GET
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Find module by id or serial number"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Success got module.",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "404",
							description = "Module not found",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response getModule(@Parameter(description = "Unique module id or serial number") @PathParam("id") String id) {
		Device d = moduleService.findByIdOrSerialNumber(id);
		if (d == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		} else {
			return Response.ok(d).build();
		}
	}


	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Register new module in the system."
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "201",
							description = "Device registered",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Device.class))

					),
					@APIResponse(
							responseCode = "409",
							description = "Device with given serial number already exists."
					),
			}
	)
	public Response register(Module module) throws AlreadyExistsException {
		return Response.created(null).entity(moduleService.register(module)).build();
	}

}
