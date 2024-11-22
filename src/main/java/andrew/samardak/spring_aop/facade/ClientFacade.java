package andrew.samardak.spring_aop.facade;

import andrew.samardak.spring_aop.dto.request.ClientRequestDto;
import andrew.samardak.spring_aop.dto.response.ClientResponseDto;
import andrew.samardak.spring_aop.entity.Client;
import andrew.samardak.spring_aop.mappers.ClientMapper;
import andrew.samardak.spring_aop.service.ClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientFacade {

    ClientService clientService;
    ClientMapper clientMapper;

    public ClientResponseDto create(ClientRequestDto dto) {
        Client account = clientService.create(dto);

        return clientMapper.toDto(account);
    }

    public ClientResponseDto read(Long id) {
        Client account = clientService.read(id);

        return clientMapper.toDto(account);
    }

    public ClientResponseDto update(ClientRequestDto dto, Long id) {
        Client update = clientService.update(dto, id);

        return clientMapper.toDto(update);
    }

    public void delete(Long id) {
        clientService.delete(id);
    }
}
