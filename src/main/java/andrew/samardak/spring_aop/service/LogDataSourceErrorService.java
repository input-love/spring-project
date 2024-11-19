package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.entity.DataSourceErrorLog;

public interface LogDataSourceErrorService {
    DataSourceErrorLog create(DataSourceErrorLog entity);
}
