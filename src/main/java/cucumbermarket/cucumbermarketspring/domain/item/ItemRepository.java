package cucumbermarket.cucumbermarketspring.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

  // @Query("SELECT p FROM Item p WHERE p.city = :city AND p.street1 = :street1 ORDER BY p.id")
  // List<Item> findByAddress_CityAndAddress_Street1(String city, String street1);
}