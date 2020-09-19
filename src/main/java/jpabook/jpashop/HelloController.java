package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

  @GetMapping("hello")
  public String hello(Model model) {
    // view에 넘기는 데이터
    model.addAttribute("data", "hello!!");

    // 보여줄 view
    return "hello";
  }
}
