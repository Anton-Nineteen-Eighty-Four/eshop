package com.antonhulevich.eshop.service;

import com.antonhulevich.eshop.dao.BucketRepository;
import com.antonhulevich.eshop.dao.ProductRepository;
import com.antonhulevich.eshop.domain.*;
import com.antonhulevich.eshop.dto.BucketDetailDto;
import com.antonhulevich.eshop.dto.BucketDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BucketServiceImpl implements BucketService{
    private final ProductRepository productRepository;
    private final BucketRepository bucketRepository;
    private final UserService userService;
    private final OrderService orderService;

    public BucketServiceImpl(ProductRepository productRepository, BucketRepository bucketRepository,
                             UserService userService, OrderService orderService) {
        this.productRepository = productRepository;
        this.bucketRepository = bucketRepository;
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    @Transactional
    public Bucket createBucket(User user, List<Long> productIds) {
        Bucket bucket = new Bucket();
        bucket.setUser(user);
        List<Product> productList = getCollectRefProductsByIds(productIds);
        bucket.setProducts(productList);
        return bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void addProducts(Bucket bucket, List<Long> productIds) {
        List<Product> productList = bucket.getProducts();

        List<Product> newProductList;

        if(productList == null){
            newProductList = new ArrayList<>();
        } else {
            newProductList = new ArrayList<>(productList);
        }

        newProductList.addAll(getCollectRefProductsByIds(productIds));

        bucket.setProducts(newProductList);
        bucketRepository.save(bucket);
    }

    private List<Product> getCollectRefProductsByIds(List<Long> productIds){
        List<Product> productList =
                productIds.stream()
                        .map(productRepository::getReferenceById)
                        .collect(Collectors.toList());
        return productList;
    }

    @Override
    @Transactional(readOnly = true)
    public BucketDto getBucketDtoByUser(String name) {

        User user = userService.getUserByName(name);

        if(user == null || user.getBucked() == null){
            return new BucketDto();
        }

        BucketDto bucketDto = new BucketDto();

        Map<Long, BucketDetailDto> mapByProductId = new HashMap<>();

        List<Product> productList = user.getBucked().getProducts();

        for (Product product : productList) {
            BucketDetailDto detail = mapByProductId.get(product.getId());
            if(detail == null){
                mapByProductId.put(product.getId(), new BucketDetailDto(product));
            } else {
                detail.setAmount(detail.getAmount().add(BigDecimal.ONE));
                detail.setSum(detail.getSum().add(product.getPrice()));
            }
        }

        bucketDto.setBucketDetail(new ArrayList<>(mapByProductId.values()));
        bucketDto.aggregate();

        return bucketDto ;
    }

    @Override
    @Transactional
    public void deleteProduct(String name, Long productId){
        User user = userService.getUserByName(name);
        if (user == null || user.getBucked() == null) {
            return;
        }

        Bucket bucket = user.getBucked();
        List<Product> productList = bucket.getProducts();

        Product productToRemove = null;

        for (Product p : productList) {
            if (Objects.equals(productId, p.getId())) {
                productToRemove = p;
                break;
            }
        }

        if (productToRemove != null) {
            productList.remove(productToRemove);
        }

        bucket.setProducts(productList);
        bucketRepository.save(bucket);
    }

    @Override
    @Transactional
    public void commitBucketToOrder(String username){
        User user = userService.getUserByName(username);
        if(user == null){
            throw new RuntimeException("User is not found");
        }
        Bucket bucket = user.getBucked();
        if(bucket == null || bucket.getProducts().isEmpty()){
            return;
        }

        Order order = new Order();
        order.setStatus(OrderStatus.NEW);
        order.setUser(user);

        Map<Product, Long> productWithAmount = bucket.getProducts().stream()
                .collect(Collectors.groupingBy(product -> product, Collectors.counting()));

        List<OrderDetails> orderDetails = productWithAmount.entrySet().stream()
                .map(pair -> new OrderDetails(order, pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());

        BigDecimal total = orderDetails.stream()
                .map(detail -> detail.getPrice().multiply(detail.getAmount()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");

        orderService.saveOrder(order);
        bucket.getProducts().clear();
        bucketRepository.save(bucket);
    }
}
