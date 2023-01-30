package com.auction.productService.service;

import com.auction.productService.model.Product;
import com.auction.productService.productDto.BiddingDto;
import com.auction.productService.productDto.ProductDto;
import com.auction.productService.productDto.UserDto;
import com.auction.productService.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private JavaMailSender mailSender;
    public ProductDto addProduct(ProductDto productDto) {
        Product product= new Product();

        try{
            product= productBuilder(productDto);
        } catch(Exception e){
            e.fillInStackTrace();
        }
         productRepository.save(product);
        return productDto;
    }


    public List<ProductDto> getAllProducts() {
        List<ProductDto> list=new ArrayList<>();

       try
       {
        ProductDto productDto=new ProductDto();
         list=productRepository.findAll().stream().map(p-> productDtoBuilder(p)).toList();
       } catch(Exception e){
           e.fillInStackTrace();
       }
        return list;
    }

    public List<ProductDto> getByName(String name) {
        return productRepository.findAll().stream().filter(p->p.getProductName().equals(name)).map(p->productDtoBuilder(p)).toList();
    }

    public List<ProductDto> getByType(String type) {
        return productRepository.findAll().stream().filter(p->p.getProductType().equals(type)).map(p->productDtoBuilder(p)).toList();
    }
    private Product productBuilder(ProductDto productDto) {
        Product product=null;
        return product.builder()
                .sellerId(productDto.getSellerId())
                .username(productDto.getUsername())
                .productName(productDto.getProductName())
                .productType(productDto.getProductType())
                .description(productDto.getDescription())
                .url(productDto.getUrl())
                .expiryDateTime(productDto.getExpiryDateTime())
                .minAmount(productDto.getMinAmount())
                .ActiveFlag(productDto.isActiveFlag())
                .build();
    }
    private ProductDto productDtoBuilder(Product product) {
        ProductDto productDto=null;
        return productDto.builder()
                .sellerId(product.getSellerId())
                .username(product.getUsername())
                .productName(product.getProductName())
                .productType(product.getProductType())
                .description(product.getDescription())
                .url(product.getUrl())
                .expiryDateTime(product.getExpiryDateTime())
                .minAmount(product.getMinAmount())
                .ActiveFlag(product.isActiveFlag())
                .build();
    }


    public String deleteById(int id) {
        if(productRepository.findById(id).isPresent()) {
            productRepository.deleteById(id);
            return id + " is deleted successfully";
        }
        else {
            return id + " is not present";
        }
    }

    public boolean validation(ProductDto productDto) {
        if(productDto.getProductName()==null||productDto.getProductType()==null||productDto.getMinAmount()==0||productDto.getExpiryDateTime()==null||productDto.getSellerId()==0) {
            return false;
        }
        else return true;
    }
    public boolean validation(Product product) {
        ProductDto productDto=productDtoBuilder(product);
        return validation(productDto);
    }

    public String updateproduct(Product product) {
        Product updateProduct=new Product();
        Optional<Product> product1=productRepository.findById(product.getProductId());
      if(product1.isPresent()){
          updateProduct.setProductName(product.getProductName());
          updateProduct.setProductType(product.getProductType());
          updateProduct.setDescription(product.getDescription());
          updateProduct.setUrl(product.getUrl());
          updateProduct.setExpiryDateTime(product.getExpiryDateTime());
          updateProduct.setMinAmount(product.getMinAmount());
          updateProduct.setActiveFlag(product.isActiveFlag());
          return "product details updated";
      }else  {
          productRepository.save(product);
          return "new product created ";
      }

    }
    public List<ProductDto> getBySellerId(int id) {
        return productRepository.findAll().stream().filter(p->p.getSellerId()==id).map(p->productDtoBuilder(p)).toList();
    }

    public Product getByProductId(int id) {
        return productRepository.findById(id).orElseGet(null);
    }

    public List<Product> getByUsername(String name) {
        List<Product> product= productRepository.findByUsername(name);
        return product;
    }

    public String updateAmount(int productId, double amount) {
        Product product=productRepository.findById(productId).get();
        if(Objects.isNull(product)){
            return "please check your ProductId";
        }
        product.setMinAmount(amount);
        productRepository.save(product);
        return amount+" updated";
    }

    public void dateVerify(){
        productRepository.findAll().stream().filter(product -> product.isActiveFlag()&&(product.getExpiryDateTime().isBefore(LocalDateTime.now()))).map(p->sendEmail(p)).toList();
    }


    public Product sendEmail(Product product) {
        RestTemplate restTemplate=new RestTemplate();
        SimpleMailMessage message = new SimpleMailMessage();
        ResponseEntity<BiddingDto> biddingDto=restTemplate.getForEntity("http://localhost:8085/bidder/details/"+product.getProductId(),BiddingDto.class);
        Product product2=productRepository.findById(biddingDto.getBody().getProductID()).get();
        ResponseEntity<UserDto> seller=restTemplate.getForEntity("http://localhost:8082/api/findUser/"+product2.getSellerId(),UserDto.class);
        ResponseEntity<UserDto> bidder=restTemplate.getForEntity("http://localhost:8082/api/findUser/"+biddingDto.getBody().getBidderId(),UserDto.class);
        message.setTo(seller.getBody().getEmail());
        message.setSubject("Product "+product.getProductName()+" action is closed");
        message.setText("body");
        mailSender.send(message);
        product.setActiveFlag(false);
        productRepository.save(product);
        return product;
    }

}
