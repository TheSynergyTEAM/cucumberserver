package cucumbermarket.cucumbermarketspring.domain.upload.domain.file;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<cucumbermarket.cucumbermarketspring.domain.upload.file.File, Long>{
}