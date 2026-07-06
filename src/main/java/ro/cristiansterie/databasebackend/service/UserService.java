package ro.cristiansterie.databasebackend.service;

import org.springframework.stereotype.Service;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.repository.UserRepository;
import ro.cristiansterie.databasebackend.util.converter.models.UserModelConverter;

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
	
	public UserDTO getById(Long id) {
		return converter.toDto(repo.findById(id));
	}

}
