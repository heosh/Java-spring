package hello.itemservice.web.validation;

import hello.itemservice.domain.item.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Slf4j
@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
        // class로 넘어오는 타입과 item이 같은지 검즘
        // class로 넘어오는 타입과 item의 자식 class가 같은지 검즘
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;
        // errors = bindingResult

        log.info("objectName={}", errors.getObjectName());
        log.info("target={}", target);

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName", "required");

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }

        //특정 필드가 아닌 복합 롤 검증
        if(item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if(resultPrice < 10000) {
               errors.reject("totalPriceMin", new Object[]{10000, resultPrice}, null);
            }
        }
    }
}
