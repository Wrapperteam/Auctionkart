package com.wrapperteam.bidding.service;

import com.wrapperteam.bidding.dto.ProductResponse;
import com.wrapperteam.bidding.model.BiddingModel;
import com.wrapperteam.bidding.repository.BiddingrRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class BiddingService {
    @Autowired
    BiddingrRepo repo;
    public List<BiddingModel> getAll() {
        return repo.findAll();

    }

    public BiddingModel saveBidder(BiddingModel bidder) {
        return repo.save(bidder);
    }

    public String updateBidder(BiddingModel bidder) {

        Optional<BiddingModel> biddupdate = repo.findById(bidder.getUserID());

        if(biddupdate.isPresent()) {
            biddupdate.get().setAmount(bidder.getAmount());

        }else {
            return "Not found";
        }

        repo.save(biddupdate.get());
        return "Successfully saved";
    }
    public String deleteBidder(Integer bidder) {
        // TODO Auto-generated method stub
        repo.deleteById(bidder);

        return "Employee Details Deleted";
    }

    @Transactional
    public String updateBiddingAmount(BiddingModel bidder) {
        String msg="Amount adding";
        RestTemplate restTemplate = new RestTemplate();
        int productID=bidder.getProductID();
        //ProductResponse responseEntity;
        ProductResponse responseEntity = restTemplate.getForObject("http://localhost:8083/product/products/" + productID, ProductResponse.class);
        if(Objects.isNull(responseEntity)){
            return "Product not found";
        }
        String role =restTemplate.getForEntity("http://localhost:8082/api/findUser/role/"+bidder.getBidderId(), String.class).getBody();
        ProductResponse response= responseEntity;
        LocalDateTime productDt = response.getExpiryDateTime();
        LocalDateTime biddingtm = LocalDateTime.now();
        if(response.isActiveFlag()) {
            if(role.equals("BIDDER")) {
            if (biddingtm.compareTo(productDt) < 0) {
                    if (bidder.getAmount() > response.getMinAmount()) {
                        BiddingModel bd = repo.findByProductID(bidder.getProductID());
                        if (Objects.nonNull(bd)) {
                            bd.setAmount(bidder.getAmount());
                            bd.setBidderId(bidder.getBidderId());
                            repo.save(bd);
                        } else {
                            repo.save(bidder);
                        }
                        try{
                        restTemplate.put("http://localhost:8083/product/amount/id=" + productID + "&amount=" + bidder.getAmount(), String.class);
                        msg = "Amount successfully added";
                        } catch (RestClientException e) {
                        throw e;
                         }
                    } else {
                        msg = "Amount should be greater than" + response.getMinAmount();
                    }
            } else {
                msg = "End time for "+response.getProductId()+" is " + response.getExpiryDateTime();
            }
            }else{
                msg = "You have not registered as a bidder";
            }
        }else{
            msg = "Bidding for "+response.getProductId()+" is closed";
        }
        return msg;


    }

    public BiddingModel getProductById(int id) {
       BiddingModel userOpt =  repo.findByProductID(id);
        return userOpt;
    }
}

