package ru.belkevglaz.ypa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ru.belkevglaz.ypa.objects.Subscription;

@ApplicationScoped
public class SubscriptionRepository implements PanacheRepository<Subscription> {
}
