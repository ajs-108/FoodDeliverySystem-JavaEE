package com.app.dao.jpa.impl;

import com.app.common.exception.DBException;
import com.app.common.util.EntityManagerFactoryUtil;
import com.app.dao.jpa.ICartRepository;
import com.app.model.common.Category;
import com.app.model.common.FoodItem;
import com.app.model.jpa.JPACart;
import com.app.model.jpa.JPAUser;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class CartRepository implements ICartRepository {
    @Override
    public void save(JPACart cart) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.merge(cart);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public List<JPACart> find(int userId) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            Query query = em.createNativeQuery("""
                            SELECT u.user_id, fi.food_item_id, fi.food_name, fi.food_description,
                            fi.price, fi.discount, fi.image_path, c.category_id,
                            c.category_name, sc.quantity
                            from shopping_cart sc, user_ u, food_item fi, category c
                            where sc.user_id = u.user_id and sc.food_item_id = fi.food_item_id
                            and fi.category_id = c.category_id and u.user_id = ?1
                            """, JPACart.class)
                    .setParameter(1, userId);
            tx = em.getTransaction();
            tx.begin();
            List<JPACart> result = query.getResultList();
            List<JPACart> cartList = new ArrayList<>();
            for (JPACart cart : result) {
                Category category = new Category();
                category.setCategoryId(cart.getFoodItem().getCategory().getCategoryId());
                category.setCategoryName(cart.getFoodItem().getCategory().getCategoryName());
                FoodItem foodItem = new FoodItem();
                foodItem.setFoodItemId(cart.getFoodItem().getFoodItemId());
                foodItem.setFoodName(cart.getFoodItem().getFoodName());
                foodItem.setFoodDescription(cart.getFoodItem().getFoodDescription());
                foodItem.setCategory(category);
                foodItem.setPrice(cart.getFoodItem().getPrice());
                foodItem.setDiscount(cart.getFoodItem().getDiscount());
                foodItem.setImagePath(cart.getFoodItem().getImagePath());
                JPAUser user = new JPAUser();
                user.setUserId(cart.getUser().getUserId());
                JPACart jpaCart = new JPACart();
                jpaCart.setUser(user);
                jpaCart.setFoodItem(foodItem);
                jpaCart.setQuantity(cart.getQuantity());
                cartList.add(jpaCart);
            }
            tx.commit();
            return cartList;
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void remove(JPACart cart) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            JPACart cart1= em.merge(cart);
            em.remove(cart1);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void updateQuantity(JPACart cart) throws DBException {
        EntityTransaction tx = null;
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            tx = em.getTransaction();
            tx.begin();
            em.merge(cart);
            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new DBException(e);
        }
    }

    @Override
    public void removeCartOfUser(JPAUser user) throws DBException {
        try (EntityManager em = EntityManagerFactoryUtil.getEmfInstance().createEntityManager()) {
            Query removeQuery = em.createQuery("""
                            delete from cart c where c.user = ?1
                            """)
                    .setParameter(1, user);
            removeQuery.executeUpdate();
        } catch (Exception e) {
            throw new DBException(e);
        }
    }
}
