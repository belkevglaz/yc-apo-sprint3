package ru.belkevglaz.ypa.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ru.belkevglaz.ypa.objects.Subscription;
import ru.belkevglaz.ypa.services.SubscriptionService;

/**
 * Административный ресурс, для создания подписки.
 *
 * @since 1.0
 */
@Path("/api/v1/subscription")
public class SubscriptionResource {

	@Inject
	SubscriptionService subscriptionService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createSubscription(Subscription subscription) {
		return Response.created(null).entity(subscriptionService.createSubscription(subscription)).build();
	}
}
