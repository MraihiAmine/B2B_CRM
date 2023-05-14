package B2B.CRM.dashboard.repositories;

import org.springframework.data.repository.CrudRepository;

import B2B.CRM.dashboard.entities.Article;
public interface ArticleRepository extends CrudRepository<Article,Long> {

}
