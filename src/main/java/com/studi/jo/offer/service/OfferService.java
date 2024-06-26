package com.studi.jo.offer.service;

import com.studi.jo.offer.domain.Offer;
import com.studi.jo.offer.domain.OfferDTO;
import com.studi.jo.offer.domain.OfferName;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class OfferService {
    private final OfferRepository offerRepository;

    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    private Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer createOffer(OfferDTO offerDTO) {
        return saveOffer(offerDTO.toOffer());
    }

    public Offer updateOffer(Long id, OfferDTO offerDTO) {
        Offer existingOffer = this.getOfferById(id);
        existingOffer.setName(offerDTO.getName());
        existingOffer.setDescription(offerDTO.getDescription());
        existingOffer.setPrice(offerDTO.getPrice());
        return offerRepository.save(existingOffer);
    }

    public Offer updateOfferIncrementSales(OfferName name, int increment) {
        Offer existingOffer = this.getOfferByName(name);
        int currentSalesNumber = existingOffer.getSalesNumber();
        existingOffer.setSalesNumber(currentSalesNumber + increment);
        return offerRepository.save(existingOffer);
    }

    public Offer getOfferByName(OfferName name) {
        Optional<Offer> offer = offerRepository.findByName(name);
        if (!offer.isPresent()) {
            throw new EntityNotFoundException("ERROR: Offer with name " + name.getValue() + " not found.");
        }
        return offer.get();
    }
    
    public Offer getOfferById(Long id) {
        Optional<Offer> offer = offerRepository.findById(id);
        if (!offer.isPresent()) {
            throw new EntityNotFoundException("ERROR: Offer with id " + id + " not found.");
        }
        return offer.get();
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAllByOrderByIdAsc();
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }

}
