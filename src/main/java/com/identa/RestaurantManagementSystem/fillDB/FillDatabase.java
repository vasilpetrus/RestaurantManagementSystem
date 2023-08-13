package com.identa.RestaurantManagementSystem.fillDB;

import com.identa.RestaurantManagementSystem.entity.Item;
import com.identa.RestaurantManagementSystem.entity.User;
import com.identa.RestaurantManagementSystem.repository.ItemRepository;
import com.identa.RestaurantManagementSystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static com.identa.RestaurantManagementSystem.entity.enums.UserRole.ADMINISTRATOR;
import static com.identa.RestaurantManagementSystem.entity.enums.UserRole.WORKER;

/**
 * This class is a configuration component responsible for initializing the database with sample data
 * using Spring Boot's CommandLineRunner interface. It creates and saves sample items and users to
 * the corresponding repositories upon application startup.
 */
@Configuration
public class FillDatabase {

    @Bean
    CommandLineRunner run(UserRepository userRepository,
            ItemRepository itemRepository) {
        return args -> {

            // ITEMS
            Item chickenBurger = new Item(
                    "1001",
                    "Chicken burger",
                    "Tasty chicken burger",
                    "https://static01.nyt.com/images/2021/04/11/dining/yf-chicken-burgers/yf-chicken-burgers-mediumSquareAt3X.jpg",
                    95
            );

            Item beefBurger = new Item(
                    "1002",
                    "Beef burger",
                    "Delicious beef burger",
                    "https://production-media.gousto.co.uk/cms/mood-image/3397_The-Roddas-Cream-Tea-Beef-Burger--8453-1681459029469.jpg",
                    109
            );

            Item cheeseBurger = new Item(
                    "1003",
                    "Cheese burger",
                    "Perfect cheese burger",
                    "https://yellowplate.ng/wp-content/uploads/2019/03/Cheese-Burger.jpg",
                    90
            );

            Item cocaCola = new Item(
                    "1004",
                    "Coca-cola",
                    "Refreshing cola - 0,33",
                    "https://m.media-amazon.com/images/I/81nn0r6y-4L._AC_UF1000,1000_QL80_.jpg",
                    25
            );

            Item americanoCoffee = new Item(
                    "1005",
                    "Coffee Americano",
                    "Arabica 100%",
                    "https://images.squarespace-cdn.com/content/v1/5a7cbe247131a5f17b3cc8fc/1519447742018-MOHBW2G0VOQ7QSCPJE14/Americano-Coffee-Lounge-Ingredients.jpg",
                    34
            );


            Item tea = new Item(
                    "1006",
                    "Black Tea",
                    "Tea from Sri Lanka",
                    "https://5.imimg.com/data5/BS/SV/MY-42129887/herbal-teas-500x500.jpg",
                    20
            );

            //USERS
            User admin = new User("John", "Smith", "admin", "admin", ADMINISTRATOR);
            User worker = new User("Maria", "Soles", "worker", "worker", WORKER);

            // Save sample items and users to their respective repositories
            itemRepository.saveAll(List.of(chickenBurger, beefBurger, cheeseBurger, cocaCola, americanoCoffee, tea));
            userRepository.saveAll(List.of(admin, worker));
        };
    }
}
