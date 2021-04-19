package cucumbermarket.cucumbermarketspring.domain.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ReviewRepository extends JpaRepository<Review, Long>, QuerydslPredicateExecutor<Review> {

  //  @EntityGraph(attributePaths = "member")
  //  @Query("")
  //  List<Review> findBySeller(String name);

  //  @EntityGraph(attributePaths = "member")
  //  @Query("SELECT r FROM Review r JOIN r.member m WHERE m.name = name")
  //  List<Review> findByBuyer(String name);
}