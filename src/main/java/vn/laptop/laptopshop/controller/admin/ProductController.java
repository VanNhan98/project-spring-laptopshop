package vn.laptop.laptopshop.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.laptop.laptopshop.domain.Product;
import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.service.ProductService;
import vn.laptop.laptopshop.service.UploadService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ProductController {

    private final UploadService uploadService;
    private final ProductService productService;

    public ProductController(UploadService uploadService, ProductService productService) {
        this.uploadService = uploadService;
        this.productService = productService;
    }

    @GetMapping("/admin/product")
    public String getProduct() {
        return "admin/product/show";
    }

    // hien thi giao dien form
    @GetMapping("/admin/product/create")
    public String getCreateProductPage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "admin/product/create";
    }

    // lay thong tin ben form va hien thi vs id, email va fullname
    @RequestMapping("/admin/product")
    public String getUserPage(Model model) {
        List<Product> products = this.productService.getAllProducts();
        model.addAttribute("product1", products);
        return "admin/product/show";
    }

    @PostMapping("/admin/product/create")
    public String createPorductPage(Model model, @ModelAttribute("newProduct") Product nhanProduct,
            @RequestParam("loadFile") MultipartFile file) {
        String image = this.uploadService.handleSaveUploadFile(file, "product");
        nhanProduct.setImage(image);
        this.productService.handleSaveProduct(nhanProduct);
        return "redirect:/admin/product";
        // TODO: process POST request

    }

}
