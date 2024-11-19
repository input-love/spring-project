package andrew.samardak.spring_aop.mappers;

import org.mapstruct.MappingTarget;

public interface BaseMapper<Entity, Request, Response> {

    Entity toEntity(Request dto);

    Response toDto(Entity entity);

    void updateEntity(@MappingTarget Entity entity, Request dto);
}
