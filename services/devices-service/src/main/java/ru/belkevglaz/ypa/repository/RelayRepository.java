package ru.belkevglaz.ypa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ru.belkevglaz.ypa.objects.Relay;

@ApplicationScoped
public class RelayRepository implements PanacheRepository<Relay> {
}
