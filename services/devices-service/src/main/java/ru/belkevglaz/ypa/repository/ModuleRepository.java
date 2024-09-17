package ru.belkevglaz.ypa.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import ru.belkevglaz.ypa.objects.Module;

/**
 * @since 1.0
 */
@ApplicationScoped
public class ModuleRepository implements PanacheRepository<Module> {
}
