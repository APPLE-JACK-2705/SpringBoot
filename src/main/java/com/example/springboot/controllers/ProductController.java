package com.example.springboot.controllers;

import com.example.springboot.entities.Product;
import com.example.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String indexPage(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "product-views/index";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable(value = "id") Long id,
                              Model model){
        model.addAttribute("product", productService.getById(id));
        return "product-views/product_form";
    }

    @PostMapping("/product_update")
    public String updateProduct(Product product){
        productService.update(product);
        return "redirect:/product";
    }

    @GetMapping("/new")
    public String newProduct(Product product){
        return "product-views/new_product_form";
    }

    @PostMapping("/new")
    public String createNewProduct(Product product){
        productService.add(product);
        return "redirect:/product";
    }

    @GetMapping("/remove/{id}")
    public String removeProduct(@PathVariable(value = "id") Long id){
        productService.remove(id);
        return "redirect:/product";
    }
}
