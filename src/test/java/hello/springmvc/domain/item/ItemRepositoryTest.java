package hello.springmvc.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ItemRepositoryTest {

    ItemRepository2 itemRepository2 = new ItemRepository2();

    @AfterEach
    void afterEach() {
        itemRepository2.clearStore();
    }

    @Test
    void save() {
        Item item = new Item("itemA", 10000, 10);
        Item saveItem = itemRepository2.save(item);
        Item findItem = itemRepository2.findById(saveItem.getId());

        Assertions.assertThat(findItem).isEqualTo(saveItem);
    }

    @Test
    void findAll() {
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemB", 10000, 20);
        itemRepository2.save(item1);
        itemRepository2.save(item2);

        List<Item> result = itemRepository2.findAll();
        Assertions.assertThat(result.size()).isEqualTo(2);
        Assertions.assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem() {
        Item item = new Item("item1", 10000, 10);

        Item savedItem = itemRepository2.save(item);
        Long itemId = savedItem.getId();

        Item updateParam = new Item("item2", 20000, 20);
        itemRepository2.update(itemId, updateParam);

        Item findItem = itemRepository2.findById(itemId);

        Assertions.assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        Assertions.assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        Assertions.assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());


    }
}
