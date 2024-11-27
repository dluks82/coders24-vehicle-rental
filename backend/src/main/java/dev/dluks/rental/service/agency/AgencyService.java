package dev.dluks.rental.service.agency;

import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.repository.AgencyRepository;
import org.springframework.stereotype.Service;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;

    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public Agency create(CreateAgencyRequest dto) {
        Agency agency = Agency.builder()
                .name(dto.getName())
                .document(dto.getDocument())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
        return agencyRepository.save(agency);
    }

    public void findByDocument() {
    }

    public void findByName() {
    }

    public void updateAgency() {
    }
}
