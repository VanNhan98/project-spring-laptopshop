package vn.laptop.laptopshop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.laptop.laptopshop.domain.Cart;
import vn.laptop.laptopshop.domain.CartDetail;
import vn.laptop.laptopshop.domain.Product;
import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.repository.CartDetailRepository;
import vn.laptop.laptopshop.repository.CartRepository;
import vn.laptop.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }

    public Product createProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> fetchProducts() {
        return this.productRepository.findAll();
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
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
                    cartDetail.setQuantity(1);
                    this.cartDetailRepository.save(cartDetail);

                    // update cart
                    int sum = cart.getSum() + 1;
                    cart.setSum(sum);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", sum);

                } else {
                    oldCartDetail.setQuantity(oldCartDetail.getQuantity() + 1);
                }

            }

        }

    }

    public Cart fetchBuyUser(User user) {
        return this.cartRepository.findByUser(user);
    }
}
