package ru.belkevglaz.ypa.rest;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import ru.belkevglaz.ypa.objects.Subscription;
import ru.belkevglaz.ypa.services.SubscriptionService;

/**
 * Административный ресурс, для создания подписки.
 *
 * @since 1.0
 */
@Path("/api/v1/subscription")
//@Authenticated
@Tag(name = "Automations and subscription  management resource", description = "Automation management API")
@APIResponses(
		value = {
				@APIResponse(
						responseCode = "503",
						description = "Internal server error"
				)
		}
)
public class SubscriptionResource {

	@Inject
	SubscriptionService subscriptionService;

	@POST
//	@RolesAllowed("Admin")
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(
			summary = "Exec command or set state on external device"
	)
	@APIResponses(
			value = {
					@APIResponse(
							responseCode = "201",
							description = "Successfully created subscription",
							content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Subscription.class))
					),
					@APIResponse(
							responseCode = "403",
							description = "User not authorized to do this."
					),
			}
	)
	public Response createSubscription(@Parameter(description = "Subscription entity to create appropriated consumer") Subscription subscription) {
		return Response.created(null).entity(subscriptionService.createSubscription(subscription)).build();
	}
}
