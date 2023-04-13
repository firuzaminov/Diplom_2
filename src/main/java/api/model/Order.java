package api.model;
import java.util.List;

public class Order {
    private List<String> ingredients;

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    private Order(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public static Order getCorrectOrder(String correctHashFirst, String correctHashSecond) {
        return new Order(List.of(correctHashFirst, correctHashSecond));
    }

    public static Order getOrderWithWrongHash(String wrongHashFirst, String wrongHashSecond) {
        return new Order(List.of(wrongHashFirst, wrongHashSecond));
    }

    public static Order getEmptyOrder() {
        return new Order(List.of());
    }
}