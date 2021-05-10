package cucumbermarket.cucumbermarketspring.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface PhotoRepository extends JpaRepository<Photo, Long>, QuerydslPredicateExecutor<Photo> {

}