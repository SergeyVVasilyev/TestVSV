package vsv.task4.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vsv.task4.service.ArticleDto;
import vsv.task4.service.ArticleListDto;
import vsv.task4.service.ArticleService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping(path = "/articles", consumes = MediaType.ALL_VALUE, produces = MediaType.ALL_VALUE)
public class ArticleController {
    private ArticleService articleService;

    @GetMapping(path = "/{id}")
    public ArticleDto getArticle(@PathVariable(name = "id") Long id) {
        ArticleDto articleDto = articleService.getArticle(id);
        if (articleDto == null) {
            throw new NotFoundArticleException();
        }
        return articleDto;
    }

    @GetMapping
    public ArticleListDto findArticles(@RequestParam(name = "createdDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate createdDate) {
        return articleService.toArticleListDto(articleService.findArticles(createdDate));
    }

    @PostMapping
    public ArticleDto create(@RequestBody @Valid ArticleDto article) {
        return articleService.create(article);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody @Valid ArticleDto article) {
        articleService.update(article);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) {
        articleService.delete(id);
    }

    @Autowired
    public void setArticleService(ArticleService articleService) {
        this.articleService = articleService;
    }

    public static class NotFoundArticleException extends RuntimeException {

    }

    @ControllerAdvice
    public static class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

        @ExceptionHandler(NotFoundArticleException .class)
        public void springHandleNotFound(HttpServletResponse response) throws IOException {
            response.sendError(HttpStatus.NOT_FOUND.value());
        }
    }
}
