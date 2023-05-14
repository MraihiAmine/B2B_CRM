package B2B.CRM.dashboard.repositories.statisitics;
import B2B.CRM.dashboard.entities.statistics.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

