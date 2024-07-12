package vn.laptop.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.laptop.laptopshop.domain.Cart;
import vn.laptop.laptopshop.domain.CartDetail;
import vn.laptop.laptopshop.domain.Order;
import vn.laptop.laptopshop.domain.OrderDetail;
import vn.laptop.laptopshop.domain.Product;
import vn.laptop.laptopshop.domain.Product_;
import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.repository.CartDetailRepository;
import vn.laptop.laptopshop.repository.CartRepository;
import vn.laptop.laptopshop.repository.OrderDetailRepository;
import vn.laptop.laptopshop.repository.OrderRepository;
import vn.laptop.laptopshop.repository.ProductRepository;
import vn.laptop.laptopshop.service.specification.ProductSpecs;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderRepository orderRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService,
            OrderDetailRepository orderDetailRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
        this.orderDetailRepository = orderDetailRepository;
        this.orderRepository = orderRepository;
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    // public Page<Product> fetchProducts(Pageable page) {
    // return this.productRepository.findAll(page);
    // }
    public Page<Product> fetchProducts(Pageable page) {
        return this.productRepository.findAll(page);
    }

    public Page<Product> fetchProductsWithSpec(Pageable page, String name) {
        return this.productRepository.findAll(ProductSpecs.nameLike(name), page);
    }

    // public Page<Product> fetchProductsWithSpec(Pageable page, double min) {
    // return this.productRepository.findAll(ProductSpecs.minPrice(min), page);
    // }

    // public Page<Product> fetchProductsWithSpec(Pageable page, double max) {
    // return this.productRepository.findAll(ProductSpecs.maxPrice(max), page);
    // }

    // public Page<Product> fetchProductsWithSpec(Pageable page, String factory) {
    // return this.productRepository.findAll(ProductSpecs.matchFactory(factory),
    // page);
    // }

    // public Page<Product> fetchProductsWithSpec(Pageable page, List<String>
    // factory) {
    // return this.productRepository.findAll(ProductSpecs.matchListFactory(factory),
    // page);
    // }

    // public Page<Product> fetchProductsWithSpec(Pageable page, String price) {
    // if (price.equals("10-toi-15-trieu")) {
    // double min = 10000000;
    // double max = 15000000;
    // return this.productRepository.findAll(ProductSpecs.matchPrice(min, max),
    // page);
    // } else if (price.equals("15-toi-30-trieu")) {
    // double min = 15000000;
    // double max = 30000000;
    // return this.productRepository.findAll(ProductSpecs.matchPrice(min, max),
    // page);
    // }

    // return this.productRepository.findAll(page);

    // }

    // public Page<Product> fetchProductsWithSpec(Pageable page, List<String> price)
    // {
    // Specification<Product> combineSpec = (root, query, criteriaBuilder) ->
    // criteriaBuilder.disjunction();
    // int count = 0;
    // for (String p : price) {
    // double min = 0;
    // double max = 0;
    // switch (p) {
    // case "10-toi-15-trieu":
    // min = 10000000;
    // max = 15000000;
    // count++;
    // break;
    // case "15-toi-20-trieu":
    // min = 15000000;
    // max = 20000000;
    // count++;
    // case "20-toi-25-trieu":
    // min = 20000000;
    // max = 30000000;
    // count++;
    // }
    // if (min != 0 && max != 0) {
    // Specification<Product> rangeSpec = ProductSpecs.matchMultiplePrice(min, max);
    // combineSpec = combineSpec.or(rangeSpec);
    // }

    // }
    // if (count == 0) {
    // return this.productRepository.findAll(page);

    // }
    // return this.productRepository.findAll(combineSpec, page);

    // }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session, long quantity) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            // check users đã có Cart chưa? nếu chưa? tạo ms
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                // tao moi
                Cart otherCart = new Cart();
                otherCart.setUser(user);
                otherCart.setSum(0);
                cart = this.cartRepository.save(otherCart);
            }

            // save cart_detail

            Optional<Product> productOptional = this.productRepository.findById(productId);
            if (productOptional.isPresent()) {
                Product realProduct = productOptional.get();

                // check sp đã tùng add vào cart hay chưa
                CartDetail oldCartDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProduct);
                if (oldCartDetail == null) {

                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProduct);
                    cartDetail.setPrice(realProduct.getPrice());
                    cartDetail.setQuantity(quantity);
                    this.cartDetailRepository.save(cartDetail);

                    // update cart
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);

                } else {
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + quantity);
                }

            }

        }

    }

    public Cart fetchByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleRemoveCartDetail(long cartDetailId, HttpSession session) {
        Optional<CartDetail> cartDetaiLOptional = this.cartDetailRepository.findById(cartDetailId);
        if (cartDetaiLOptional.isPresent()) {
            CartDetail cartDetail = cartDetaiLOptional.get();

            Cart currentCart = cartDetail.getCart();
            // delete cartdetail
            this.cartDetailRepository.deleteById(cartDetailId);

            // update cart
            if (currentCart.getSum() > 1) {
                // update current cart
                int s = currentCart.getSum() - 1;
                currentCart.setSum(s);
                session.setAttribute("sum", s);
                this.cartRepository.save(currentCart);
            }

            else {
                this.cartRepository.deleteById(currentCart.getId());
                session.setAttribute("sum", 0);
            }

        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailRepository.save(currentCartDetail);
            }
        }
    }

    public void handlePlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        // step 1: get cart by user
        Cart cart = this.cartRepository.findByUser(user);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                // create order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverName(receiverName);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("PENDING");

                double sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += (cd.getPrice() * cd.getQuantity());
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);

                // create orderDetail

                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                }

                // step 2: delete cartDetail and cart
                for (CartDetail cd : cartDetails) {
                    this.cartDetailRepository.deleteById(cd.getId());
                }

                this.cartRepository.deleteById(cart.getId());

                // step 3: update session
                session.setAttribute("sum", 0);
            }
        }

    }
}
