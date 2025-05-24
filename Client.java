// Импортираме Serializable – това ни позволява да запишем клиента във файл
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

// Класът Client представя един клиент в магазина
// Той има име, пари и количка с продукти
public class Client implements Serializable {

    // Името на клиента – например: "Мария Георгиева"
    private String name;

    // Колко пари има клиентът – например: 15.00
    private double money;

    // Количката на клиента – съдържа продуктите и желаното количество от всеки
    // Пример: мляко → 2 броя, хляб → 1 брой
    private Map<Product, Integer> cart;

    // Конструктор: създава нов клиент с име и начална сума пари
    public Client(String name, double money) {
        this.name = name;
        this.money = money;
        this.cart = new HashMap<>(); // започваме с празна количка
    }

    // Връща името на клиента
    public String getName() {
        return name;
    }

    // Връща колко пари има клиентът
    public double getMoney() {
        return money;
    }

    // Метод за добавяне на продукт в количката
    // Например: client.addToCart(мляко, 2)
    public void addToCart(Product product, int quantity) {
        cart.put(product, quantity);
    }

    // Връща цялата количка (списък с продукти и количества)
    public Map<Product, Integer> getCart() {
        return cart;
    }

    // Метод, който намалява парите на клиента след покупка
    // Пример: client.deductMoney(5.50) → маха 5.50 от наличните пари
    public void deductMoney(double amount) {
        this.money -= amount;
    }

    // Метод, който връща информация за клиента във видим вид (при печат)
    @Override
    public String toString() {
        return "Клиент: " + name + " - Пари: " + money;
    }
}
