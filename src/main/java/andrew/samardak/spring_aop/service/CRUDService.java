package andrew.samardak.spring_aop.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CRUDService<Entity, ID> {

    default Entity create(Entity entity) {
        return getRepository().save(entity);
    }

    default Entity read(ID id) {
        return getRepository().findById(id).orElseThrow();
    }

    default Entity update(Entity entity) {
        return getRepository().saveAndFlush(entity);
    }

    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    JpaRepository<Entity, ID> getRepository();
}
