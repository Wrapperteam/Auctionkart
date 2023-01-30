package com.wrapperteam.bidding.service;

import com.wrapperteam.bidding.dto.ProductResponse;
import com.wrapperteam.bidding.model.BiddingModel;
import com.wrapperteam.bidding.repository.BiddingrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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


    public String updateBiddingAmount(BiddingModel bidder) {
        String msg="Amount adding";
        RestTemplate restTemplate = new RestTemplate();
        int productID=bidder.getProductID();
        ResponseEntity<ProductResponse> responseEntity = restTemplate.getForEntity("http://localhost:8082/product/products/"+productID, ProductResponse.class);
        ProductResponse response= responseEntity.getBody();
        LocalDateTime productDt = response.getExpiryDateTime();
        LocalDateTime biddingtm = LocalDateTime.now();
        if(biddingtm.compareTo(productDt)<0){
            if(bidder.getAmount()>response.getMinAmount()){
                BiddingModel bd=repo.findByProductID(bidder.getProductID()).get();
                if(Objects.nonNull(bd)){
                    bd.setAmount(bidder.getAmount());
                    repo.save(bd);
                }else {
                    repo.save(bidder);
                }
                restTemplate.getForEntity("http://localhost:8082/amount/id="+productID+"&amount="+bd.getAmount(), String.class);
                msg = "Amount successfully added";
            }else {
                msg = "Amount should be greater than" + response.getMinAmount();
            }

        }else {
            msg = "Time should be greater than" + response.getExpiryDateTime();
        }

        return msg;


    }

    public BiddingModel getProductById(int id) {
        Optional<BiddingModel> userOpt =  repo.findByProductID(id);
        return userOpt.orElseGet(null);
    }
}

