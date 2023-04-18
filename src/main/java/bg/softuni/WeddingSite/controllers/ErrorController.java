package bg.softuni.WeddingSite.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping(value = "errors", method = RequestMethod.GET)
    public String renderErrorPage(HttpServletRequest httpRequest) {

        int httpErrorCode = getErrorCode(httpRequest);

        switch (httpErrorCode) {
            case 401: {
                return "401";
            }
            case 404: {
                return "404";
            }
            case 500: {
                return "500";
            }
            default: return "404";
        }
    }

    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest
                .getAttribute("javax.servlet.error.status_code");
    }
}