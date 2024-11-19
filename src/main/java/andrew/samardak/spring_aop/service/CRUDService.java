package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.mappers.BaseMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CRUDService<Entity, ID, Request, Response> {

    default Response create(Request dto) {
        Entity entity = getMapper().toEntity(dto);
        getRepository().save(entity);

        return getMapper().toDto(entity);
    }

    default Response read(ID id) {
        Entity entity = getRepository().findById(id).orElseThrow();

        return getMapper().toDto(entity);
    }

    default Response update(Request dto, ID id) {
        Entity entity = getRepository().findById(id).orElseThrow();

        getMapper().updateEntity(entity, dto);

        getRepository().saveAndFlush(entity);

        return getMapper().toDto(entity);
    }

    default void delete(ID id) {
        getRepository().deleteById(id);
    }

    JpaRepository<Entity, ID> getRepository();

    BaseMapper<Entity, Request, Response> getMapper();
}
