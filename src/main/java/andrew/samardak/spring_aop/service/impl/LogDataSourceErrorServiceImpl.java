package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.entity.DataSourceErrorLog;
import andrew.samardak.spring_aop.repository.DataSourceErrorLogRepository;
import andrew.samardak.spring_aop.service.LogDataSourceErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDataSourceErrorServiceImpl implements LogDataSourceErrorService {

    private final DataSourceErrorLogRepository dataSourceErrorLogRepository;

    @Override
    public DataSourceErrorLog create(DataSourceErrorLog entity) {
        return dataSourceErrorLogRepository.save(entity);
    }
}
