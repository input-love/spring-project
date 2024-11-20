package andrew.samardak.spring_aop.service;

import andrew.samardak.spring_aop.dto.request.ClientRequestDto;
import andrew.samardak.spring_aop.dto.response.ClientResponseDto;
import andrew.samardak.spring_aop.entity.Client;

public interface ClientService extends CRUDService<Client, Long, ClientRequestDto, ClientResponseDto> {
}
