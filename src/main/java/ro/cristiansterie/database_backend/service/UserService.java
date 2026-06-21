package ro.cristiansterie.database_backend.service;

import org.springframework.stereotype.Service;
import ro.cristiansterie.database_backend.dto.UserDTO;
import ro.cristiansterie.database_backend.repository.UserRepository;
import ro.cristiansterie.database_backend.util.converter.models.UserModelConverter;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repo;
    private final UserModelConverter converter;

    public UserService(UserRepository repo,  UserModelConverter converter) {
        this.repo = repo;
        this.converter = converter;
    }

    public List<UserDTO> getAll() {
        return converter.toDtoList(repo.findAll());
    }

}
