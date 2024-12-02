package dev.dluks.rental.service.agency;

import dev.dluks.rental.exception.AgencyNotFoundException;
import dev.dluks.rental.model.agency.Agency;
import dev.dluks.rental.repository.AgencyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyRepository agencyRepository;

    public Agency getAgencyById(UUID id) {
        return agencyRepository.findById(id)
                .orElseThrow(() -> new AgencyNotFoundException(id.toString() + " not found."));
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

    public Agency findByDocument(String document) {
        return agencyRepository.findByDocument(document)
                .orElseThrow(() -> new AgencyNotFoundException("Agency by document " + document + " not found"));
    }

    public Agency findByName(String name) {
        return agencyRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new AgencyNotFoundException("Agency by name " + name + " not found"));
    }

    private void updateField(Consumer<String> setter, String value) {
        if (value != null && !value.isEmpty()) {
            setter.accept(value);
        }
    }

    public Agency updateAgency(UUID id, UpdateAgencyRequest updateAgencyRequest) {
       Agency agency = getAgencyById(id);

       updateField(agency::setName, updateAgencyRequest.getName());
       updateField(agency::setDocument, updateAgencyRequest.getDocument());
       updateField(agency::setPhone, updateAgencyRequest.getPhone());
       updateField(agency::setEmail, updateAgencyRequest.getEmail());

       agencyRepository.save(agency);
       return agency;
    }

}
