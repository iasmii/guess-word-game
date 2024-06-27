package server.repository;

import comun.domain.User;

import java.util.Optional;

public interface IUserRepository extends Repository<Long, User> {

    Optional<User> findByUsername(String username);
}
