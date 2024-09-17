package ru.belkevglaz.ypa.services;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.transaction.Transactional;
import ru.belkevglaz.ypa.exceptions.AlreadyExistsException;
import ru.belkevglaz.ypa.objects.Device;

/**
 *
 */
public abstract class DeviceService<T extends  Device> {

	abstract PanacheRepository<T> getRepository();

	public T findByIdOrSerialNumber(String id) {
		try {
			Long longId = Long.parseLong(id);
			return getRepository().findById(longId);
		} catch (NumberFormatException e) {
			return getRepository().find("serialNumber", id).firstResult();
		}
	}

	@Transactional
	public T register(T device) throws AlreadyExistsException {
		T d = findByIdOrSerialNumber(device.getSerialNumber());

		if (d != null) {
			throw new AlreadyExistsException("Device with serialNumber [" + device.getSerialNumber() + "] already registered");
		} else {
			getRepository().persist(device);
			return device;
		}
	}

	@Transactional
	public boolean unregister(String id) {
		Device d = findByIdOrSerialNumber(id);
		if (d != null) {
			getRepository().delete(findByIdOrSerialNumber(id));
			return true;
		} else {
			return false;
		}
	}
}
