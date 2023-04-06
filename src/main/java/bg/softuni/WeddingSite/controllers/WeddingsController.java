package bg.softuni.WeddingSite.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class WeddingsController {

    @GetMapping("/weddings")
    public String homeWeddings(){
        return "weddings";
    }

    @GetMapping("/weddings/{id}")
    public String wedding(@PathVariable("id") Long weddingId){
        return "weddingsPlanner";
    }

    @GetMapping("/vendors")
    public String vendors(){
        return "vendors";
    }
}
