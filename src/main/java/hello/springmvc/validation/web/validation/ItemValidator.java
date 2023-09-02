package hello.springmvc.validation.web.validation;

import hello.springmvc.validation.item.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

//@Component
public class ItemValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        //return Item.class == clazz; 도 가능하나 위의 코드는 자식까지 커버가된다. item == subItem
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item)target;
        //검증 로직
        // errors에도 rejectValue가 있다.
        if(!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }
        if(item.getPrice()==null||item.getPrice()<1000||item.getPrice()>10000) {
            errors.rejectValue("price", "range", new Object[]{1000, 10000}, null);
        }
        if(item.getQuantity()==null||item.getQuantity()>100) {
            errors.rejectValue("quantity", "max", new Object[]{100}, null);
        }

        //특정 필드가 아닌 복합 검증
        if(item.getPrice()!=null&&item.getQuantity()!=null) {
            int resultPrice = item.getPrice()*item.getQuantity();
            if(resultPrice<10000) {
                errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }

    }
}
