package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.entity.DataSourceErrorLog;
import andrew.samardak.spring_aop.repository.DataSourceErrorLogRepository;
import andrew.samardak.spring_aop.service.LogDataSourceErrorService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LogDataSourceErrorServiceImpl implements LogDataSourceErrorService {

    DataSourceErrorLogRepository repository;

    @Override
    public DataSourceErrorLog create(DataSourceErrorLog entity) {
        return repository.save(entity);
    }
}
