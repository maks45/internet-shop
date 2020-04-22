package mate.academy.internetshop.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Item {
    private Long id;
    private String name;
    private BigDecimal price;
    private int count;

    public Item(Long id, String name, BigDecimal price, int count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        Item item = (Item) o;
        return count == item.count
                && id.equals(item.id)
                && name.equals(item.name)
                && price.equals(item.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, count);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", price=" + price
                + ", count=" + count
                + '}';
    }
}
