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
import ru.belkevglaz.ypa.exceptions.AlreadyExistsException;
import ru.belkevglaz.ypa.exceptions.NotExistsException;
import ru.belkevglaz.ypa.objects.Device;
import ru.belkevglaz.ypa.objects.Sensor;
import ru.belkevglaz.ypa.services.SensorService;

import java.util.List;

@Path("/api/v1/sensors")
@APIResponses(
		value = {
				@APIResponse(
						responseCode = "503",
						description = "Internal server error"
				)
		}
)
@Tag(name = "Sensors Resource", description = "Sensors management Rest API")
public class SensorResource {

	@Inject
	SensorService sensorService;

	@GET
	@Path("/{id}")
	@Operation(
			summary = "Find sensor by id or serial number"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Success got sensor.",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "404",
							description = "Sensor not found",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response findByIdOrSerialNumber(@Parameter(description = "Unique sensor id or serial number") @PathParam("id") String id) {
		return Response.ok().entity(sensorService.findByIdOrSerialNumber(id)).build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Register new sensor"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "201",
							description = "Sensor registered",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Device.class))

					),
					@APIResponse(
							responseCode = "409",
							description = "Sensor with given id/SN already exists.",
							content = @Content(mediaType = MediaType.MEDIA_TYPE_WILDCARD)
					),
			}
	)
	public Response create(Sensor sensor) throws AlreadyExistsException {
		return Response.created(null).entity(sensorService.register(sensor)).build();
	}

	@PUT
	@Path("/{moduleId}/{id}")
	@Operation(
			summary = "Link sensor with module"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Sensor successfully linked to module",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "404",
							description = "Given sensor or module not found",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response linkToModule(@Parameter(description = "") @PathParam("moduleId") String moduleId, @PathParam("id") String id) throws NotExistsException {
		return Response.ok().entity(sensorService.linkSensorToModule(id, moduleId)).build();
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
	public Response update(Sensor sensor) {
		sensorService.update(sensor);
		return Response.ok().build();
	}

	@DELETE
	@Path("/{id}")
	@Operation(
			summary = "Find and delete sensor by id or serial number"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "Sensor successfully deleted",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Sensor.class))
					),
					@APIResponse(
							responseCode = "204",
							description = "Given sensor not registered",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response unregister(@Parameter(description = "") @PathParam("id") String id) {
		boolean result = sensorService.unregister(id);
		if (result) {
			return Response.ok().build();
		} else {
			return Response.status(Response.Status.NO_CONTENT).build();
		}
	}

	@GET
	@Path("/linked/{moduleId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Fetch sensors that linked to module with given id/sn"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "200",
							description = "List of linked sensors",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = List.class))
					),
					@APIResponse(
							responseCode = "404",
							description = "Module not found with given id/sn",
							content = @Content(mediaType = MediaType.WILDCARD, schema = @Schema(implementation = NotFoundException.class))
					)
			}
	)
	public Response getLinkedSensors(@Parameter(description = "Unique module id or serial number") @PathParam("moduleId") String moduleId) {
		return Response.ok().entity(sensorService.getLinkedByModuleId(moduleId)).build();
	}


}
