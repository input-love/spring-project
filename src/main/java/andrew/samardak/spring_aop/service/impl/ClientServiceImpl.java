package andrew.samardak.spring_aop.service.impl;

import andrew.samardak.spring_aop.entity.Client;
import andrew.samardak.spring_aop.repository.ClientRepository;
import andrew.samardak.spring_aop.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public JpaRepository<Client, Long> getRepository() {
        return clientRepository;
    }
}
