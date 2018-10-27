package wecc.examples.springboot.rest.client.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import wecc.examples.springboot.rest.client.model.Product;

import java.util.List;

@Controller
public class ProductController {

    @Autowired
    private RestTemplate restTemplate;
    @Value("${rest.server.url}")
    private String restServerUrl;


    @GetMapping()
    public String welcome() {
        return "welcome";
    }

    @GetMapping("/list")
    public String list(Model model) {
        List<Product> products = restTemplate.getForObject(restServerUrl + "products",List.class);
        model.addAttribute("products", products);
        return "list";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("product", new Product());
        return "add";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Product product) {
        restTemplate.exchange(restServerUrl + "product", HttpMethod.POST, new HttpEntity<>(product),Product.class);
        return "welcome";
    }

}
