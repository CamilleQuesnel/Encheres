package fr.eni.tp.encheres.dal;

import java.util.List;

public interface ArticleVenduDAO {
    void insert (ArticleVenduDAO article);
    ArticleVenduDAO selectById(int id);
    List<ArticleVenduDAO> selectAll();
}
