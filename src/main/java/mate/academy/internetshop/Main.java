package mate.academy.internetshop;

import java.math.BigDecimal;
import mate.academy.internetshop.lib.Injector;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.service.ItemService;

public class Main {
    static Injector injector = Injector.getInstance("mate.academy.internetshop");

    public static void main(String[] args) {
        ItemService itemService = (ItemService) injector.getInstance(ItemService.class);
        itemService.create(new Item(0L,"item_1", new BigDecimal("19.0"), 34));
        itemService.create(new Item(0L,"item_2", new BigDecimal("28.0"), 12));
        itemService.create(new Item(0L,"item_3", new BigDecimal("46.0"), 0));
        itemService.create(new Item(0L,"item_4", new BigDecimal("56.0"), 1));
        itemService.create(new Item(0L,"item_5", new BigDecimal("0.1"), 3));
        System.out.println("after adding");
        itemService.getAll().forEach(System.out::println);
        System.out.println("delete item with id == 2");
        System.out.println(itemService.delete(2L));
        System.out.println("after deleting");
        itemService.getAll().forEach(System.out::println);
        System.out.println("all available");
        itemService.getAllAvailable().forEach(System.out::println);
        System.out.println("get item with id == 1");
        System.out.println(itemService.get(1L));
        System.out.println("update item with id == 1");
        itemService.update(new Item(1L,"updated_item_1",new BigDecimal("1.0"),90));
        System.out.println("all available");
        itemService.getAllAvailable().forEach(System.out::println);
    }
}
