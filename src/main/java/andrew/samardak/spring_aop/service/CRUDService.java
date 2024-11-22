package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.mappers.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CRUDService<Entity, ID, Request, Response> {

    default Entity create(Request dto) {
        Entity entity = getMapper().toEntity(dto);

        return getRepository().save(entity);
    }

    default Entity read(ID id) {
        return getRepository().findById(id).orElseThrow();
    }

    default Entity update(Request dto, ID id) {
        Entity entity = getRepository().findById(id).orElseThrow();

        getMapper().updateEntity(entity, dto);

        return getRepository().saveAndFlush(entity);
    }

    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    JpaRepository<Entity, ID> getRepository();

    BaseMapper<Entity, Request, Response> getMapper();
}
