package com.auction.productService.service;

import com.auction.productService.model.Product;
import com.auction.productService.productDto.BiddingDto;
import com.auction.productService.productDto.ProductDto;
import com.auction.productService.productDto.UserDto;
import com.auction.productService.repository.ProductRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.activation.DataSource;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.catalina.User;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

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
                .productName(productDto.getProductName())
                .productType(productDto.getProductType())
                .description(productDto.getDescription())
                .url(productDto.getUrl())
                .expiryDateTime(productDto.getExpiryDateTime())
                .minAmount(productDto.getMinAmount())
                .activeFlag(productDto.isActiveFlag())
                .build();
    }
    private ProductDto productDtoBuilder(Product product) {
        ProductDto productDto=null;
        return productDto.builder()
                .sellerId(product.getSellerId())
                .productName(product.getProductName())
                .productType(product.getProductType())
                .description(product.getDescription())
                .url(product.getUrl())
                .expiryDateTime(product.getExpiryDateTime())
                .minAmount(product.getMinAmount())
                .activeFlag(product.isActiveFlag())
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
    public  Product getByProductId(int id) {
        Optional<Product> product=productRepository.findById(id);
        if(!product.isPresent()){
           return null;
        }
        return product.get();
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
        productRepository.findAll().stream().filter(product -> product.isActiveFlag()&&(product.getExpiryDateTime().isBefore(LocalDateTime.now()))).map(p-> {
            try {
                return sendEmail(p);
            } catch (DocumentException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
    public Product sendEmail(Product product) throws DocumentException {
        RestTemplate restTemplate=new RestTemplate();
        SimpleMailMessage message = new SimpleMailMessage();
        UserDto bidder=new UserDto();
        ResponseEntity<UserDto> seller=restTemplate.getForEntity("http://localhost:8082/api/findUser/"+product.getSellerId(),UserDto.class);
        ResponseEntity<BiddingDto> biddingDto=restTemplate.getForEntity("http://localhost:8085/bidder/details/"+product.getProductId(),BiddingDto.class);
        if(Objects.isNull(biddingDto.getBody())){

        }else {
            ResponseEntity<UserDto> bidderResponce = restTemplate.getForEntity("http://localhost:8082/api/findUser/" + biddingDto.getBody().getBidderId(), UserDto.class);
            bidder=bidderResponce.getBody();
        }
         //   message.setTo(seller.getBody().getEmail());
           // message.setSubject("Product "+product.getProductName()+" action is closed without any Bidding");
           // message.setText(msgBody());
            //mailSender.send(message);
       // } else {

           biddedDocuments(seller.getBody(),product,bidder);
          /*  message.setTo(seller.getBody().getEmail());
            message.setSubject("Product " + product.getProductName() + " action is  closed with "+product.getMinAmount());
            message.setText("body");
            mailSender.send(message);
        }*/
        product.setActiveFlag(false);
        productRepository.save(product);
        return product;
    }

    public void biddedDocuments(UserDto seller,Product product,UserDto bidder) throws DocumentException {
        try {
            Document document = new Document();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
// FileOutputStream baos=new FileOutputStream(new File("E:\\BusTicket_Booking\\InvoiceLocation\\Tickets.pdf"));
            PdfWriter.getInstance(document, baos);
            document.open();
            Paragraph p1=new Paragraph("Bidded Details");
            p1.setAlignment(Element.ALIGN_MIDDLE);
            document.add(p1);
            Image img = Image.getInstance("images\\"+product.getUrl());
            img.scaleAbsolute(200, 200);

            Phrase phrase = new Phrase();
            phrase.add(new Chunk(img, 350,-200));
            System.out.println(img);
            document.add(new Paragraph(phrase));
            document.add(new Paragraph("------------------------------------------------------------------"));
            document.add(new Paragraph("Product Details:"));
            document.add(new Paragraph("Product Id:" + product.getProductId()));
            document.add(new Paragraph("Product Name:" + product.getProductName()));
            document.add(new Paragraph("Product Type:" + product.getProductType()));
            document.add(new Paragraph("Product Description:" + product.getDescription()));
            document.add(new Paragraph("Product Expiry Date:" + product.getExpiryDateTime()));
            document.add(new Paragraph("-------------------------------------------------------------------"));
            document.add(new Paragraph("Seller Details:"));
            document.add(new Paragraph("Seller ID:" + seller.getUserId()));
            document.add(new Paragraph("Seller Name:"+seller.getName()));
            document.add(new Paragraph("Seller Phone number:"+seller.getPhoneNumber()));
            document.add(new Paragraph("Seller Email:"+seller.getUserEmail()));
            document.add(new Paragraph("-------------------------------------------------------------------"));
            if(Objects.isNull(bidder.getUserEmail())) {

            }else {
                document.add(new Paragraph("Bidder Details:"));
                document.add(new Paragraph("Bidder ID:" + bidder.getUserId()));
                document.add(new Paragraph("Bidder Name:" + bidder.getName()));
                document.add(new Paragraph("Bidder Phone number:" + bidder.getPhoneNumber()));
                document.add(new Paragraph("Bidder Email:" + bidder.getUserEmail()));
            }
            document.close();
            byte[] pdfBytes = baos.toByteArray();
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setTo(seller.getUserEmail());
            if(Objects.isNull(bidder.getUserEmail())) {
                mimeMessageHelper.setSubject("Product " + product.getProductName() + " auction is closed without any Bidding");
            }else{
                mimeMessageHelper.setSubject("Product " + product.getProductName() +" action is  closed with "+product.getMinAmount());
                mimeMessageHelper.setCc(bidder.getUserEmail());
            }
            mimeMessageHelper.setText("Hi "+seller.getName());
            FileSystemResource fileSystem = new FileSystemResource(product.getUrl());
            DataSource source = new ByteArrayDataSource( new ByteArrayInputStream(pdfBytes),"document/pdf");
            mimeMessageHelper.addAttachment("Bidding.pdf", source);
            mailSender.send(mimeMessage);
            } catch (Exception e) {e.printStackTrace();
            System.out.println("Exception occured in document generation!");
        }
    }

}
