package com.blaze.server.services.mongo;

import java.util.List;

import com.blaze.server.models.Identification;
import com.blaze.server.repositories.mongo.IdentificationRepository;
import com.blaze.server.services.interfaces.IIdentification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IdentificationService implements IIdentification {
    private final int initialIdDocument = 1;
    private final int incrementIdDocument = 1;

    @Autowired
    private IdentificationRepository identificationRepository;

    public List<Identification> getAll() {
        return identificationRepository.findAll();
    }

    public Identification get(String id) {
        return identificationRepository.findById(id).orElse(null);
    }

    public Identification save(Identification item) {
        return identificationRepository.save(item);
    }

    public Identification update(Identification item) {
        Identification identification = identificationRepository.findById(item.getId()).orElse(null);
        identification.setLastIdDocument(item.getLastIdDocument());
        identification.setDocument(item.getDocument());
        identificationRepository.save(identification);

        return identification;
    }

    public void delete(String id) {
        identificationRepository.deleteById(id);
    }

    public int getIdDocument(String document) {
        Identification identification = this.getByDocument(document);
        int newIdDocument;

        if (identification == null) {
            newIdDocument = this.getAll().size() + this.incrementIdDocument;

            identification = new Identification();
            identification.setId(String.valueOf(newIdDocument));
            identification.setLastIdDocument(this.initialIdDocument);
            identification.setDocument(document);

            this.save(identification);
        }
        else {
            newIdDocument = identification.getLastIdDocument() + this.incrementIdDocument;
            identification.setLastIdDocument(newIdDocument);

            this.update(identification);
        } 

        return identification.getLastIdDocument();
    }

    private Identification getByDocument(String document) {
        try {
            List<Identification> identifications = identificationRepository.findAll();
    
            return identifications.stream().filter(id -> document.equals(id.getDocument())).findAny().orElse(null);
        }
        catch(Exception e) {
            throw e;
        }
    }
}
