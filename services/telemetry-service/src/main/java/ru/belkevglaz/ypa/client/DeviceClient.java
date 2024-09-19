package ru.belkevglaz.ypa.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import ru.belkevglaz.ypa.objects.Sensor;

/**
 * Клиент для вызова сервиса по устройствам.
 *
 * @since 1.0
 */
@Path("/api/v1/sensors/")
public interface DeviceClient {

	@GET
	@Path("/{id}")
	Sensor findSensor(@PathParam("id") String id);
}
