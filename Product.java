// Импортираме:
// - Serializable: за да може обектът да се записва във файл (сериализация)
// - LocalDate: за работа с дати
// - ChronoUnit: за изчисляване на дни между две дати
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Клас, който описва един продукт в магазина (например мляко, хляб, шампоан)
public class Product implements Serializable {

    // Уникален номер на продукта (пример: 1)
    private int id;

    // Името на продукта (пример: "Мляко")
    private String name;

    // Цена на доставка на продукта (колко струва на магазина да го купи)
    private double costPrice;

    // Категория: "хранителна" или "нехранителна"
    private String category;

    // Дата, до която продуктът е годен за употреба
    private LocalDate expiryDate;

    // Наличен брой бройки от продукта в склада
    private int quantity;

    // Конструктор: използва се, когато създаваме нов продукт
    public Product(int id, String name, double costPrice, String category, LocalDate expiryDate, int quantity) {
        this.id = id;
        this.name = name;
        this.costPrice = costPrice;
        this.category = category;
        this.expiryDate = expiryDate;
        this.quantity = quantity;
    }

    // Проверка дали срокът на годност е изтекъл
    public boolean isExpired() {
        return expiryDate.isBefore(LocalDate.now());
    }

    // Изчислява колко дни остават до изтичане на срока на годност
    public int getDaysToExpire() {
        return (int) ChronoUnit.DAYS.between(LocalDate.now(), expiryDate);
    }

    // Изчислява крайната цена за клиента:
    // - с надценка (markup)
    // - с евентуално намаление при наближаващ срок на годност
    public double calculateSellingPrice(double markupPercentage, int discountThresholdDays, double discountPercentage) {
        if (isExpired()) {
            return 0.0; // ако е с изтекъл срок → не се продава
        }

        // Добавяме надценката към цената
        double price = costPrice + (costPrice * markupPercentage / 100);

        // Ако остават малко дни до изтичане → намаление
        if (getDaysToExpire() <= discountThresholdDays) {
            price -= price * (discountPercentage / 100);
        }

        // Закръгляме до 2 знака след десетичната запетая
        return Math.round(price * 100.0) / 100.0;
    }

    // Метод за връщане на ID-то
    public int getId() {
        return id;
    }

    // Метод за връщане на името на продукта
    public String getName() {
        return name;
    }

    // Метод за вземане на доставната цена
    public double getCostPrice() {
        return costPrice;
    }

    // Връща категорията на продукта (хранителна или не)
    public String getCategory() {
        return category;
    }

    // Връща срока на годност
    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    // Връща наличното количество
    public int getQuantity() {
        return quantity;
    }

    // Промяна на количеството (например след продажба)
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Показва продукта като текст (за печат/дебъгване)
    @Override
    public String toString() {
        return name + " (" + category + ") - Цена: " + costPrice + " - Количество: " + quantity +
                " - Годно до: " + expiryDate;
    }
}
