package vsv.task4.service;

import java.util.List;

public class ArticleListDto {
    private List<ArticleDto> list;

    public ArticleListDto() {
    }

    public ArticleListDto(List<ArticleDto> list) {
        this.list = list;
    }

    public List<ArticleDto> getList() {
        return list;
    }

    public void setList(List<ArticleDto> list) {
        this.list = list;
    }
}
