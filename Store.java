// Импортираме нужните библиотеки:
// - java.io.* – за работа с файлове
// - java.util.* – за списъци, карти (List, Map и др.)
import java.io.*;
import java.util.*;

public class Store {

    // Списък със стоките в магазина (продукти, които се продават)
    private List<Product> products;

    // Списък с касиерите, които работят в магазина
    private List<Cashier> cashiers;

    // Процент надценка за хранителни продукти (напр. 25%)
    private double foodMarkup;

    // Процент надценка за нехранителни продукти (напр. 40%)
    private double nonFoodMarkup;

    // Ако остават ≤ толкова дни до срока на годност – да се намали цената
    private int discountDays;

    // Процент на намалението при наближаващ срок на годност (напр. 15%)
    private double discountPercentage;

    // Общо приходи (пари от продажби)
    private double totalRevenue = 0;

    // Общо разходи (само за закупуване на стоки)
    private double totalCost = 0;

    // Конструктор: създава магазина и му задава правилата за надценка и намаления
    public Store(double foodMarkup, double nonFoodMarkup, int discountDays, double discountPercentage) {
        this.products = new ArrayList<>();       // празен списък със стоки
        this.cashiers = new ArrayList<>();       // празен списък с касиери
        this.foodMarkup = foodMarkup;
        this.nonFoodMarkup = nonFoodMarkup;
        this.discountDays = discountDays;
        this.discountPercentage = discountPercentage;
    }

    // Метод за добавяне на стока в магазина (зареждане от склад)
    public void addProduct(Product p) {
        products.add(p); // добавяме продукта в списъка
        totalCost += p.getCostPrice() * p.getQuantity(); // увеличаваме разходите с цената на тази стока
    }

    // Метод за добавяне на нов касиер
    public void addCashier(Cashier c) {
        cashiers.add(c);
    }

    // Основен метод – клиент опитва да си купи стоки с помощта на касиер
    public void sellProducts(Client client, Cashier cashier) throws NotEnoughQuantityException, IOException {
        double total = 0; // обща сума за плащане
        Map<Product, Integer> cart = client.getCart(); // количка на клиента (продукти и количества)
        Map<Product, Integer> actualProducts = new HashMap<>(); // продукти, които ще бъдат продадени

        // Обхождаме количката на клиента
        for (Map.Entry<Product, Integer> entry : cart.entrySet()) {
            Product p = entry.getKey();       // конкретният продукт
            int requested = entry.getValue(); // желаното количество

            if (p.isExpired()) {
                continue; // Пропускаме продукта, ако срокът му е изтекъл
            }

            // Ако няма достатъчно бройки от продукта → хвърляме грешка
            if (p.getQuantity() < requested) {
                throw new NotEnoughQuantityException("Няма достатъчно от " + p.getName() +
                        ". Иска се: " + requested + ", има: " + p.getQuantity());
            }

            // Определяме надценката – според категорията
            double markup = p.getCategory().equalsIgnoreCase("хранителна") ? foodMarkup : nonFoodMarkup;

            // Изчисляваме продажната цена с включена надценка и евентуално намаление
            double price = p.calculateSellingPrice(markup, discountDays, discountPercentage);

            // Добавяме крайната цена x количество към общата сума
            total += price * requested;

            // Запомняме, че ще продадем този продукт
            actualProducts.put(p, requested);
        }

        // Проверка дали клиентът има достатъчно пари
        if (client.getMoney() < total) {
            System.out.println("Клиентът няма достатъчно пари. Нужни: " +
                String.format("%.2f", total) + " лв. има: " +
                String.format("%.2f", client.getMoney()) + " лв.");
            return;
        }

        // Всичко е наред → извършваме покупка
        for (Map.Entry<Product, Integer> entry : actualProducts.entrySet()) {
            Product p = entry.getKey();
            int qty = entry.getValue();
            p.setQuantity(p.getQuantity() - qty); // Намаляваме наличностите
        }

        // Вземаме парите на клиента
        client.deductMoney(total);

        // Добавяме приход към общите приходи на магазина
        totalRevenue += total;

        // Създаваме и записваме касова бележка
        Receipt receipt = new Receipt(cashier, actualProducts, total);
        receipt.printAndSave();

        System.out.println("Успешна покупка. Издадена касова бележка.");
    }

    // Изчисляваме общите заплати на всички касиери
    public double calculateCashierSalaries() {
        return cashiers.stream().mapToDouble(Cashier::getSalary).sum();
    }

    // Изчисляваме печалбата на магазина:
    // Печалба = приходи - (разходи за продукти + заплати)
    public double getTotalProfit() {
        return totalRevenue - (totalCost + calculateCashierSalaries());
    }

    // Броим колко касови бележки са били издадени досега (четем от файловете в текущата папка)
    public int getIssuedReceiptsCount() {
        File folder = new File("."); // текущата директория
        return (int) Arrays.stream(folder.listFiles()) // всички файлове
                .filter(file -> file.getName().startsWith("receipt_") && file.getName().endsWith(".txt"))
                .count();
    }
}
