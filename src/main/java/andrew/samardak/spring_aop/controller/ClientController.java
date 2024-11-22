package andrew.samardak.spring_aop.controller;

import andrew.samardak.spring_aop.aspect.LogDataSourceError;
import andrew.samardak.spring_aop.dto.request.ClientRequestDto;
import andrew.samardak.spring_aop.dto.response.ClientResponseDto;
import andrew.samardak.spring_aop.facade.ClientFacade;
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

    ClientFacade clientFacade;

    @PostMapping
    public ClientResponseDto create(
            @RequestBody ClientRequestDto dto
    ) {
        return clientFacade.create(dto);
    }

    @GetMapping("/{id}")
    public ClientResponseDto read(
            @PathVariable Long id
    ) {
        return clientFacade.read(id);
    }

    @PatchMapping("/{id}")
    public ClientResponseDto update(
            @PathVariable Long id,
            @RequestBody ClientRequestDto dto
    ) {
        return clientFacade.update(dto, id);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long id
    ) {
        clientFacade.delete(id);
    }
}
