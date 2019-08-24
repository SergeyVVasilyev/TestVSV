package vsv.task4.dao;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import static java.util.stream.Collectors.toList;

@Repository
public class ArticleDao {
    private final AtomicLong idSequence = new AtomicLong(0);
    private final List<ArticleEntity> articleList = new CopyOnWriteArrayList<>();

    public ArticleEntity getById(Long id) {
        return articleList.stream().filter(a -> Objects.equals(a.getId(), id)).findFirst().orElse(null);
    }

    public List<ArticleEntity> findArticles(LocalDate createdDate) {
        return articleList.stream().filter(a -> Objects.equals(a.getCreatedDate(), createdDate)).collect(toList());
    }

    public ArticleEntity create(ArticleEntity article) {
        checkTitleLength(article.getTitle());
        synchronized (articleList) {
            article.setId(idSequence.getAndIncrement());
            articleList.add(article);
            return article;
        }
    }

    public void update(ArticleEntity article) {
        checkTitleLength(article.getTitle());
        synchronized (articleList) {
            ArticleEntity foundArticle = getById(article.getId());
            if (foundArticle != null) {
                foundArticle.setCreatedDate(article.getCreatedDate()); // TODO: is it need to update?
                foundArticle.setTitle(article.getTitle());
                foundArticle.setBody(article.getBody());
            }
        }
    }

    public void deleteByIdAndCreatedDateMoreThanOneYearOld(Long id) {
        synchronized (articleList) {
            ArticleEntity foundArticle = getById(id);
            if (foundArticle != null && foundArticle.getCreatedDate().isBefore(LocalDate.now().minusYears(1))) {
                articleList.remove(foundArticle);
            }
        }
    }

    private static void checkTitleLength(String title) {
        if (title != null && title.length() > 64) throw new IllegalArgumentException("Title length more than 64 symbols");
    }
}
