package com.studi.jo;

import com.studi.jo.offer.domain.Offer;
import com.studi.jo.offer.service.OfferService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@SpringBootApplication
@Controller
public class HomeController {

    private final OfferService offerService;

    public HomeController(OfferService offerService) {
        this.offerService = offerService;
    }

    public static void main(String[] args) {
        SpringApplication.run(com.studi.jo.HomeController.class, args);
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public ModelAndView login(HttpServletRequest req, HttpSession session) {
        ModelAndView mav = new ModelAndView("login");
        if (session != null) {
            SavedRequest savedRequest = (SavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            if (savedRequest != null) {
                mav.addObject("redirectUrl", savedRequest.getRedirectUrl());
            }
        }
        return mav;
    }

    @GetMapping("/offers")
    public String home(Model model) {
        List<Offer> offers = offerService.getAllOffers();
        model.addAttribute("offers", offers);
        model.addAttribute("hasOffers", !offers.isEmpty());
        return "offers";
    }

    @GetMapping("/checkout")
    public String checkout(){
        return "checkout";
    }

    @GetMapping("/checkout/validated")
    public String checkoutValidated(){
        return "checkout-validated";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }

}
