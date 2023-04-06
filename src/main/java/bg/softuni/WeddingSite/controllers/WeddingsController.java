package bg.softuni.WeddingSite.controllers;

import bg.softuni.WeddingSite.models.dtos.WeddingRegDTO;
import bg.softuni.WeddingSite.services.WeddingService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class WeddingsController {
    private final WeddingService weddingService;

    public WeddingsController(WeddingService weddingService) {
        this.weddingService = weddingService;
    }

    @ModelAttribute("weddingRegDTO")
    public WeddingRegDTO initForm() {
        return new WeddingRegDTO();
    }

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

    @GetMapping("/create")
    public String create(){
        return "create";
    }

    @PostMapping("/create")
    public String createWedding(@Valid WeddingRegDTO weddingRegDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("weddingRegDTO", weddingRegDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.weddingRegDTO", bindingResult);

            return "redirect:/create";
        }

        this.weddingService.addingWedding(weddingRegDTO);
        return "redirect:/profile";
    }
}
