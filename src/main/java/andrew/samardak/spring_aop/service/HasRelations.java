package andrew.samardak.spring_aop.service;

public interface HasRelations<Entity, ID> extends CRUDService<Entity, ID> {

    Entity create(Entity entity, ID id);
}
