// Импортираме класа LocalDate – той ни помага да работим с дати (напр. срок на годност)
import java.time.LocalDate;

public class Main {

    // Това е основният метод, който Java стартира първи, когато пуснеш програмата
    public static void main(String[] args) {

        // 1. Създаваме три продукта – мляко, хляб и шампоан

        // Мляко: ID = 1, име = "Мляко", цена на доставка = 1 лв,
        // категория = хранителна, годно още 3 дни, налични бройки = 10
        Product milk = new Product(1, "Мляко", 1, "хранителна", LocalDate.now().plusDays(3), 10);

        // Хляб: годен още 1 ден, 20 бройки
        Product bread = new Product(2, "Хляб", 1, "хранителна", LocalDate.now().plusDays(1), 20);

        // Шампоан: нехранителен, скъп (1000 лв), годен още 6 месеца
        Product shampoo = new Product(3, "Шампоан", 1000, "нехранителна", LocalDate.now().plusMonths(6), 15);

        // 2. Създаваме касиер – човек, който ще обслужва клиента
        Cashier cashier = new Cashier(1, "Иван Петров", 1200.00);

        // 3. Създаваме клиент – човек, който ще пазарува
        // Той има 15 лв в портфейла
        Client client = new Client("Мария Георгиева", 15.00);

        // Клиентът избира да купи 2 млека
        client.addToCart(milk, 2);

        // ... 1 хляб
        client.addToCart(bread, 1);

        // ... 1 шампоан
        client.addToCart(shampoo, 1);

        // 4. Настройки на магазина – задаваме правила:
        double foodMarkup = 25.0;          // хранителните стоки поскъпват с 25%
        double nonFoodMarkup = 40.0;       // нехранителните – с 40%
        int discountDays = 2;              // ако остават 2 или по-малко дни → намаление
        double discountPercentage = 15.0;  // намалението е 15%

        // 5. Създаваме самия магазин и му задаваме тези правила
        Store store = new Store(foodMarkup, nonFoodMarkup, discountDays, discountPercentage);

        // 6. Зареждаме продукта в склада на магазина
        store.addProduct(milk);
        store.addProduct(bread);
        store.addProduct(shampoo);

        // Добавяме касиера в магазина
        store.addCashier(cashier);

        // 7. Опитваме се да продадем стоките от количката на клиента
        try {
            store.sellProducts(client, cashier); // подаваме клиента и касиера
        } catch (Exception e) {
            // Ако нещо се обърка (напр. липсват бройки), се показва съобщение
            System.out.println("Грешка при продажба: " + e.getMessage());
        }

        // 8. Показваме статистика:
        // Колко касови бележки сме издали досега
        System.out.println("Издадени касови бележки: " + store.getIssuedReceiptsCount());

        // Колко е печалбата на магазина (приходи – разходи)
        System.out.println("Печалба на магазина: " + String.format("%.2f", store.getTotalProfit()) + " лв.");
    }
}
