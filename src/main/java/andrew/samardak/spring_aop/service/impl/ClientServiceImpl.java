package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.dto.request.ClientRequestDto;
import andrew.samardak.spring_aop.dto.response.ClientResponseDto;
import andrew.samardak.spring_aop.entity.Client;
import andrew.samardak.spring_aop.mappers.BaseMapper;
import andrew.samardak.spring_aop.mappers.ClientMapper;
import andrew.samardak.spring_aop.repository.ClientRepository;
import andrew.samardak.spring_aop.service.ClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientServiceImpl implements ClientService {

    ClientRepository repository;
    ClientMapper mapper;

    @Override
    public JpaRepository<Client, Long> getRepository() {
        return repository;
    }

    @Override
    public BaseMapper<Client, ClientRequestDto, ClientResponseDto> getMapper() {
        return mapper;
    }
}
