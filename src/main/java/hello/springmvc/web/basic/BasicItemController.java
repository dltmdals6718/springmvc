package hello.springmvc.web.basic;

import hello.springmvc.domain.item.Item;
import hello.springmvc.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/basic/items")
public class BasicItemController {
    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/item";
    }

    @GetMapping("add")
    public String addForm() {
        return "basic/addForm";
    }

    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam Integer quantity,
                       Model model) {
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item", item);
        return "basic/item";
    }

    //@PostMapping("add")
    public String addItemV2(@ModelAttribute("item") Item item,
                            Model model) {
        itemRepository.save(item);
        //model.addAttribute("item", item); 이 과정을 생략 가능하다.
        // @ModelAttribute(name="item")으로 name을 지정한 값으로 모델에 담긴다.
        return "basic/item";
    }

    //@PostMapping("add")
    public String addItemV3(@ModelAttribute Item itemName) {
        itemRepository.save(itemName);
//        name 속성이 생략되면
//        클래스명의 첫 글자를 소문자로 바꿔 모델이 담는다.
//                HelloData -> helloData로 모델이 담김.
//                주의! 파라미터명이 아닌 클래스명으로 모델에 담는다.
        return "basic/item";
    }

    //@PostMapping("add")
    public String addItemV4(@ModelAttribute Item item) {
        itemRepository.save(item);
//        @ModelAttribute 생략도 가능하다. 객체의 경우 @ModelAttribute가, 단순 타입인 경우 @RequestParam 적용
        return "basic/item";
    }

    //@PostMapping("add")
    public String addItemV5(Item item) {
        itemRepository.save(item);
        //return "basic/item"; 새로고침시 POST 요청을 또 보냄.
        return "redirect:/basic/items/" + item.getId(); // PRG 적용
    }

    @PostMapping("add")
    public String addItemV6(Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/basic/items/{itemId}"; // 치환이 된거 외에는 쿼리 파라미터로 넘어간다.
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/basic/items/{itemId}"; // redirect시 {itemId}처럼 @PathVariable을 쓸 수 있게 해준다.
    }

    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }
}
