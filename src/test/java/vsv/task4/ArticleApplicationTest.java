package vsv.task4;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import vsv.task4.service.ArticleDto;
import vsv.task4.service.ArticleListDto;

import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ArticleApplicationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static ArticleDto createdArticleDto;

    @Test
    public void test1NotFound() throws Exception {
        ResponseEntity<Map> entity = testRestTemplate.getForEntity(
                "http://localhost:8080/articles/90000", Map.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void test2CreateTitleMoreThan64() throws Exception {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setCreatedDate(LocalDate.now());
        articleDto.setTitle("Very long Title AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        articleDto.setBody("Body 1");

        ResponseEntity<ArticleDto> entity = testRestTemplate.postForEntity(
                "http://localhost:8080/articles", articleDto, ArticleDto.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    public void test3Create() throws Exception {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setCreatedDate(LocalDate.now());
        articleDto.setTitle("Title 1");
        articleDto.setBody("Body 1");

        ResponseEntity<ArticleDto> entity = testRestTemplate.postForEntity(
                "http://localhost:8080/articles", articleDto, ArticleDto.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(entity.getBody());
        assertNotNull(entity.getBody().getId());
        createdArticleDto = entity.getBody();
    }

    @Test
    public void test4GetById() throws Exception {
        ResponseEntity<ArticleDto> entity = testRestTemplate.getForEntity(
                "http://localhost:8080/articles/" + createdArticleDto.getId(), ArticleDto.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(entity.getBody());
        assertEquals(createdArticleDto.getId(), entity.getBody().getId());
    }

    @Test
    public void test5FindByCreatedDate() throws Exception {
        ResponseEntity<ArticleListDto> entity = testRestTemplate.getForEntity(
                "http://localhost:8080/articles?createdDate=" + createdArticleDto.getCreatedDate(), ArticleListDto.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(entity.getBody());
        assertTrue(entity.getBody().getList().size() > 0);
    }

    @Test
    public void test6DeleteNotMoreThan1YearOld() throws Exception {
        testRestTemplate.delete("http://localhost:8080/articles/" + createdArticleDto.getId());

        ResponseEntity<ArticleDto> entity = testRestTemplate.getForEntity(
                "http://localhost:8080/articles/" + createdArticleDto.getId(), ArticleDto.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(entity.getBody());
        assertEquals(createdArticleDto.getId(), entity.getBody().getId());
    }

    @Test
    public void test7Update() throws Exception {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setId(createdArticleDto.getId());
        articleDto.setCreatedDate(createdArticleDto.getCreatedDate().minusYears(2));
        articleDto.setTitle("Title 1 update");
        articleDto.setBody("Body 1 updated");

        testRestTemplate.put("http://localhost:8080/articles", articleDto);
    }

    @Test
    public void test8Delete() throws Exception {
        testRestTemplate.delete("http://localhost:8080/articles/" + createdArticleDto.getId());

        ResponseEntity<ArticleDto> entity = testRestTemplate.getForEntity(
                "http://localhost:8080/articles/" + createdArticleDto.getId(), ArticleDto.class);
        then(entity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}
