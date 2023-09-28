package com.coffeedev.admin.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.coffeedev.common.entity.User;
@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer>, 
	CrudRepository<User, Integer> {

	@Query("SELECT u FROM User u WHERE u.email = :email")
	public User getUserByEmail(@Param("email") String email);

	public Long countById(Integer id);

//	@Query("SELECT u FROM User u WHERE CONCAT(u.id, ' ', u.email, ' ', u.firstName, ' '," + " u.lastName) LIKE %?1%")
//	public Page<User> findAll(String keyword, Pageable pageable);

	// ?1: Đại diện cho tham số đầu tiên, tức là id của người dùng (Integer id).
	// ?2: Đại diện cho tham số thứ hai, tức là enabled - giá trị mới cho thuộc tính
	// enabled
	@Query("UPDATE User u SET u.enabled = ?2 WHERE u.id = ?1")
	@Modifying
	public void updateEnabledStatus(Integer id, boolean enabled);
}
