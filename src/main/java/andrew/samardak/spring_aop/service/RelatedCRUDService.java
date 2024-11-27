package andrew.samardak.spring_aop.service;

public interface RelatedCRUDService<Entity, ID> extends CRUDService<Entity, ID> {

    Entity createWithRelations(Entity entity, ID id);
}
