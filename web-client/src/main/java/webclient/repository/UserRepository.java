package webclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webclient.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
