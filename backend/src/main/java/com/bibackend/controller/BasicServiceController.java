package com.bibackend.controller;

import com.bibackend.dao.*;
import com.bibackend.entity.AUTHOR;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/basicService")
@Api(tags = "基本业务查询")
public class BasicServiceController {
    @Resource
    PAPERRepository paperRepository;
    @Resource
    AFFILIATIONRepository affiliationRepository;
    @Resource
    AUTHORRepository authorRepository;
    @Resource
    BELONG_TORepository belong_toRepository;
    @Resource
    HAS_INTERESTRepository has_interestRepository;
    @Resource
    CO_AUTHORRepository co_authorRepository;
    @Resource
    INTERESTRepository interestRepository;
    @Resource
    PUBLISHRepository publishRepository;
    @Resource
    REFERRepository referRepository;
    @Resource
    VENUERepository venueRepository;
    @Resource
    WRITERepository writeRepository;

    @Operation(summary = "查询关键作者 ", description = "type: 0表示使用hindex，1表示pindex 2表示upindex 3表示publishcount")
    @GetMapping("keyAuthor")
    public ResponseEntity<Object> getKeyAuthor(@Param("interest") String interest, @Param("type") Integer type) {

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
            Map<String, String> map = new HashMap<>();
            map.put("message", "参数错误");
            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(authorList, HttpStatus.OK);

    }

    @Operation(summary = "查询关键单位")
    @GetMapping("keyAffiliation")
    public ResponseEntity<Object> getKeyAffiliationByInterest(@Param("interest") String interest) {
        return new ResponseEntity<>(affiliationRepository.getKeyAffiliation(interest), HttpStatus.OK);
    }

    @Operation(summary = "查询关键期刊")
    @GetMapping("keyVenue")
    public ResponseEntity<Object> getKeyVenueByInterest(@Param("interest") String interest) {
        return new ResponseEntity<>(venueRepository.getKeyVenue(interest, "", true), HttpStatus.OK);
    }
    @Operation(summary = "查询某一年的关键期刊")
    @GetMapping("keyVenueByYear")
    public ResponseEntity<Object> getKeyVenueByInterestByYear(@Param("interest") String interest, @Param("year") String year){
        return new ResponseEntity<>(venueRepository.getKeyVenue(interest, year, false), HttpStatus.OK);
    }

}
