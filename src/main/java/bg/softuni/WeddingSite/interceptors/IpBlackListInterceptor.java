//package bg.softuni.WeddingSite.interceptors;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class IpBlackListInterceptor implements HandlerInterceptor {
//    private final List<String> blacklistedIpAddresses = new ArrayList<>();
//
//    public IpBlackListInterceptor() {
//        blacklistedIpAddresses.add("30.118.215.229");
//        blacklistedIpAddresses.add("86.137.70.226");
//        blacklistedIpAddresses.add("63.10.194.239");
//        blacklistedIpAddresses.add("107.172.118.4");
//        blacklistedIpAddresses.add("133.87.137.163");
//    }
//
////    @Override
////    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////        String ipAddress = request.getRemoteAddr();
////        System.out.println(ipAddress);
////        if(blacklistedIpAddresses.contains(ipAddress)) {
////            response.sendRedirect("/error");
////        }
////        return true;
////    }
//}
