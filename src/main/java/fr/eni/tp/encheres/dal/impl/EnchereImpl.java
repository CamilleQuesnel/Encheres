package fr.eni.tp.encheres.dal.impl;

import fr.eni.tp.encheres.bo.Enchere;
import fr.eni.tp.encheres.dal.EnchereDAO;

import java.util.List;

public class EnchereImpl implements EnchereDAO {




    @Override
    public void save(Enchere enchere) {

    }

    @Override
    public List<Enchere> selectByUtilisateur(int noUtilisateur) {
        return List.of();
    }

    @Override
    public List<Enchere> selectByArticle(int noArticle) {
        return List.of();
    }

    @Override
    public Enchere selectBestEnchere(int noArticle) {
        return null;
    }

    @Override
    public void deleteByArticle(int noArticle) {

    }

    @Override
    public boolean hasUserBid(int noUtilisateur, int noArticle) {
        return false;
    }
}
