package com.priyanshparekh.plated.user;

import com.priyanshparekh.plated.search.UserSearchItemProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    List<UserSearchItemProjection> findAllByDisplayNameContaining(String query);
}
