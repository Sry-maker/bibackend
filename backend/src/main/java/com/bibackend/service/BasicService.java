package com.bibackend.service;

import com.bibackend.dao.AFFILIATIONRepository;
import com.bibackend.dao.AUTHORRepository;
import com.bibackend.dao.VENUERepository;
import com.bibackend.entity.AUTHOR;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@CacheConfig(cacheNames = "basic-service")
public class BasicService {
    @Resource
    AUTHORRepository authorRepository;
    @Resource
    AFFILIATIONRepository affiliationRepository;
    @Resource
    VENUERepository venueRepository;

    @Cacheable(key = "'key-author-interest-' + #p0 + '-type-' + #p1")
    public List<AUTHOR> getKeyAuthor(String interest, Integer type) {
        List<AUTHOR> authorList = authorRepository.findAuthorByInterest(interest);
        if (type.equals(0)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getHindex());
                Float sum2 = Float.parseFloat(o2.getHindex());
                return sum2.compareTo(sum1);
            });
        } else if (type.equals(1)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getPindex());
                Float sum2 = Float.parseFloat(o2.getPindex());
                return sum2.compareTo(sum1);
            });
        } else if (type.equals(2)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getUPindex());
                Float sum2 = Float.parseFloat(o2.getUPindex());
                return sum2.compareTo(sum1);
            });
        } else if (type.equals(3)) {
            authorList.sort((o1, o2) -> {
                Float sum1 = Float.parseFloat(o1.getPublishCount());
                Float sum2 = Float.parseFloat(o2.getPublishCount());
                return sum2.compareTo(sum1);
            });
        } else {

        }
        return authorList;
    }

    @Cacheable(key = "'key-affiliation-interest-' + #p0")
    public List<Map<String, Object>> getKeyAffiliationByInterest(String interest) {
        return affiliationRepository.getKeyAffiliation(interest);
    }

    @Cacheable(key = "'key-venue-interest-' + #p0")
    public List<Map<String, Object>> getKeyVenueByInterest(String interest) {
        return venueRepository.getKeyVenue(interest, "", true);
    }

    @Cacheable(key = "'key-venue-interest-' + #p0 + '-year-' + #p1")
    public List<Map<String, Object>> getKeyVenueByInterestByYear(String interest, String year) {
        return venueRepository.getKeyVenue(interest, year, false);
    }


}
