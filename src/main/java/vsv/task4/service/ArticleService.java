package vsv.task4.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vsv.task4.dao.ArticleDao;
import vsv.task4.dao.ArticleEntity;

import java.time.LocalDate;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class ArticleService {
    private ArticleDao articleDao;

    public ArticleDto getArticle(Long id) {
        return toArticleDto(articleDao.getById(id));
    }

    public List<ArticleDto> findArticles(LocalDate createdDate) {
        return articleDao.findArticles(createdDate).stream().map(this::toArticleDto).collect(toList());
    }

    public ArticleDto create(ArticleDto articleDto) {
        return toArticleDto(articleDao.create(toArticleEntity(articleDto)));
    }

    public void update(ArticleDto articleDto) {
        articleDao.update(toArticleEntity(articleDto));
    }

    public void delete(Long id) {
        articleDao.deleteByIdAndCreatedDateMoreThanOneYearOld(id);
    }

    @Autowired
    public void setArticleDao(ArticleDao articleDao) {
        this.articleDao = articleDao;
    }

    public ArticleDto toArticleDto(ArticleEntity articleEntity) {
        if (articleEntity == null) {
            return null;
        }
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(articleEntity.getId());
        articleDto.setCreatedDate(articleEntity.getCreatedDate());
        articleDto.setTitle(articleEntity.getTitle());
        articleDto.setBody(articleEntity.getBody());
        return articleDto;
    }

    public ArticleEntity toArticleEntity(ArticleDto articleDto) {
        if (articleDto == null) {
            return null;
        }
        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setId(articleDto.getId());
        articleEntity.setCreatedDate(articleDto.getCreatedDate());
        articleEntity.setTitle(articleDto.getTitle());
        articleEntity.setBody(articleDto.getBody());
        return articleEntity;
    }

    public ArticleListDto toArticleListDto(List<ArticleDto> listDto) {
        return new ArticleListDto(listDto);
    }
}
