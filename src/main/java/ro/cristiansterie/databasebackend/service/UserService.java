package ro.cristiansterie.databasebackend.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.MethodNotAllowedException;
import ro.cristiansterie.databasebackend.dto.UserDTO;
import ro.cristiansterie.databasebackend.repository.UserRepository;
import ro.cristiansterie.databasebackend.util.AppConstants;
import ro.cristiansterie.databasebackend.util.converter.models.UserModelConverter;

import java.util.List;

@Service
public class UserService {

	private final UserRepository repo;
	private final UserModelConverter converter;

	public UserService(UserRepository repo, UserModelConverter converter) {
		this.repo = repo;
		this.converter = converter;
	}

	public List<UserDTO> getAll() {
		return converter.toDtoList(repo.findAll());
	}

	public UserDTO getById(Long id) {
		var user = repo.findById(id)
		               .orElse(null);
		return converter.toDto(user);
	}

	public UserDTO addUser(UserDTO userDTO) {
		return converter.toDto(repo.save(converter.toEntity(userDTO)));
	}

	public UserDTO updateUser(UserDTO userDTO) {
		if (repo.existsById(userDTO.id())) {
			return converter.toDto(repo.save(converter.toEntity(userDTO)));
		}

		throw new EntityNotFoundException(AppConstants.UPDATE_NOT_ALLOWED + userDTO.id());
	}

	public Boolean deleteById(Long id) {
		try {
			repo.deleteById(id);

			return true;
		} catch (
				Exception e) {
			// XXX: log this event;
			return false;
		}
	}

}
