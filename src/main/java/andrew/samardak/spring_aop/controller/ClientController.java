package andrew.samardak.spring_aop.controller;

import andrew.samardak.spring_aop.aspect.LogDataSourceError;
import andrew.samardak.spring_aop.dto.request.ClientRequestDto;
import andrew.samardak.spring_aop.dto.response.ClientResponseDto;
import andrew.samardak.spring_aop.service.ClientService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@LogDataSourceError
@RequiredArgsConstructor
@RequestMapping("v1/clients")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClientController {

    ClientService clientService;

    @PostMapping
    public ClientResponseDto create(
            @RequestBody ClientRequestDto dto
    ) {
        return clientService.create(dto);
    }

    @GetMapping("/{id}")
    public ClientResponseDto read(
            @PathVariable Long id
    ) {
        return clientService.read(id);
    }

    @PatchMapping("/{id}")
    public ClientResponseDto update(
            @PathVariable Long id,
            @RequestBody ClientRequestDto dto
    ) {
        return clientService.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ) {
        clientService.delete(id);
    }
}
